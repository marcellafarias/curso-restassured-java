package br.com.curso;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		// forma verbosa - feita para entender o caminho
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");

		// Tipos diferentes de asserção
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		// Assert.assertTrue("O status code deveria ser 200", response.statusCode() ==
		// 201);

		// Primeiro parametro é o valor esperado, assim a leitura do erro fica coerente
		Assert.assertEquals(200, response.statusCode());

		// Validaçao final
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);
	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		// forma verbosa direta
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		// forma simples de fazer o request qndo importado o get
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);

		// forma com gherkin
		given().when().get("http://restapi.wcaquino.me/ola").then()
				// .assertThat() --> exemplo de outro tipo, só facilita a leitura
				.statusCode(200);

	}

	@Test
	public void devoConhecerMatcherHamcrest() {
//		Lista completa dos matchers
//		http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html

		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));

		// Verifica se é do tipo Integer
		Assert.assertThat(128, Matchers.isA(Integer.class));

		// Verifica se é do tipo Double
		Assert.assertThat(128d, Matchers.isA(Double.class));

		// Verifica se 128d é maior que 130d
		Assert.assertThat(128d, Matchers.greaterThan(130d));

		// Verifica se 128d é menor que 130d
		Assert.assertThat(128d, Matchers.lessThan(130d));

		// Verificando Listas
		// Primeiro eu crio a lista
		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		// Verificando qual o tamanho da Lista
		// * importei as classes Assert e Matchers para n precisar indicar
		assertThat(impares, hasSize(5));

		// Verifica na ordem especifica
		assertThat(impares, contains(1, 3, 5, 7, 9));

		// Verifica em qualquer ordem
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7, 9));

		// Verifica somente se existe um elemento especifico
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1, 5));

		// Forma diferentes de fazer a mesma asserção de comparação por diferença
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));

		// Asserção do tipo Qualquer um desses -tipo OU - Primeiro parametro é o expect
		assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Luiz", anyOf(is("Maria"), is("Joaquina")));

		// Asserção do tipo Todos esses - tipo E - Primeiro parametro é o expect
		assertThat("Maria", allOf(is("Maria"), is("Joaquina")));

		// Asserção com cumulando vários tipos de verificação
		assertThat("Joaquina", allOf(startsWith("João"), endsWith("ima"), containsString("quina")));

	}

}
