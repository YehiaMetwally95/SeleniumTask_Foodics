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
                //Pass invalid User ID that not exists
                .prepareWhoamiRequest(json.getData("User2.UserID"))
                //Pass invalid Token
                .sendWhoamiRequest(json.getData("User2.Token"))
                .validateCodeFromResponse(Integer.parseInt(json.getData("ResponseCodes.WhoamiFailure")))
                .validateMassageFromResponse(json.getData("Messages.WhoamiFailure"))
                .validateNoUserDetailsExistInResponse();
    }
}
