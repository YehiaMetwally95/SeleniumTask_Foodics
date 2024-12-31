package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.UpdateBookingRequestPojo;
import pojoClassesForAPIs.UpdateBookingResponsePojo;

import java.util.List;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.ApisManager.AuthType.CookieAuth;
import static yehiaEngine.managers.ApisManager.ContentType.JSON;
import static yehiaEngine.managers.ApisManager.MethodType.POST;
import static yehiaEngine.managers.ApisManager.MethodType.PUT;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;


public class UpdateBookingRequestModel {

    //Variables
    String UpdateBookingEndpoint = getPropertiesValue("baseUrlApi") + "/booking/";
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    UpdateBookingRequestPojo requestObject;
    UpdateBookingResponsePojo responseObject;

    //Method to set Request Body By Static Data using Builder Pattern
    @Step("Prepare Update Booking Request Body With Static Data")
    public UpdateBookingRequestModel prepareUpdateBookingRequestWithStaticData(String firstName, String lastName, String totalPrice, String depositPaid, String checkInDate, String checkOutDate, String additionalNeeds) {
        requestObject = UpdateBookingRequestPojo.builder()
                .firstname(firstName)
                .lastname(lastName)
                .totalprice(Integer.parseInt(totalPrice))
                .depositpaid(Boolean.parseBoolean(depositPaid))
                .additionalneeds(additionalNeeds)
                .bookingdates(
                        UpdateBookingRequestPojo.BookingDates.builder()
                                .checkin(checkInDate)
                                .checkout(checkOutDate)
                                .build())
                .build();
        return this;
    }

    @Step("Prepare Update Booking Request Body With Dynamic Random Data")
    public UpdateBookingRequestModel prepareUpdateBookingRequestWithDynamicRandomData() {
        requestObject = UpdateBookingRequestPojo.builder()
                .firstname(generateName())
                .lastname(generateName())
                .totalprice(Integer.parseInt(generateUniqueInteger()))
                .depositpaid(Boolean.parseBoolean(generateItemFromList(List.of("true,false"))))
                .additionalneeds(generateDescription())
                .bookingdates(
                        UpdateBookingRequestPojo.BookingDates.builder()
                                .checkin(generatePreviousDate("yyyy-MM-dd"))
                                .checkout(generateFutureDate("yyyy-MM-dd"))
                                .build())
                .build();
        return this;
    }

    //Method to Execute Update Booking Request
    @Step("Send Request of Update Booking")
    public UpdateBookingResponseModel sendUpdateBookingRequest(int bookingID,String token) throws JsonProcessingException {
        //Send Update Booking Request
        response = MakeAuthRequest(PUT, UpdateBookingEndpoint+bookingID, requestObject, JSON,CookieAuth,token,null,null);

        //Get Response Body as JsonString from Response
        jsonBodyAsString = getResponseBody(response);

        //Deserialize Response JsonString into Pojo Object
        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, UpdateBookingResponsePojo.class);

        return new UpdateBookingResponseModel(requestObject, responseObject, response);
    }
}
