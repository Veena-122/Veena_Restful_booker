package com.api.stepdefinition;

import static org.junit.Assert.*;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.api.model.BookingDTO;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class CreateBooking {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(CreateBooking.class);

	public CreateBooking(TestContext context) {
		this.context = context;
	}

	@When("user creates a booking")
	public void userCreatesABooking(DataTable table) {
		Map<String,String> data = table.asMaps().get(0);
		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", data.get("firstname"));
		bookingBody.put("lastname", data.get("lastname"));
		bookingBody.put("totalprice", Integer.valueOf(data.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.valueOf(data.get("depositpaid")));
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", (data.get("checkin")));
		bookingDates.put("checkout", (data.get("checkout")));
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", data.get("additionalneeds"));

		context.response = context.requestSetup().body(bookingBody.toString())
				.when().post(context.session.get("endpoint").toString());

		BookingDTO bookingDTO = ResponseHandler.deserializedResponse(context.response, BookingDTO.class);
		assertNotNull("Booking not created", bookingDTO);
		LOG.info("Newly created booking ID: "+bookingDTO.getBookingid());
		context.session.put("bookingID", bookingDTO.getBookingid());
		validatedata(new JSONObject(data), bookingDTO);
	}

	private void validatedata(JSONObject data, BookingDTO bookingDTO) {
		LOG.info(data);
		assertNotNull("Booking ID missing", bookingDTO.getBookingid());
		assertEquals("First Name did not match", data.get("firstname"), bookingDTO.getBooking().getFirstname());
		assertEquals("Last Name did not match", data.get("lastname"), bookingDTO.getBooking().getLastname());
		assertEquals("Total Price did not match", data.get("totalprice"), bookingDTO.getBooking().getTotalprice());
		assertEquals("Deposit Paid did not match", data.get("depositpaid"), bookingDTO.getBooking().getDepositpaid());
		assertEquals("Additional Needs did not match", data.get("additionalneeds"), bookingDTO.getBooking().getAdditionalneeds());
		assertEquals("Check in Date did not match", data.get("checkin"), bookingDTO.getBooking().getBookingdates().getCheckin());
		assertEquals("Check out Date did not match", data.get("checkout"), bookingDTO.getBooking().getBookingdates().getCheckout());
	}

}
