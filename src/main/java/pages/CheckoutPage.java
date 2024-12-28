package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;
import yehiaEngine.elementActions.WebElementsActions;

import java.security.Key;

public class CheckoutPage {

    //Variables
    WebDriver driver;
    WebElementsActions action;
    int expectedTotalPrice;
    int actualTotalPrice;

    //Locators
    final private By checkoutHeader = By.id("nav-checkout-title-header-text");
    final private By deliveryAddressButton = By.name("add-new-address-non-mobile-tango-sasp-zero-address");
    final private By countryDropDown = By.id("address-ui-widgets-countryCode-dropdown-nativeId");
    final private By fullNameTextBox = By.id("address-ui-widgets-enterAddressFullName");
    final private By mobileNumberTextBox = By.id("address-ui-widgets-enterAddressPhoneNumber");
    final private By streetNameTextBox = By.id("address-ui-widgets-enterAddressLine1");
    final private By buildingNameTextBox = By.id("address-ui-widgets-enter-building-name-or-number");
    final private By areaTextBox = By.id("address-ui-widgets-enterAddressCity");
    final private By districtTextBox = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    final private By landmarkTextBox = By.id("address-ui-widgets-landmark");
    final private By submitAddressButton = By.xpath("//input[@aria-labelledby='address-ui-widgets-form-submit-button-announce']");
    final private By submitPaymentButton = By.name("ppw-widgetEvent:SetPaymentPlanSelectContinueEvent");
    final private By totalPrice = By.cssSelector("#subtotals-marketplace-table .grand-total-price");
    final private By changeAddressButton = By.xpath("//a[@data-csa-c-slot-id='checkout-change-shipaddressselect']");
    final private By changeAddressLink = By.cssSelector(".address-edit-link a");

    private By addressTypeLocator (String addressType) {
        if (addressType.equalsIgnoreCase("Home"))
            return By.id("address-ui-widgets-addr-details-res-radio-input");
        else if (addressType.equalsIgnoreCase("Office"))
            return By.id("address-ui-widgets-addr-details-com-radio-input");
        else
            return null;
    }
    private By paymentTypeLocator (String paymentType) {
        if (paymentType.equalsIgnoreCase("Buy Now/Pay Later"))
            return By.xpath("(//div[@data-a-input-name='ppw-instrumentRowSelection']//input)[1]");
        else if (paymentType.equalsIgnoreCase("Cash On Delivery"))
            return By.xpath("(//div[@data-a-input-name='ppw-instrumentRowSelection']//input)[2]");
        else
            return null;
    }


    //Constructor
    public CheckoutPage(WebDriver driver,int totalPrice)
    {
        this.driver = driver;
        action = new WebElementsActions(driver);
        this.expectedTotalPrice = totalPrice;
    }

    //Actions
    @Step("Fill Address Details")
    public CheckoutPage fillAddressDetails (String country, String fullName, String mobileNumber, String streetName, String buildingName, String area, String district,String landmark,String addressType)
    {
        action
                .press(changeAddressButton)
                .press(changeAddressLink)
                .selectFromDropdownByValue(countryDropDown,country)
                .type(fullNameTextBox,fullName)
                .type(mobileNumberTextBox,mobileNumber)
                .type(streetNameTextBox,streetName)
                .type(buildingNameTextBox,buildingName)
                .type(areaTextBox,area)
                .pressOnKey(areaTextBox,Keys.LEFT_ALT)
                .type(districtTextBox,district)
                .pressOnKey(districtTextBox,Keys.LEFT_ALT)
                .type(landmarkTextBox,landmark)
                .press(addressTypeLocator(addressType))
                .press(submitAddressButton);
        return this;
    }

    @Step("Fill Payment Details")
    public CheckoutPage fillPaymentDetails (String paymentType)
    {
        action
                .press(paymentTypeLocator(paymentType))
                .press(submitPaymentButton);
        return this;
    }

    //Validations
    @Step("Assert Total Price Is Correct")
    public CheckoutPage assertTotalPriceIsCorrect() {
        actualTotalPrice = Integer.parseInt(
                action.readText(totalPrice)
                        .trim()
                        .replace("EGP ","")
                        .replace(",", "")
                        .split("\\.")[0])-2;

        CustomAssert.assertEquals(actualTotalPrice,expectedTotalPrice);
        return this;
    }

    @Step("Verify Checkout Page is Opened")
    public CheckoutPage verifyCheckoutPageIsOpened(String header) {
        verifyCheckoutHeader(header);
        return this;
    }

    //Private Methods
    @Step("Verify Checkout Header")
    private CheckoutPage verifyCheckoutHeader(String header) {
        CustomSoftAssert.assertTrue(action.readText(checkoutHeader).contains(header));
        return this;
    }
}
