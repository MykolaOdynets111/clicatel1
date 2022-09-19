package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-customer-satisfaction-gauge")
public class CustomerSatisfactionSection extends AbstractUIElement {
    @FindBy(css = ".cl-satisfaction-score>.cl-gauge-total-value")
    private WebElement customerSatisfactionScore;

    public boolean isCustomerSatisfactionScoreDisplayed() {
        return isElementShown(this.getCurrentDriver(), customerSatisfactionScore, 5);
    }

    public double getCustomerSatisfactionScore() {
        return Double.parseDouble(getTextFromElem(this.getCurrentDriver(), customerSatisfactionScore, 3,
                "Customer Satisfaction score"));
    }

}
