package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;
import yehiaEngine.elementActions.WebElementsActions;

public class LoginPage {
    //Variables
    WebDriver driver;
    WebElementsActions action;

    //Locators
    final private By emailTextBox = By.id("ap_email");
    final private By mobileNumberTextBox = By.id("ap_email");
    final private By passwordTextBox = By.id("ap_password");
    final private By continueButton=By.xpath("//input[@id='continue']");
    final private By signInButton=By.id("signInSubmit");
    final private By signInHeader = By.cssSelector(".a-box h1");

    //Constructor
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        action = new WebElementsActions(driver);
    }

    //Actions
    @Step("Login With Email")
    public HomePage loginWithEmail (String email,String password)
    {
        enterEmail(email)
                .clickOnContinueButton()
                .enterPassword(password)
                .clickOnSignInButton();
        return new HomePage(driver);
    }

    @Step("Login With Mobile Number")
    public HomePage loginWithMobileNumber (String mobileNumber,String password)
    {
        enterMobileNumber(mobileNumber)
                .clickOnContinueButton()
                .enterPassword(password)
                .clickOnSignInButton();
        return new HomePage(driver);
    }

    //Validations
    @Step("Verify Login Page is Opened")
    public LoginPage verifyLoginPageIsOpened(String header)
    {
        verifyLoginHeaderIsDisplayed(header);
        return this;
    }

    //Private Methods
    @Step("Verify Login Header is Displayed")
    private LoginPage verifyLoginHeaderIsDisplayed (String header)
    {
        CustomSoftAssert.assertTrue(action.readText(signInHeader).contains(header));
        return this;
    }

    @Step("Enter Mobile Number")
    private LoginPage enterMobileNumber (String mobileNumber)
    {
        action.type(mobileNumberTextBox,mobileNumber);
        return this;
    }

    @Step("Enter Email")
    private LoginPage enterEmail (String email)
    {
        action.type(emailTextBox,email);
        return this;
    }

    @Step("Enter Password")
    private LoginPage enterPassword (String password)
    {
        action.type(passwordTextBox,password);
        return this;
    }

    @Step("Click on Continue Button")
    private LoginPage clickOnContinueButton ()
    {
        action.press(continueButton);
        return this;
    }

    @Step("Click on SignIn Button")
    private HomePage clickOnSignInButton ()
    {
        action.press(signInButton);
        return new HomePage(driver);
    }

}
