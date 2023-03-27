package com.api.stepdefinition;

import com.api.utils.TestContext;
import io.cucumber.java.en.When;

public class DeleteBooking {
	private TestContext context;

	public DeleteBooking(TestContext context) {
		this.context = context;
	}

	@When("user delete booking with basic auth {string} & {string}")
	public void userMakesARequestToDeleteBookingWithBasicAuth(String username, String password) {
		context.response = context.requestSetup()
				.auth().preemptive().basic(username, password)
				.pathParam("bookingID", context.session.get("bookingID"))
				.when().delete(context.session.get("endpoint")+"/{bookingID}");
	}
}
