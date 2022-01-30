package br.ce.ivan.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetonarTarefas() {
		
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
		
	}
	
	@Test
	public void deveAdicionarTarefaComSuccesso() {
		
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2022-01-29\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		    //.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		RestAssured.given()
		.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-01-29\" }")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		    //.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
		
	}
}
