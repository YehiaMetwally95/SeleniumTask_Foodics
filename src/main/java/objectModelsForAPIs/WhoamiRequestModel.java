package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.WhoamiRequestPojo;
import pojoClassesForAPIs.WhoamiResponsePojo;

import java.util.HashMap;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.GetAuthRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class WhoamiRequestModel {

    //Variables
    String whoamiEndpoint = getPropertiesValue("baseUrlApi")+"/whoami";
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;
    Map<String,Integer > queryParam;

    //ObjectsFromPojoClasses
    WhoamiRequestPojo requestObject;
    WhoamiResponsePojo responseObject;


    //Method to set Request with query parameter of userID
    @Step("Set Request with query parameter of userID")
    public WhoamiRequestModel prepareWhoamiRequest(int userId) {
        queryParam = new HashMap<>();
        queryParam.put("id",userId);
        return this;
    }

    //Method to Execute WhoAmI Request
    @Step("Send Request of WhoAmI")
    public WhoamiResponseModel sendWhoamiRequest(String sessionID) throws JsonProcessingException {
        response =
                GetAuthRequest(whoamiEndpoint, queryParam, "Session-ID",null,null,sessionID);
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, WhoamiResponsePojo.class);

        return new WhoamiResponseModel(requestObject, responseObject,response);
    }
}
