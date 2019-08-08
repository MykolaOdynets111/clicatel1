package portaluielem;

import abstractclasses.AbstractWidget;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;


public class GetStartedSection extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(xpath = ".//div[contains(@class, 'cl-media')]/following-sibling::p")
    private WebElement cardTitle;

    @FindBy(css = "button.status-button.ng-scope span")
    private WebElement cardStatus;

    @FindBy(xpath = "//button[contains(@class, 'button-secondary')]|//button[contains(@class, 'button-primary')]")
    private List<WebElement> cardButtons;

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
//        try{
            clickButtonPrivate(buttonName, wait);
//        }catch (StaleElementReferenceException e){
//            waitFor(500);
//            clickButtonPrivate(buttonName, wait);
//        }

    }

    private void clickButtonPrivate(String buttonName, int wait) {
        WebElement button = getTargetButton(buttonName);
        for(int i = 0; i < wait; i++){
            if(button!=null){
                try {
                    clickElem(this.getCurrentDriver(), button, 5, buttonName);
                }catch(ElementClickInterceptedException e){}
            }
            else {
                waitFor(1000);
                button = getTargetButton(buttonName);
            }
        }
    }
}
