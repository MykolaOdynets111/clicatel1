package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class UpgradeYourPlanWindow  extends AbstractUIElement {

    @FindBy(css = "button.bttn-add-agents-seats")
    private WebElement addAgentSeatsButton;

    @FindBy(xpath =  ".//button[@ng-click='wizardSubmit()'][not(@id='integration-save')]")
    private WebElement addToCardButton;

    @FindBy(xpath = "//span[contains(@value, 'MONTH')]//span[contains(@class, 'cl-input-label')]")
    private WebElement monthlyRadioButton;

    public UpgradeYourPlanWindow selectAgentSeats(int seats){
        try {
            waitForElementToBeClickableAgent(addAgentSeatsButton, 25, "main");
        } catch(TimeoutException e){
            Assert.assertTrue(false, "'Add agent seats' button is not clickable.\n " +
                    "Please check the screenshot");
        }
        for(int i =1; i<seats; i++ ){
            addAgentSeatsButton.click();
        }
        return this;
    }

    public void clickAddToCardButton(){
        executeJSclick(addToCardButton, DriverFactory.getAgentDriverInstance());
//        addToCardButton.click();
    }

    public UpgradeYourPlanWindow selectMonthly(){
        monthlyRadioButton.click();
        return this;
    }
}
