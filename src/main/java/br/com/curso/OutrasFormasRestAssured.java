package br.com.curso;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OutrasFormasRestAssured {
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

}
