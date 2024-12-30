package testCases.AmazonTests;

import baseTest.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;

public class AddProductsToCart extends BaseTest {
    //Variables
    final private String AddToCartTestDataFilePath = "src/test/resources/AmazonTestDataJsonFiles/AddToCartTestData.json";
    final private JsonManager json = new JsonManager(AddToCartTestDataFilePath);

    //Tests
    @Test
    public void addVideoGamesToCart() throws InterruptedException {
        //Get The driver from Thread Local Isolated Driver
        WebDriver driver = getDriver(isolatedDriver);

        //Perform the Filter After Switching To English Language
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .switchToEnglishLanguage()
                //Navigate To VideoGames Page
                .navigateToVideoGamesFromMenu()
                .verifyProductsPageIsOpened(json.getData("Headers.EnglishProducts"))
                //Perform Filtration
                .filterBySeller(json.getData("EnglishFilters.Seller"))
                .filterByCondition(json.getData("EnglishFilters.Condition"))
                .filterByFreeShipping(json.getData("EnglishFilters.FreeShipping"))
                //Add All Products that less than 15k to Cart
                .addProductsToCart(json.getData("Price"))
                //Open Cart Page and Assert if all Products are Added to Cart by Their Names
                .openCartPage()
                .verifyCartPageIsOpened(json.getData("Headers.EnglishCart"))
                .assertAllProductAreAddedToCart();
    }
}
