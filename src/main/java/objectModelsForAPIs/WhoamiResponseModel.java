package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.WhoamiRequestPojo;
import pojoClassesForAPIs.WhoamiResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;

import java.util.Arrays;
import java.util.List;

import static yehiaEngine.managers.ApisManager.getResponseCode;

public class WhoamiResponseModel {

    //ObjectsFromPojoClasses
    WhoamiRequestPojo requestObject;
    WhoamiResponsePojo responseObject;
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public WhoamiResponseModel(WhoamiRequestPojo requestObject, WhoamiResponsePojo responseObject, Response response) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.response = response;
    }

    //Validation Methods
    @Step("Validate Massage From Response")
    public WhoamiResponseModel validateMassageFromResponse(String expectedMessage) {
        CustomAssert.assertEquals(responseObject.getMessage(), expectedMessage);
        return this;
    }

    @Step("Validate Status From Response")
    public WhoamiResponseModel validateStatusFromResponse(String expectedStatus) {
        CustomAssert.assertEquals(responseObject.getStatus(), expectedStatus);
        return this;
    }

    @Step("Validate Code From Response")
    public WhoamiResponseModel validateCodeFromResponse(int responseCody) {
        CustomAssert.assertEquals(getResponseCode(response), responseCody);
        return this;
    }

    @Step("Validate UserID From Response")
    public WhoamiResponseModel validateUserIDFromResponse(int user_id) {
        CustomAssert.assertEquals(responseObject.getData().getUserId(), user_id);
        return this;
    }

    @Step("Validate User Details Retrieved From Response")
    public WhoamiResponseModel validateUserDetailsRetrievedFromResponse(String fullName,String email,String mobile,String company, String dateOfBirth) {

        CustomAssert.assertEquals(responseObject.getData().getFullName(),fullName);
        CustomAssert.assertEquals(responseObject.getData().getEmail(),email);
        CustomAssert.assertEquals(responseObject.getData().getMobile(),mobile);
        CustomAssert.assertEquals(responseObject.getData().getCompany(),company);
        CustomAssert.assertEquals(responseObject.getData().getDateOfBirth(),dateOfBirth);
        return this;
    }

    @Step("validateNoUserDetailsExistInResponse")
    public WhoamiResponseModel validateNoUserDetailsExistInResponse() {
        CustomAssert.assertTrue(responseObject.getData() == null);
        return this;
    }

    //Getter Methods
    public WhoamiRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public WhoamiResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}
