package baseTest;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

import static yehiaEngine.browserActions.WindowManager.closeAllWindows;
import static yehiaEngine.browserActions.WindowManager.navigateToURL;
import static yehiaEngine.driverManager.BrowserFactory.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

public class BaseTest {
    //Variables
    protected ThreadLocal<RemoteWebDriver> isolatedDriver;

    @Parameters({"BrowserType"})
    @BeforeMethod
    public void setUpAndOpenBrowser(@Optional String browserType) throws MalformedURLException {
        //Open Browser
        isolatedDriver = openBrowser(browserType);

        //Navigate to Website URL
        navigateToURL(getDriver(isolatedDriver),getPropertiesValue("baseUrlWeb"));
    }

    @AfterMethod
    public void closeBrowser()
    {
        //Close All Browser Tabs
        closeAllWindows(getDriver(isolatedDriver));

        //Remove the Isolated Driver from Memory
        removeIsolatedDriver(isolatedDriver);
    }
}
