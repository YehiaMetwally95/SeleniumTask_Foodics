package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
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
                .prepareLoginRequestWithCredentials(json.getData("User1.Email"),json.getData("User1.Password"))
                .sendLoginRequest()
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginSuccess")))
                .validateMassageFromResponse(json.getData("Messages.LoginSuccess"))
                .validateMobileFromResponse(json.getData("User1.Mobile"))
                .validateTokenExistsInResponse()
                .getToken();
    }
}
