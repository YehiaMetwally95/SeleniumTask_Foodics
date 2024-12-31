package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.LoginRequestPojo;
import pojoClassesForAPIs.LoginResponsePojo;
import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;


public class LoginRequestModel {

    //Variables
    String loginEndpoint = getPropertiesValue("baseUrlApi")+"/auth";
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    LoginRequestPojo requestObject;
    LoginResponsePojo responseObject;

    //Method to set Request Body using Builder Pattern
    @Step("Prepare Login Request Body With Credentials")
    public LoginRequestModel prepareLoginRequestWithCredentials(String userName,String passWord) {
        requestObject = LoginRequestPojo.builder()
                .username(userName)
                .password(passWord)
                .build();
        return this;
    }

    //Method to Execute Login Request
    @Step("Send Request of Login")
    public LoginResponseModel sendLoginRequest() throws JsonProcessingException {
        //Send Login Request
        response = MakeRequest(POST, loginEndpoint, requestObject, JSON);

        //Get Response Body as JsonString from Response
        jsonBodyAsString = getResponseBody(response);

        //Deserialize Response JsonString into Pojo Object
        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, LoginResponsePojo.class);

        return new LoginResponseModel(requestObject, responseObject,response);
    }
}
