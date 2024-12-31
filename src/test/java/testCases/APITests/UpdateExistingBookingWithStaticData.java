package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.CreateBookingRequestModel;
import objectModelsForAPIs.GetBookingRequestModel;
import objectModelsForAPIs.LoginRequestModel;
import objectModelsForAPIs.UpdateBookingRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import yehiaEngine.managers.JsonManager;

public class UpdateExistingBookingWithStaticData {
    //Variables:
    String jsonFilePathForUpdateTestData = "src/test/resources/APITestDataJsonFiles/UpdateExistingBookingTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForUpdateTestData);
    int bookingID;
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
    public void createNewBookingWithStaticData() throws JsonProcessingException {
        //Create New Booking Record
        bookingID =
        new CreateBookingRequestModel()
                //Prepare Booking Request with Static Data from Json File & Send it
                .prepareCreateBookingRequestWithStaticData(json.getData("OldBookingData.FirstName"),
                        json.getData("OldBookingData.LastName"), json.getData("OldBookingData.TotalPrice"),
                        json.getData("OldBookingData.DepositPaid"), json.getData("OldBookingData.CheckInDate"),
                        json.getData("OldBookingData.CheckOutDate"), json.getData("OldBookingData.AdditionalNeeds"))
                .sendCreateBookingRequest()
                //Validate Booking Data from Response
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.CreateNewBookingSuccess")))
                .validateIdExistsInResponse()
                .validateBookingDetailsFromResponse()
                //Get Booking ID From Response for Next Requests
                .getId();

        //Retrieve An Existing Booking Record
        new GetBookingRequestModel()
                //Prepare The Get Booking Request with Booking ID from Create Request & Send it
                .prepareGetBookingDetailsRequestWithID(bookingID)
                .sendGetBookingDetailsRequest()
                //Validate Booking Data from Get Response Against Booking Data from Create Request
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.GetBookingSuccess")))
                .validateBookingDetailsFromResponse(json.getData("OldBookingData.FirstName"),
                        json.getData("OldBookingData.LastName"), Integer.parseInt(json.getData("OldBookingData.TotalPrice")),
                        Boolean.parseBoolean(json.getData("OldBookingData.DepositPaid")), json.getData("OldBookingData.CheckInDate"),
                        json.getData("OldBookingData.CheckOutDate"), json.getData("OldBookingData.AdditionalNeeds"));
    }

    @Test
    public void updateExistingBookingWithStaticData() throws JsonProcessingException {
        //Update The Old Booking Record
        new UpdateBookingRequestModel()
                //Prepare Update Booking Request with Static Data from Json File
                .prepareUpdateBookingRequestWithStaticData(json.getData("NewBookingData.FirstName"),
                        json.getData("NewBookingData.LastName"), json.getData("NewBookingData.TotalPrice"),
                        json.getData("NewBookingData.DepositPaid"), json.getData("NewBookingData.CheckInDate"),
                        json.getData("NewBookingData.CheckOutDate"), json.getData("NewBookingData.AdditionalNeeds"))
                //Send Update Booking Request with The Booking ID from Create & Token from Login
                .sendUpdateBookingRequest(bookingID,token)
                //Validate Booking Data from Response are Updated
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.UpdateBookingSuccess")))
                .validateUpdatedBookingDetailsFromResponse();

        //Retrieve The Updated Booking Record
        new GetBookingRequestModel()
                //Prepare The Get Booking Request with Booking ID from Create Request & Send it
                .prepareGetBookingDetailsRequestWithID(bookingID)
                .sendGetBookingDetailsRequest()
                //Validate Booking Data from Get Response Against Booking Data from Create Request
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.GetBookingSuccess")))
                .validateBookingDetailsFromResponse(json.getData("NewBookingData.FirstName"),
                        json.getData("NewBookingData.LastName"), Integer.parseInt(json.getData("NewBookingData.TotalPrice")),
                        Boolean.parseBoolean(json.getData("NewBookingData.DepositPaid")), json.getData("NewBookingData.CheckInDate"),
                        json.getData("NewBookingData.CheckOutDate"), json.getData("NewBookingData.AdditionalNeeds"));
    }
}
