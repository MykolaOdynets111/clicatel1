package portaluielem;

import abstractclasses.AbstractWidget;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class GetStartedSection extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(xpath = ".//div[contains(@class, 'cl-media')]/following-sibling::p")
    private WebElement cardTitle;

    @FindBy(css = "button.status-button.ng-scope span")
    private WebElement cardStatus;

    @FindBy(xpath = "//button[contains(@class, 'button-secondary')]|//button[contains(@class, 'button-primary')]")
    private List<WebElement> cardButtons;

    @FindBy(css = "ul.integration-phones li span.default-cursor")
    private List<WebElement> testPhones;

    @FindBy(css = "button.add-remove-item-bttn")
    private WebElement deleteTestPhone;

    public GetStartedSection(WebElement element) {
        super(element);
    }

    public GetStartedSection setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    private WebElement getTargetButton(String buttonName){
        try{
            return cardButtons.stream().filter(e -> e.getText().trim().equals(buttonName))
                    .findFirst().get();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    public String getCardTitle(){
        return cardTitle.getText().trim();
    }

    @Step(value = "Get card status")
    public String getCardStatus(){
        return cardStatus.getText().trim();
    }

    @Step(value = "Verify button is displayed")
    public boolean isButtonDisplayed(String buttonName, int wait){
        boolean result = false;
        for(int i = 0; i < wait; i++){
            result = cardButtons.stream().map(e -> e.getText().trim())
                    .anyMatch(e -> e.equals(buttonName));
            if(result) return result;
            else waitFor(1000);
        }
        return result;
    }

    @Step(value = "Click '{buttonName}' button")
    public void clickButton(String buttonName, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        clickButtonPrivate(buttonName, wait);

    }

    private void clickButtonPrivate(String buttonName, int wait) {
        WebElement button = getTargetButton(buttonName);
        for(int i = 0; i < wait; i++){
            if(button!=null){
                waitForElementToBeClickable(this.getCurrentDriver(), button, 5);
                executeAngularClick(this.getCurrentDriver(), button);
                break;
            }
            else {
                waitFor(1000);
                button = getTargetButton(buttonName);
            }
        }
        if(button==null) Assert.fail("Cannot find '" +buttonName + "' button.");
    }

    @Step(value = "Get test phones")
    public List<String> getTestPhones(){
        waitForElementsToBeVisible(this.getCurrentDriver(), testPhones, 5);
        if(testPhones.size()==0) Assert.fail("Test Phones list is empty");
        List<String> phones =  testPhones.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Collections.sort(phones);
        return phones;
    }

    @Step(value = "Click 'Remove' button for test phone on Launchpad")
    public void clickDeletePhoneButton(){
        clickElem(this.getCurrentDriver(), deleteTestPhone, 3, "Delete test phone button");
    }
}
