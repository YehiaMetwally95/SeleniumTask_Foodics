package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pojoClassesForAPIs.LoginResponsePojo;
import yehiaEngine.managers.JsonManager;

public class LoginWithValidUser {
    //Variables:
    String jsonFilePathForLoginTestData = "src/test/resources/APITestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLoginTestData);
    String token;

    @Test
    public void loginWithValidUser() throws JsonProcessingException {
        token =
        new LoginRequestModel()
                .prepareLoginRequestWithCredentials(json.getData("ValidUser.Username"),json.getData("ValidUser.Password"))
                .sendLoginRequest()
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginSuccess")))
                .validateTokenExistsInResponse()
                .getToken();

        //Store Token Into Json File
        json.setData("ValidUser.Token",token);
    }
}
