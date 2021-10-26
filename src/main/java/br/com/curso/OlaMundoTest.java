package br.com.curso;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		//forma verbosa - feita para entender o caminho
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		
		//Tipos diferentes de asserção
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 201);
		
		//Primeiro parametro é o valor esperado, assim a leitura do erro fica coerente
		Assert.assertEquals(200, response.statusCode());
		
		//Validaçao final
		ValidatableResponse validacao = response.then();
		validacao.statusCode(201);
		
		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
		System.out.println(response.statusCode() == 200);
	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		//forma verbosa direta
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

		// forma simples de fazer o request qndo importado o get
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);

		// forma com gherkin
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			//.assertThat() --> exemplo de outro tipo, só facilita a leitura
			.statusCode(200);

	}

}
