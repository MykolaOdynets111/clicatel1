package portaluielem;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class UpgradeYourPlanWindow  extends BasePortalWindow {

    @FindBy(css = "button.bttn-add-agents-seats")
    private WebElement addAgentSeatsButton;

    @FindBy(xpath = "//span[contains(@value, 'MONTH')]//span[contains(@class, 'cl-input-label')]")
    private WebElement monthlyRadioButton;

    public UpgradeYourPlanWindow selectAgentSeats(int seats){
        try {
            waitForElementToBeClickable(this.getCurrentDriver(), addAgentSeatsButton, 25);
        } catch(TimeoutException e){
            Assert.fail("'Add agent seats' button is not clickable.\n " +
                    "Please check the screenshot");
        }
        for(int i =1; i<seats; i++ ){
            addAgentSeatsButton.click();
        }
        return this;
    }


    public UpgradeYourPlanWindow selectMonthly(){
        monthlyRadioButton.click();
        return this;
    }
}
