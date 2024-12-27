package testCases.AmazonTests;

import baseTest.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;

public class LoginWithValidUser extends BaseTest {
    //Variables
    final private String loginTestDataFilePath = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    final private JsonManager json = new JsonManager(loginTestDataFilePath);

    //Tests
    @Test
    public void loginWithMobileNumber()
    {
        //Get The driver from Thread Local Isolated Driver
        WebDriver driver = getDriver(isolatedDriver);

        //Perform Login with Mobile Number After Switching To English Language
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .switchToEnglishLanguage()
                .navigateToLoginPage()
                .verifyLoginPageIsOpened(json.getData("Headers.EnglishSignIn"))
                .loginWithMobileNumber(json.getData("Users[0].MobileNumber"),json.getData("Users[0].Password"))
                .assertUserIsLoggedIn(json.getData("Users[0].Name"));
    }
}
