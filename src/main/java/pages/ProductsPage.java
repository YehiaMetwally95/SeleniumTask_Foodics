package pages;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import yehiaEngine.assertions.CustomSoftAssert;
import yehiaEngine.elementActions.WebElementsActions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static yehiaEngine.browserActions.WindowManager.refreshWindow;

public class ProductsPage {
    //Variables
    WebDriver driver;
    WebElementsActions action;
    List<WebElement> products;
    int totalPrice;

    //Getters
    @Getter
    List<String> productNames = new ArrayList<>();

    //Locators
    final private By sortingDropDown = By.id("s-result-sort-select");
    final private By productsHeader = By.cssSelector(".pageBanner h1");
    final private By productsPrice = By.xpath("//div[@class='a-section']//span[contains(@class,'a-price-whole')]");
    final private By cartLink = By.id("nav-cart");

    private By limitedTimeDealProduct(int i) {
        return By.xpath("(//span[contains(@class,'a-price-whole')])[" + i + "]//ancestor::div[@class='a-section']//span[contains(text(),'Limited time deal')]");
    }

    private By addToCartButton(int i) {
        return By.xpath("(//span[contains(@class,'a-price-whole')])[" + i + "]//ancestor::div[@class='a-section']//button[@class='a-button-text']");
    }

    private By productName(int i) {
        return By.xpath("(//span[contains(@class,'a-price-whole')])[" + i + "]//ancestor::div[@class='a-section']//div[@data-cy='title-recipe']");
    }

    private By conditionLocator(String conditionFilter) {
        return By.xpath("//ul[@aria-labelledby='p_n_condition-type-title']//a[contains(.,'" + conditionFilter + "')]");
    }

    private By IsFreeShippingLocator(String shippingFilter) {
        return By.xpath("//ul[@aria-labelledby='p_n_free_shipping_eligible-title']//a[contains(.,'" + shippingFilter + "')]");
    }

    private By sellerLocator(String sellerFilter) {
        return By.xpath("//ul[@aria-labelledby='p_6-title']//a[contains(.,'" + sellerFilter + "')]");
    }

    //Constructor
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        action = new WebElementsActions(driver);
    }

    //Actions
    @Step("Filter By Condition")
    public ProductsPage filterByCondition(String conditionFilter) {
        action.press(conditionLocator(conditionFilter));
        refreshWindow(driver);
        return this;
    }

    @Step("Filter By Free Shipping")
    public ProductsPage filterByFreeShipping(String freeShippingFilter) {
        action.press(IsFreeShippingLocator(freeShippingFilter));
        refreshWindow(driver);
        return this;
    }

    @Step("Filter By Seller")
    public ProductsPage filterBySeller(String sellerFilter) {
        action.press(sellerLocator(sellerFilter));
        refreshWindow(driver);
        return this;
    }

    @Step("Sort By Value")
    public ProductsPage sortByValue(String sortingValue) {
        action.selectFromDropdownByValue(sortingDropDown, sortingValue + "-rank");
        refreshWindow(driver);
        return this;
    }

    @Step("Add Products To Cart")
    public ProductsPage addProductsToCart(String price) {
        products = action.getAllMatchedElements(productsPrice);
        for (int i = 1; i <= products.size(); i++) {
            WebElement element = products.get(i - 1);
            if (Integer.parseInt(action.readText(element).replace(",", "")) < Integer.parseInt(price) &&
                    action.isElementNotDisplayedWithoutWait(limitedTimeDealProduct(i)) &&
                    action.isElementDisplayedWithoutWait(addToCartButton(i)))
            {
                action.press(addToCartButton(i));
                productNames.add(action.readText(productName(i)));
                totalPrice = totalPrice + Integer.parseInt(action.readText(element).replace(",", ""));
            }
        }
        return this;
    }

    @Step("Open Cart Page")
    public CartPage openCartPage() {
        action.press(cartLink);
        return new CartPage(driver,totalPrice);
    }

    @Step("Print Product Names")
    public ProductsPage printProductNames() {
        for (String item : productNames) {
            System.out.println(item);
        }
        return this;
    }

    //Validations
    @Step("Verify Products Page is Opened")
    public ProductsPage verifyProductsPageIsOpened(String header) {
        verifyProductsHeader(header);
        return this;
    }

    //Private Methods
    @Step("Verify Products Header")
    private ProductsPage verifyProductsHeader(String header) {
        CustomSoftAssert.assertEquals(action.readText(productsHeader), header);
        return this;
    }
}
