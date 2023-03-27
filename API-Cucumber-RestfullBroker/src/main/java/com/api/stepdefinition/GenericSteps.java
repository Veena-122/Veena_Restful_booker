package com.api.stepdefinition;

import static org.junit.Assert.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.model.BookingDetailsDTO;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;

public class GenericSteps {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(GenericSteps.class);
	
	public GenericSteps(TestContext context) {
		this.context = context;
	}

	@When("user creates a auth token with credential {string} & {string}")
	public void userCreatesAAuthTokenWithCredential(String username, String password) {
		JSONObject credentials = new JSONObject();
		credentials.put("username", username);
		credentials.put("password", password);
		context.response = context.requestSetup().body(credentials.toString())
				.when().post(context.session.get("endpoint").toString());
		String token = context.response.path("token");
		LOG.info("Auth Token: "+token);
		context.session.put("token", "token="+token);	
	}
	
	@Given("user can access the endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {		
		context.session.put("endpoint", endpoint);
	}

	@Then("user should get the response code {int}")
	public void userShpuldGetTheResponseCode(Integer statusCode) {
		assertEquals(Long.valueOf(statusCode), Long.valueOf(context.response.getStatusCode()));
	}
	
	@Then("user validates the response against JSON schema {string}")
	public void userValidatesResponseWithJSONSchema(String schemaFileName) {
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+schemaFileName));
		LOG.info("Successfully Validated schema from "+schemaFileName);
	}
	
}
