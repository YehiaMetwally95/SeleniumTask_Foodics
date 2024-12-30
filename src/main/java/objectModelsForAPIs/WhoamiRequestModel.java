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
    Map<String,String > queryParam;

    //ObjectsFromPojoClasses
    WhoamiRequestPojo requestObject;
    WhoamiResponsePojo responseObject;


    //Method to set Request with query parameter of userID
    @Step("Set Request with query parameter of email")
    public WhoamiRequestModel prepareWhoamiRequest(String userEmail) {
        queryParam = new HashMap<>();
        queryParam.put("email",userEmail);
        return this;
    }

    //Method to Execute WhoAmI Request
    @Step("Send Request of WhoAmI")
    public WhoamiResponseModel sendWhoamiRequest(String token) throws JsonProcessingException {
        response =
                GetAuthRequest(whoamiEndpoint, queryParam, "Bearer",null,null,token);
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, WhoamiResponsePojo.class);

        return new WhoamiResponseModel(requestObject, responseObject,response);
    }
}
