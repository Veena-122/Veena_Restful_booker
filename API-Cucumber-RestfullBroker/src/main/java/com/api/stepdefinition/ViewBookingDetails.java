package com.api.stepdefinition;

import static org.junit.Assert.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.api.model.BookingDetailsDTO;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ViewBookingDetails {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(ViewBookingDetails.class);
	
	public ViewBookingDetails(TestContext context) {
		this.context = context;
	}

	@Then("user view details using a booking ID")
	public void userMakesARequestToViewDetailsOfBookingID() {
		LOG.info("Session BookingID: "+context.session.get("bookingID"));
		context.response = context.requestSetup().pathParam("bookingID", context.session.get("bookingID"))
				.when().get(context.session.get("endpoint")+"/{bookingID}");
		BookingDetailsDTO bookingDetails = ResponseHandler.deserializedResponse(context.response, BookingDetailsDTO.class);
		assertNotNull("Booking Details not found!!", bookingDetails);
		context.session.put("firstname", bookingDetails.getFirstname());
		context.session.put("lastname", bookingDetails.getLastname());
	}	
}
