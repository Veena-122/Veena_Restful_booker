@RestfullBroker_CRUD 
Feature: To test restful-booker

  Background: create an auth token
    Given user can access the endpoint "/auth"
    When user creates a auth token with credential "admin" & "password123"
    Then user should get the response code 200

 Scenario Outline: To perform a CURD operation on restful-booker
    Given user can access the endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then user should get the response code 200
    And user validates the response against JSON schema "createBookingSchema.json"
    And user updates the details of a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   |
      | <firstname> | <lastname> | 1300					| <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    And user should get the response code 200
    And user validates the response against JSON schema "bookingDetailsSchema.json"
    And user view details using a booking ID
    And user should get the response code 200
    And user validates the response against JSON schema "bookingDetailsSchema.json"
    And user delete booking with basic auth "admin" & "password123"
    And user should get the response code 201

    Examples: 
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Veena     | Kunooru  |       3333 | true        | 2023-01-01 | 2023-01-15 | Refrigerator    |
      | Ram       | Kunooru  |       1300 | false       | 2022-02-05 | 2023-02-15 | Pool            |      