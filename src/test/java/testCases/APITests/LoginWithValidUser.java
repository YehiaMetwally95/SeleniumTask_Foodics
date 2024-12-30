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
    LoginResponsePojo responseObject;
    String sessionID;
    int userID;

    @Test
    public void loginWithValidUser() throws JsonProcessingException {
        responseObject =
        new LoginRequestModel()
                .prepareLoginRequestWithCredentials(json.getData("User1.Email"),json.getData("User1.Password"),json.getData("User1.Token"))
                .sendLoginRequest()
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginSuccess")))
                .validateStatusFromResponse(json.getData("Statuses.LoginSuccess"))
                .validateMassageFromResponse(json.getData("Messages.LoginSuccess"))
                .validateUserIDFromResponse(Integer.parseInt(json.getData("User1.UserID")))
                .validateSessionIDExistsInResponse()
                .getResponsePojoObject();

        sessionID = responseObject.getSessionId();
        userID = responseObject.getUserId();
    }
}
