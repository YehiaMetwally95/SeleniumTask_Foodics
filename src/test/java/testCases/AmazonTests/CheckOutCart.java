package testCases.AmazonTests;

import baseTest.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductsPage;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;

public class CheckOutCart extends BaseTest {
    //Variables
    final private String CheckOutTestDataFilePath = "src/test/resources/TestDataJsonFiles/CheckOutTestData.json";
    final private JsonManager json = new JsonManager(CheckOutTestDataFilePath);

    @BeforeMethod
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

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "loginWithMobileNumber")
    public void resetCart() throws InterruptedException {
        //Get The driver from Thread Local Isolated Driver
        WebDriver driver = getDriver(isolatedDriver);

        new HomePage(driver)
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test
    @Test
    public void checkoutVideoGames() throws InterruptedException {
        //Get The driver from Thread Local Isolated Driver
        WebDriver driver = getDriver(isolatedDriver);

        new HomePage(driver)
                //Navigate To VideoGames Page
                .navigateToVideoGamesFromMenu()
                .verifyProductsPageIsOpened(json.getData("Headers.EnglishProducts"))
                //Perform Sorting & Filtration
                .filterBySeller(json.getData("EnglishFilters.Seller"))
                .filterByCondition(json.getData("EnglishFilters.Condition"))
                .filterByFreeShipping(json.getData("EnglishFilters.FreeShipping"))
                .sortByValue(json.getData("EnglishFilters.Sorting"))
                //Add All Products that less than 15k to Cart
                .addProductsToCart(json.getData("Price"))
                //Open Cart Page and Verify if all Products are Added to Cart by Their Names
                .openCartPage()
                .verifyCartPageIsOpened(json.getData("Headers.EnglishCart"))
                .verifyAllProductAreAddedToCart()
                //Open Checkout Page & Fill Address / Payment Details
                .openCheckoutPage()
                .verifyCheckoutPageIsOpened(json.getData("Headers.EnglishCheckout"))
                .fillAddressDetails(json.getData("AddressDetails.Country"),json.getData("AddressDetails.FullName"),json.getData("AddressDetails.MobileNumber"),json.getData("AddressDetails.StreetName"),json.getData("AddressDetails.BuildingName"),json.getData("AddressDetails.AreaName"),json.getData("AddressDetails.DistrictName"),json.getData("AddressDetails.Landmark"),json.getData("AddressDetails.AddressType"))
                .fillPaymentDetails(json.getData("PaymentDetails.PaymentType"))
                //Assert The Total Price is Correct in the Checkout
                .assertTotalPriceIsCorrect();
    }

}
