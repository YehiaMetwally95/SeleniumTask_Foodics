package testCases.AmazonTests;

import baseTest.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;

public class FilterProducts extends BaseTest {
    //Variables
    final private String filterTestDataFilePath = "src/test/resources/TestDataJsonFiles/FilterTestData.json";
    final private JsonManager json = new JsonManager(filterTestDataFilePath);

    //Tests
    @Test
    public void filterVideoGames()
    {
        //Get The driver from Thread Local Isolated Driver
        WebDriver driver = getDriver(isolatedDriver);

        //Perform the Filter After Switching To English Language
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .switchToEnglishLanguage()
                //Navigate To VideoGames Page
                .navigateToVideoGamesFromMenu()
                .verifyProductsPageIsOpened(json.getData("Headers.EnglishProducts"))
                //Perform Sorting & Filtration
                .filterBySeller(json.getData("EnglishFilters.Seller"))
                .filterByCondition(json.getData("EnglishFilters.Condition"))
                .filterByFreeShipping(json.getData("EnglishFilters.FreeShipping"))
                .sortByValue(json.getData("EnglishFilters.Sorting"));
    }

}
