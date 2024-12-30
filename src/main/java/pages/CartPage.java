package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;
import yehiaEngine.elementActions.WebElementsActions;

import java.util.ArrayList;
import java.util.List;

import static yehiaEngine.browserActions.WindowManager.refreshWindow;

public class CartPage {
    //Variables
    WebDriver driver;
    WebElementsActions action;
    List<String> expectedProductNames;
    int totalPrice;

    //Locators
    final private By cartHeader = By.id("sc-active-items-header");
    final private By checkOutLink = By.name("proceedToRetailCheckout");
    final private By deleteButton = By.xpath("//input[@data-action='delete']");
    private By productNameLocator (String name){
        return By.xpath("//div[@class='sc-list-item-content']//a[contains(.,'"+name+"')]");
    }

    //Constructor
    public CartPage(WebDriver driver,int totalPrice)
    {
        this.driver = driver;
        action = new WebElementsActions(driver);
        this.totalPrice = totalPrice;
    }

    //Actions
    @Step("Open Checkout Page")
    public CheckoutPage openCheckoutPage ()
    {
        action.press(checkOutLink);
        return new CheckoutPage(driver,totalPrice);
    }

    @Step("Remove All Old Products from Cart")
    public CartPage removeAllOldProductsFromCart() throws InterruptedException {
        while(action.isElementDisplayedWithoutWait(deleteButton)) {
            action.press(deleteButton);
            refreshWindow(driver);
        }
        return this;
    }

    //Validations
    @Step("Assert All Products Are Added To cart")
    public CartPage assertAllProductAreAddedToCart ()
    {
        expectedProductNames = new ProductsPage(driver).getProductNames();
        for (String productName : expectedProductNames) {
            CustomAssert.assertTrue(action.isElementDisplayed(productNameLocator(productName)));
        }
        return this;
    }

    @Step("Verify All Products Are Added To cart")
    public CartPage verifyAllProductAreAddedToCart ()
    {
        expectedProductNames = new ProductsPage(driver).getProductNames();
        for (String productName : expectedProductNames) {
            CustomSoftAssert.assertTrue(action.isElementDisplayed(productNameLocator(productName)));
        }
        return this;
    }

    @Step("Verify Cart Page is Opened")
    public CartPage verifyCartPageIsOpened(String header) {
        verifyCartHeader(header);
        return this;
    }

    //Private Methods
    @Step("Verify Cart Header")
    private CartPage verifyCartHeader(String header) {
        CustomSoftAssert.assertTrue(action.readText(cartHeader).contains(header));
        return this;
    }

}
