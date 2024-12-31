package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.CreateBookingRequestModel;
import objectModelsForAPIs.GetBookingRequestModel;
import org.testng.annotations.Test;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import yehiaEngine.managers.JsonManager;

public class CreateNewBookingWithStaticData {
    //Variables:
    String jsonFilePathForCreateTestData = "src/test/resources/APITestDataJsonFiles/CreateNewBookingTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForCreateTestData);
    int bookingID;

    @Test
    public void createNewBookingWithStaticData() throws JsonProcessingException {
        //Create New Booking Record
        bookingID =
                new CreateBookingRequestModel()
                        //Prepare Booking Request with Static Data from Json File & Send it
                        .prepareCreateBookingRequestWithStaticData(json.getData("BookingData.FirstName"),
                                json.getData("BookingData.LastName"), json.getData("BookingData.TotalPrice"),
                                json.getData("BookingData.DepositPaid"), json.getData("BookingData.CheckInDate"),
                                json.getData("BookingData.CheckOutDate"), json.getData("BookingData.AdditionalNeeds"))
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
                .validateBookingDetailsFromResponse(json.getData("BookingData.FirstName"),
                        json.getData("BookingData.LastName"), Integer.parseInt(json.getData("BookingData.TotalPrice")),
                        Boolean.parseBoolean(json.getData("BookingData.DepositPaid")), json.getData("BookingData.CheckInDate"),
                        json.getData("BookingData.CheckOutDate"), json.getData("BookingData.AdditionalNeeds"));
    }
}
