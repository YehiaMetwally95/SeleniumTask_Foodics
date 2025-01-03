package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

public class LoginWithInvalidUser {
    //Variables:
    String jsonFilePathForLoginTestData = "src/test/resources/APITestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLoginTestData);

    @Test
    public void loginWithInvalidUser() throws JsonProcessingException {
        new LoginRequestModel()
                .prepareLoginRequestWithCredentials(json.getData("InvalidUser.Username"),json.getData("InvalidUser.Password"))
                .sendLoginRequest()
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginFailure")))
                .validateMassageFromResponse(json.getData("Messages.LoginFailure"))
                .validateTokenNotExistInResponse();
    }
}
