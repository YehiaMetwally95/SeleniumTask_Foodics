package objectModelsForAPIs;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.CreateBookingRequestPojo;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import yehiaEngine.assertions.CustomAssert;

import static yehiaEngine.managers.ApisManager.getResponseCode;

public class CreateBookingResponseModel {

    //ObjectsFromPojoClasses
    CreateBookingRequestPojo requestObject;
    CreateBookingResponsePojo responseObject;
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public CreateBookingResponseModel(CreateBookingRequestPojo requestObject, CreateBookingResponsePojo responseObject, Response response) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.response = response;
    }

    //Validation Methods
    @Step("Validate Code From Response")
    public CreateBookingResponseModel validateCodeFromResponse(int responseCody) {
        CustomAssert.assertEquals(getResponseCode(response), responseCody);
        return this;
    }

    @Step("Validate Id Exists In Response")
    public CreateBookingResponseModel validateIdExistsInResponse() {
        CustomAssert.assertTrue(responseObject.getBookingid() != 0);
        return this;
    }

    @Step("Validate Booking Details From Response")
    public CreateBookingResponseModel validateBookingDetailsFromResponse() {
        CustomAssert.assertEquals(responseObject.getBooking().getFirstname(), requestObject.getFirstname());
        CustomAssert.assertEquals(responseObject.getBooking().getLastname(), requestObject.getLastname());
        CustomAssert.assertEquals(responseObject.getBooking().getTotalprice(), requestObject.getTotalprice());
        CustomAssert.assertEquals(responseObject.getBooking().isDepositpaid(), requestObject.isDepositpaid());
        CustomAssert.assertEquals(responseObject.getBooking().getBookingdates().getCheckin(), requestObject.getBookingdates().getCheckin());
        CustomAssert.assertEquals(responseObject.getBooking().getBookingdates().getCheckout(), requestObject.getBookingdates().getCheckout());
        CustomAssert.assertEquals(responseObject.getBooking().getAdditionalneeds(), requestObject.getAdditionalneeds());

        return this;
    }

    @Step("Validate Id Not Exists In Response")
    public CreateBookingResponseModel validateIdNotExistInResponse() {
        CustomAssert.assertTrue(responseObject.getBookingid() == 0);
        return this;
    }

    //Getter Methods
    @Step("Get Id from Response")
    public int getId() {
        return responseObject.getBookingid();
    }

    public CreateBookingRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public CreateBookingResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}
