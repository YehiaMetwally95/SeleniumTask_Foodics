package objectModelsForAPIs;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.LoginRequestPojo;
import pojoClassesForAPIs.LoginResponsePojo;
import yehiaEngine.assertions.CustomAssert;

import static yehiaEngine.managers.ApisManager.getResponseCode;

public class LoginResponseModel {

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public LoginResponseModel(LoginRequestPojo requestObject, LoginResponsePojo responseObject,Response response) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.response = response;
    }

    //Validation Methods
    @Step("Validate Massage From Response")
    public LoginResponseModel validateMassageFromResponse(String expectedMessage) {
        CustomAssert.assertEquals(responseObject.getReason(), expectedMessage);
        return this;
    }

    @Step("Validate Code From Response")
    public LoginResponseModel validateCodeFromResponse(int responseCody) {
        CustomAssert.assertEquals(getResponseCode(response), responseCody);
        return this;
    }

    @Step("Validate Token Exists In Response")
    public LoginResponseModel validateTokenExistsInResponse() {
        CustomAssert.assertTrue(responseObject.getToken() != null);
        return this;
    }

    @Step("Validate Token Not Exists In Response")
    public LoginResponseModel validateTokenNotExistInResponse() {
        CustomAssert.assertTrue(responseObject.getToken() == null);
        return this;
    }

    //Getter Methods
    @Step("Get Token from Response")
    public String getToken() {
        return responseObject.getToken();
    }

    public LoginRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public LoginResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}
