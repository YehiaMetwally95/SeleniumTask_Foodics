package objectModelsForAPIs;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.UpdateBookingRequestPojo;
import pojoClassesForAPIs.UpdateBookingResponsePojo;
import yehiaEngine.assertions.CustomAssert;

import static yehiaEngine.managers.ApisManager.getResponseCode;

public class UpdateBookingResponseModel {

    //ObjectsFromPojoClasses
    UpdateBookingRequestPojo requestObject;
    UpdateBookingResponsePojo responseObject;
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public UpdateBookingResponseModel(UpdateBookingRequestPojo requestObject, UpdateBookingResponsePojo responseObject, Response response) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.response = response;
    }

    //Validation Methods
    @Step("Validate Code From Response")
    public UpdateBookingResponseModel validateCodeFromResponse(int responseCody) {
        CustomAssert.assertEquals(getResponseCode(response), responseCody);
        return this;
    }

    @Step("Validate Updated Booking Details From Response")
    public UpdateBookingResponseModel validateUpdatedBookingDetailsFromResponse() {
        CustomAssert.assertEquals(responseObject.getFirstname(), requestObject.getFirstname());
        CustomAssert.assertEquals(responseObject.getLastname(), requestObject.getLastname());
        CustomAssert.assertEquals(responseObject.getTotalprice(), requestObject.getTotalprice());
        CustomAssert.assertEquals(responseObject.isDepositpaid(), requestObject.isDepositpaid());
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckin(), requestObject.getBookingdates().getCheckin());
        CustomAssert.assertEquals(responseObject.getBookingdates().getCheckout(), requestObject.getBookingdates().getCheckout());
        CustomAssert.assertEquals(responseObject.getAdditionalneeds(), requestObject.getAdditionalneeds());

        return this;
    }

    //Getter Methods
    public UpdateBookingRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public UpdateBookingResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}
