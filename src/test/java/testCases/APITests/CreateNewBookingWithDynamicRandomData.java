package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.CreateBookingRequestModel;
import objectModelsForAPIs.GetBookingRequestModel;
import org.testng.annotations.Test;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import yehiaEngine.managers.JsonManager;

public class CreateNewBookingWithDynamicRandomData {
    //Variables:
    String jsonFilePathForCreateTestData = "src/test/resources/APITestDataJsonFiles/CreateNewBookingTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForCreateTestData);
    CreateBookingResponsePojo createPojoObject;

    @Test
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
                        //Get PojoObject From Response to get ID & Booking Input Data for Next Requests
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
}
