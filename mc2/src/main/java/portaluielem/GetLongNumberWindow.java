package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = "div.create-integration-container")
public class GetLongNumberWindow extends BasePortalWindow {

    @FindBy(css = "span.form-control")
    private WebElement selectCountryDropdown;

    @FindBy(css = "input.form-control")
    private WebElement selectCountryInput;

    @FindBy(css = "div[ng-model='availableAreaCode'] span.form-control")
    private WebElement selectStateDropdown;

    @FindBy(css = "div[ng-model='availableAreaCode'] input.form-control")
    private WebElement selectStateInput;

    @FindBy(css = "div.available-number")
    private List<WebElement> availableNumbers;

    private String subsriptionXPATH = "//div[@ng-model='phoneNumber.subscriptionPlan'][following-sibling::label[contains(text(), '%s')]]";

    @Step(value = "Select county for a Long Number")
    public GetLongNumberWindow selectCountry(String country, String state){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        clickElem(this.getCurrentDriver(), selectCountryDropdown, 5, "'Select county' dropdown");
        inputText(this.getCurrentDriver(), selectCountryInput, 3, "'Select county' input", country);
        selectCountryInput.sendKeys(Keys.ENTER);
        clickElem(this.getCurrentDriver(), selectStateDropdown, 2, "'Select State'");
        inputText(this.getCurrentDriver(), selectStateInput, 3, "'Select state' input", state);
        selectStateInput.sendKeys(Keys.ENTER);
        clickNextButton();
        waitWhileProcessing(this.getCurrentDriver(), 1, 2);
        return this;
    }

    @Step(value = "Select available number")
    public String selectNumber(){
        WebElement elem = availableNumbers.get(0);
        String phone = elem.getText();
        elem.findElement(By.cssSelector("span[cl-checkbox]")).click();
        clickNextButton();
        waitWhileProcessing(this.getCurrentDriver(), 1, 2);
        return phone;
    }

    @Step(value = "Select subscription plan")
    public GetLongNumberWindow selectSubscription(String subsription){
        String locator = String.format(subsriptionXPATH, subsription);
        findElemByXPATH(this.getCurrentDriver(), locator).click();
        clickNextButton();
        return this;
    }






}
