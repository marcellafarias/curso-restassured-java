package br.com.curso;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ValidarBody {
	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is(not(nullValue())))
			// verifica exatamente o corpo
			.body(Matchers.is("Ola Mundo!"))
			.body(containsString("Mundo"));
	}

}
