package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;
import yehiaEngine.elementActions.WebElementsActions;

import static yehiaEngine.browserActions.WindowManager.refreshWindow;

public class HomePage {
    //Variables
    WebDriver driver;
    WebElementsActions action;

    //Locators
    final private By signInMenu = By.id("nav-link-accountList");
    final private By signInButton = By.cssSelector("#nav-flyout-ya-signin .nav-action-signin-button");
    final private By languageMenu = By.id("icp-nav-flyout");
    final private By arabicLanguage = By.xpath("//div[@id='nav-flyout-icp']//a[@lang='ar-AE']");
    final private By englishLanguage = By.xpath("//div[@id='nav-flyout-icp']//a[@lang='en-AE']");
    final private By amazonLogo = By.id("nav-logo-sprites");
    final private By loggedInUsername = By.id("nav-link-accountList-nav-line-1");

    //Constructor
    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        action = new WebElementsActions(driver);
    }

    //Actions
    @Step("Switch To English Language")
    public HomePage switchToEnglishLanguage ()
    {
        action
                .hoverOnElement(languageMenu)
                .press(englishLanguage);
        refreshWindow(driver);
        return this;
    }

    @Step("Switch To Arabic Language")
    public HomePage switchToArabicLanguage ()
    {
        action
                .hoverOnElement(languageMenu)
                .press(arabicLanguage);
        refreshWindow(driver);
        return this;
    }

    @Step("Navigate To Login Page")
    public LoginPage navigateToLoginPage ()
    {
        action
                .hoverOnElement(signInMenu)
                .press(signInButton);
    return new LoginPage(driver);
    }

    //Validations
    @Step("Verify Home Page is Opened")
    public HomePage verifyHomePageIsOpened()
    {
        verifyAmazonLogoIsDisplayed();
        return this;
    }

    @Step("Assert User is Logged In")
    public HomePage assertUserIsLoggedIn(String userName)
    {
        CustomAssert.assertEquals(action.readText(loggedInUsername),"Hello, "+userName);
        return this;
    }

    //Private Methods
    @Step("Verify Amazon Logo Is Displayed")
    private HomePage verifyAmazonLogoIsDisplayed()
    {
        CustomSoftAssert.assertTrue(action.isElementDisplayed(amazonLogo));
        return this;
    }
}
