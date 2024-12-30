package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
import objectModelsForAPIs.WhoamiRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojoClassesForAPIs.LoginResponsePojo;
import yehiaEngine.managers.JsonManager;

public class GetValidUserDetails {
    //Variables:
    String jsonFilePathForWhoamiTestData = "src/test/resources/APITestDataJsonFiles/WhoamiTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForWhoamiTestData);
    String sessionID;

    @BeforeMethod
    public void loginWithValidUser() throws JsonProcessingException {
        sessionID =
                new LoginRequestModel()
                        .prepareLoginRequestWithCredentials(json.getData("User1.Email"),json.getData("User1.Password"),json.getData("User1.Token"))
                        .sendLoginRequest()
                        .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.LoginSuccess")))
                        .validateStatusFromResponse(json.getData("Statuses.LoginSuccess"))
                        .validateMassageFromResponse(json.getData("Messages.LoginSuccess"))
                        .validateUserIDFromResponse(Integer.parseInt(json.getData("User1.UserID")))
                        .validateSessionIDExistsInResponse()
                        .getSessionID();
    }

    @Test
    public void getValidUserDetails() throws JsonProcessingException {
        new WhoamiRequestModel()
                .prepareWhoamiRequest(Integer.parseInt(json.getData("User1.UserID")))
                .sendWhoamiRequest(sessionID)
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.WhoamiSuccess")))
                .validateStatusFromResponse(json.getData("Statuses.WhoamiSuccess"))
                .validateMassageFromResponse(json.getData("Messages.WhoamiSuccess"))
                .validateUserIDFromResponse(Integer.parseInt(json.getData("User1.UserID")))
                .validateUserDetailsRetrievedFromResponse(json.getData("User1.FullName"),json.getData("User1.Email"),json.getData("User1.Mobile"),json.getData("User1.Company"),json.getData("User1.DateOfBirth"));
    }
}
