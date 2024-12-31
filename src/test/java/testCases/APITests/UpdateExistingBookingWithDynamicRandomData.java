package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.CreateBookingRequestModel;
import objectModelsForAPIs.GetBookingRequestModel;
import objectModelsForAPIs.LoginRequestModel;
import objectModelsForAPIs.UpdateBookingRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import pojoClassesForAPIs.UpdateBookingRequestPojo;
import pojoClassesForAPIs.UpdateBookingResponsePojo;
import yehiaEngine.managers.JsonManager;

public class UpdateExistingBookingWithDynamicRandomData {
    //Variables:
    String jsonFilePathForUpdateTestData = "src/test/resources/APITestDataJsonFiles/UpdateExistingBookingTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForUpdateTestData);
    CreateBookingResponsePojo createPojoObject;
    UpdateBookingRequestPojo updatePojoObject;
    String token;

    @BeforeMethod
    public void loginWithValidUser() throws JsonProcessingException {
        token =
        new LoginRequestModel()
                .prepareLoginRequestWithCredentials(json.getData("ValidUser.Username"), json.getData("ValidUser.Password"))
                .sendLoginRequest()
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginSuccess")))
                .validateTokenExistsInResponse()
                .getToken();

        //Store Token Into Json File
        json.setData("ValidUser.Token", token);
    }

    @BeforeMethod(dependsOnMethods = "loginWithValidUser")
    public void createNewBookingWithDynamicRandomData() throws JsonProcessingException {
        //Create New Booking Record
        createPojoObject =
        new CreateBookingRequestModel()
                //Prepare Booking Request with Dynamic Random Data & Send it
                .prepareCreateBookingRequestWithDynamicRandomData()
                .sendCreateBookingRequest()
                //Validate Booking Data from Response
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.CreateNewBookingSuccess")))
                .validateIdExistsInResponse()
                .validateBookingDetailsFromResponse()
                //Get Booking ID From Response for Next Requests
                .getResponsePojoObject();

        //Retrieve An Existing Booking Record
        new GetBookingRequestModel()
                //Prepare The Get Booking Request with Booking ID from Create Request & Send it
                .prepareGetBookingDetailsRequestWithID(createPojoObject.getBookingid())
                .sendGetBookingDetailsRequest()
                //Validate Booking Data from Get Response Against Booking Data from Create Request
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.GetBookingSuccess")))
                .validateBookingDetailsFromResponse(createPojoObject.getBooking().getFirstname(),
                        createPojoObject.getBooking().getLastname(), createPojoObject.getBooking().getTotalprice(),
                        createPojoObject.getBooking().isDepositpaid(), createPojoObject.getBooking().getBookingdates().getCheckin(),
                        createPojoObject.getBooking().getBookingdates().getCheckout(), createPojoObject.getBooking().getAdditionalneeds());
    }

    @Test
    public void updateExistingBookingWithDynamicRandomData() throws JsonProcessingException {
        //Update The Old Booking Record
        updatePojoObject =
                new UpdateBookingRequestModel()
                //Prepare Update Booking Request with Static Data from Json File
                .prepareUpdateBookingRequestWithDynamicRandomData()
                //Send Update Booking Request with The Booking ID from Create & Token from Login
                .sendUpdateBookingRequest(createPojoObject.getBookingid(),token)
                //Validate Booking Data from Response are Updated
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.UpdateBookingSuccess")))
                .validateUpdatedBookingDetailsFromResponse()
                .getRequestPojoObject();

        //Retrieve The Updated Booking Record
        new GetBookingRequestModel()
                //Prepare The Get Booking Request with Booking ID from Create Request & Send it
                .prepareGetBookingDetailsRequestWithID(createPojoObject.getBookingid())
                .sendGetBookingDetailsRequest()
                //Validate Booking Data from Get Response Against Booking Data from Create Request
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.GetBookingSuccess")))
                .validateBookingDetailsFromResponse(updatePojoObject.getFirstname(),
                        updatePojoObject.getLastname(), updatePojoObject.getTotalprice(),
                        updatePojoObject.isDepositpaid(), updatePojoObject.getBookingdates().getCheckin(),
                        updatePojoObject.getBookingdates().getCheckout(), updatePojoObject.getAdditionalneeds());
    }
}
