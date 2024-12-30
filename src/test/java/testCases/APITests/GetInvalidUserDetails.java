package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
import objectModelsForAPIs.WhoamiRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

public class GetInvalidUserDetails {
    //Variables:
    String jsonFilePathForWhoamiTestData = "src/test/resources/APITestDataJsonFiles/WhoamiTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForWhoamiTestData);

    @Test
    public void getInvalidUserDetails() throws JsonProcessingException {
        new WhoamiRequestModel()
                .prepareWhoamiRequest(Integer.parseInt(json.getData("User2.UserID")))
                .sendWhoamiRequest(json.getData("User2.SessionID"))
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.WhoamiFailure")))
                .validateStatusFromResponse(json.getData("Statuses.WhoamiFailure"))
                .validateMassageFromResponse(json.getData("Messages.WhoamiFailure"))
                .validateNoUserDetailsExistInResponse();
    }
}
