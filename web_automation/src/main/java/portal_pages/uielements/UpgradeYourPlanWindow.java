package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class UpgradeYourPlanWindow  extends AbstractUIElement {

    @FindBy(css = "button.bttn-add-agents-seats")
    private WebElement addAgentSeatsButton;

    @FindBy(xpath =  ".//button[@ng-click='wizardSubmit()'][not(@id='integration-save')]")
    private WebElement addToCardButton;




    public UpgradeYourPlanWindow selectAgentSeats(int seats){
        for(int i =0; i<seats; i++ ){
            addAgentSeatsButton.click();
        }
        return this;
    }

    public void clickAddToCardButton(){
        addToCardButton.click();
    }
}
