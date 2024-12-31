package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.CreateBookingRequestPojo;
import pojoClassesForAPIs.CreateBookingResponsePojo;
import pojoClassesForAPIs.GetBookingRequestPojo;
import pojoClassesForAPIs.GetBookingResponsePojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.ApisManager.ContentType.JSON;
import static yehiaEngine.managers.ApisManager.MethodType.POST;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;


public class GetBookingRequestModel {

    //Variables
    String getBookingEndpoint = getPropertiesValue("baseUrlApi") + "/booking";
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;
    Map<String,Integer> idMap;

    //ObjectsFromPojoClasses
    GetBookingRequestPojo requestObject;
    GetBookingResponsePojo responseObject;

    @Step("Prepare Get Booking Details Request With Booking ID")
    public GetBookingRequestModel prepareGetBookingDetailsRequestWithID(int bookingID) {
        idMap = new HashMap<>();
        idMap.put("id",bookingID);
        return this;
    }

    //Method to Execute Get Booking Details Request
    @Step("Send Get Booking Details Request")
    public GetBookingResponseModel sendGetBookingDetailsRequest() throws JsonProcessingException {
        //Send Get Booking Details Request
        response = GetRequest(getBookingEndpoint,ParameterType.PATH,idMap);

        //Get Response Body as JsonString from Response
        jsonBodyAsString = getResponseBody(response);

        //Deserialize Response JsonString into Pojo Object

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, GetBookingResponsePojo.class);

        return new GetBookingResponseModel(requestObject, responseObject, response);
    }
}
