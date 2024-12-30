package testCases.APITests;

import com.fasterxml.jackson.core.JsonProcessingException;
import objectModelsForAPIs.LoginRequestModel;
import objectModelsForAPIs.WhoamiRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

public class GetUnAuthorizedUserDetails {
    //Variables:
    String jsonFilePathForWhoamiTestData = "src/test/resources/APITestDataJsonFiles/WhoamiTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForWhoamiTestData);

    @Test
    public void getValidUserDetails() throws JsonProcessingException {
        new WhoamiRequestModel()
                .prepareWhoamiRequest(Integer.parseInt(json.getData("User1.UserID")))
                .sendWhoamiRequest(null)
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.WhoamiFailure")))
                .validateStatusFromResponse(json.getData("Statuses.WhoamiFailure"))
                .validateMassageFromResponse(json.getData("Messages.WhoamiFailure"))
                .validateNoUserDetailsExistInResponse();
    }
}
