package com.api.stepdefinition;

import static org.junit.Assert.*;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.api.model.BookingDetailsDTO;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class UpdateBooking {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(UpdateBooking.class);
	
	public UpdateBooking(TestContext context) {
		this.context = context;
	}

	@When("user updates the details of a booking")
	public void userUpdatesABooking(DataTable dataTable) {
		Map<String,String> bookingData = dataTable.asMaps().get(0);
		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", bookingData.get("firstname"));
		bookingBody.put("lastname", bookingData.get("lastname"));
		bookingBody.put("totalprice", Integer.valueOf(bookingData.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.valueOf(bookingData.get("depositpaid")));
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", (bookingData.get("checkin")));
		bookingDates.put("checkout", (bookingData.get("checkout")));
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

		context.response = context.requestSetup()
				.header("Cookie", context.session.get("token").toString())
				.pathParam("bookingID", context.session.get("bookingID"))
				.body(bookingBody.toString())
				.when().put(context.session.get("endpoint")+"/{bookingID}");

		BookingDetailsDTO bookingDetailsDTO = ResponseHandler.deserializedResponse(context.response, BookingDetailsDTO.class);
		assertNotNull("Booking not created", bookingDetailsDTO);
	}	
}
