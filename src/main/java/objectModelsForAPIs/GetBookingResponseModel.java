package objectModelsForAPIs;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.CreateBookingRequestPojo;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import pojoClassesForAPIs.GetBookingRequestPojo;
import pojoClassesForAPIs.GetBookingResponsePojo;
import yehiaEngine.assertions.CustomAssert;

import static yehiaEngine.managers.ApisManager.getResponseCode;

public class GetBookingResponseModel {

    //ObjectsFromPojoClasses
    GetBookingRequestPojo requestObject;
    GetBookingResponsePojo responseObject;
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public GetBookingResponseModel(GetBookingRequestPojo requestObject, GetBookingResponsePojo responseObject, Response response) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.response = response;
    }

    //Validation Methods
    @Step("Validate Code From Response")
    public GetBookingResponseModel validateCodeFromResponse(int responseCody) {
        CustomAssert.assertEquals(getResponseCode(response), responseCody);
        return this;
    }

    @Step("Validate Booking Details From Response")
    public GetBookingResponseModel validateBookingDetailsFromResponse(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkInDate, String checkOutDate, String additionalNeeds) {
        CustomAssert.assertEquals(responseObject.getFirstname(), firstName);
        CustomAssert.assertEquals(responseObject.getLastname(), lastName);
        CustomAssert.assertEquals(responseObject.getTotalprice(), totalPrice);
        CustomAssert.assertEquals(responseObject.isDepositpaid(), depositPaid);
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckin(), checkInDate);
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckout(), checkOutDate);
        CustomAssert.assertEquals(responseObject.getAdditionalneeds(), additionalNeeds);

        return this;
    }

    @Step("Validate Booking Details Not Exists In Response")
    public GetBookingResponseModel validateBookingDetailsNotExistInResponse() {
        CustomAssert.assertEquals(responseObject.getFirstname(), null);
        CustomAssert.assertEquals(responseObject.getLastname(), null);
        CustomAssert.assertEquals(responseObject.getTotalprice(), null);
        CustomAssert.assertEquals(responseObject.isDepositpaid(), null);
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckin(), null);
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckout(), null);
        CustomAssert.assertEquals(responseObject.getAdditionalneeds(), null);

        return this;
    }

    //Getter Methods

    public GetBookingRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public GetBookingResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}
