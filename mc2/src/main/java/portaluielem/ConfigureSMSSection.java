package portaluielem;

import abstractclasses.AbstractWidget;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class ConfigureSMSSection extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "h3[ng-if='contentWindow.title']")
    private WebElement sectionTitle;

//    private String sectionTitleCss = "h3[ng-if='contentWindow.title']";

    @FindBy(css = "span[phone]")
    private List<WebElement> phoneNumbers;

    @FindBy(xpath = ".//button[contains(@class, 'button-secondary')]|.//button[contains(@class, 'button-primary')]")
    private List<WebElement> cardButtons;

    @FindBy(css = "ul.integration-phones li span.default-cursor")
    private WebElement testPhone;

    public ConfigureSMSSection(WebElement element) {
        super(element);
    }

    public ConfigureSMSSection setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    private WebElement getTargetButton(String buttonName){
        try{
            return cardButtons.stream().filter(e -> e.getText().trim().equals(buttonName))
                    .findFirst().orElseThrow(() -> new AssertionError("Button '" +buttonName+ "' is not visible"));
        }catch(NoSuchElementException e){
            return null;
        }
    }

    public String getSectionTitle(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        return getTextFromElem(this.getCurrentDriver(), sectionTitle, 5,
                "Section title on Configure SMS page");
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
    public List<String> getTestPhone(){
        waitForElementsToBeVisible(this.getCurrentDriver(), phoneNumbers, 4);
        if(phoneNumbers.size()==0) Assert.fail("Test Phones list is empty");
        return phoneNumbers.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }
}
