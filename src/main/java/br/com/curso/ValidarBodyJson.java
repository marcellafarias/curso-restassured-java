package br.com.curso;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ValidarBodyJson {
	
	@Test
	public void devoVerificarPrimeiroNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body(is(not(nullValue())))
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18));
	}
	
	
	@Test
	public void deveVerificarPrimeiroNiveldeOutrasFormas() {
		Response response = RestAssured.request(Method.GET,"http://restapi.wcaquino.me/users/1" );
		
		//usando a lib path - essa é mais inteligente, identifica se é json ou xml
		Assert.assertEquals((1), response.path("id"));
		Assert.assertEquals((1), response.path("%s", "id"));
		
		
		//usando a lib JsonPath - só identifica json
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		//usando from - metodo static do JsonPath
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);
		
	}
	
	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body(is(not(nullValue())))
			.body("name", containsString("Joaquina"))
			//entrando no segundo nivel - nome do primeiro nivel.nome do segundo nivel
			.body("endereco.rua", is("Rua dos bobos"));		
	}
	
	
	@Test
	public void deveVerificarLista() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body(is(not(nullValue())))
			.body("name", containsString("Ana"))
			//entrando na Lista, começa pelo numero chave Array que pretende checar, inicial [0]
			//depois . o nome do atributo dentro da lista que quer acessar
			.body("filhos", hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", hasItems("Zezinho" , "Luizinho"))
			;		
	}
	
	@Test
	public void deveVerificarListaNaRaiz() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.statusCode(200)
			//Quando o json já começa na raiz com uma array/lista: 
			//vc pode identificar a busca inicial pelo simbolo do cifrão
			//ou já colocar o nome do item da lista e o numero do indexador
			.body("$", hasSize(3))
			.body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
			.body("age[1]", is(25))
			.body("salary", contains(1234.5678f, 2500, null))
			
			//como acessar uma lista dentro de outro lista 
			// passa o caminho com o nome da lista de primeiro nivel . lista de segundo nivel 
			// e dentro do parametro de indicar que vc busca um Arrays. as List
			.body("filhos.name", hasItem(Arrays.asList("Zezinho" , "Luizinho")))
			;			
	}
	
	@Test
	public void deveRetornarErroUsuarioInexistente() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/4")
		.then()
			.statusCode(404)
			.body("error", is("Usuário inexistente"))
			;		
	}
	
	
	@Test
	public void devoFazerVerificacoesAvancadas() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.statusCode(200)
			.body("$", hasSize(3))
			
			//quantos usuarios existem de até 25 anos na lista
			//indica o campo, metodo encontrar todos, 
			//cria uma variavel it onde vc ser salva a iteração
			//e indica que lá precisa ter todas as idades iguais ou maiores que 25
			.body("age.findAll{it <= 25 }.size()", is(2))
			// usando mais de uma condição (todos com idades maior ou igual 25 e menor que 20
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
			
			//como pegar o nome da usuario que passou no filtro de idade
			//começa direto no metodo, deixando em branco para assim buscar desde a raiz do json
			// e dentro da iteraçao passa o atributo que quer checar
			//insere ao final o colchetes e indexador para transformar em um objeto do tipo Array
			.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
			
			//findAll retorna todos, find retorna somente o primeiro encontrado
			.body("find{it.age <= 25}.name", is("Maria Joaquina"))
			
			//verifica todos os elementos que possuem a letra N e retorna o nome desses objetos
			.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))
			
			//verifica todos nomes com mais que 10 caracteres e retorna o nome desses objetos
			.body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
			
			
		
			
			
			;
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	

}
