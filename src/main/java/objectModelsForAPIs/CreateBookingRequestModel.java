package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.CreateBookingRequestPojo;
import pojoClassesForAPIs.CreateBookingResponsePojo;

import java.util.List;

import static yehiaEngine.utilities.RandomDataGenerator.*;

import static yehiaEngine.managers.ApisManager.ContentType.JSON;
import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.ApisManager.MethodType.POST;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;


public class CreateBookingRequestModel {

    //Variables
    String createBookingEndpoint = getPropertiesValue("baseUrlApi") + "/booking";
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    CreateBookingRequestPojo requestObject;
    CreateBookingResponsePojo responseObject;

    //Method to set Request Body By Static Data using Builder Pattern
    @Step("Prepare Create Booking Request Body With Static Data")
    public CreateBookingRequestModel prepareCreateBookingRequestWithStaticData(String firstName, String lastName, String totalPrice, String depositPaid, String checkInDate, String checkOutDate, String additionalNeeds) {
        requestObject = CreateBookingRequestPojo.builder()
                .firstname(firstName)
                .lastname(lastName)
                .totalprice(Integer.parseInt(totalPrice))
                .depositpaid(Boolean.parseBoolean(depositPaid))
                .additionalneeds(additionalNeeds)
                .bookingdates(
                        CreateBookingRequestPojo.BookingDates.builder()
                                .checkin(checkInDate)
                                .checkout(checkOutDate)
                                .build())
                .build();
        return this;
    }

    @Step("Prepare Create Booking Request Body With Dynamic Random Data")
    public CreateBookingRequestModel prepareCreateBookingRequestWithDynamicRandomData() {
        requestObject = CreateBookingRequestPojo.builder()
                .firstname(generateName())
                .lastname(generateName())
                .totalprice(Integer.parseInt(generateUniqueInteger()))
                .depositpaid(Boolean.parseBoolean(generateItemFromList(List.of("true,false"))))
                .additionalneeds(generateDescription())
                .bookingdates(
                        CreateBookingRequestPojo.BookingDates.builder()
                                .checkin(generatePreviousDate("yyyy-MM-dd"))
                                .checkout(generateFutureDate("yyyy-MM-dd"))
                                .build())
                .build();
        return this;
    }

    //Method to Execute Create Booking Request
    @Step("Send Request of Create Booking")
    public CreateBookingResponseModel sendCreateBookingRequest() throws JsonProcessingException {
        //Send Create Booking Request
        response = MakeRequest(POST, createBookingEndpoint, requestObject, JSON);

        //Get Response Body as JsonString from Response
        jsonBodyAsString = getResponseBody(response);

        //Deserialize Response JsonString into Pojo Object
        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, CreateBookingResponsePojo.class);

        return new CreateBookingResponseModel(requestObject, responseObject, response);
    }
}
