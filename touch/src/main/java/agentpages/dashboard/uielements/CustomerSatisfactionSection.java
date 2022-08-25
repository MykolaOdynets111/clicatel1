package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-customer-satisfaction-gauge")
public class CustomerSatisfactionSection extends AbstractUIElement {
    @FindBy(css = ".cl-satisfaction-score>.cl-gauge-total-value")
    private WebElement customerSatisfactionScore;

    @FindBy(css = ".cl-no-data-alert")
    private WebElement noDataAlert;

    public static Double satisfactionScoreOld;

    public static Double satisfactionScoreNew;

    public boolean isCustomerSatisfactionScoreDisplayed() {
        return isElementShown(this.getCurrentDriver(), customerSatisfactionScore, 5);
    }

    public double getCustomerSatisfactionScoreOld() {
        satisfactionScoreOld = Double.parseDouble(getTextFromElem(this.getCurrentDriver(), customerSatisfactionScore, 3,
                "Customer Satisfaction score"));
        return satisfactionScoreOld;
    }

    public double getCustomerSatisfactionScoreNew() {
        satisfactionScoreNew = Double.parseDouble(getTextFromElem(this.getCurrentDriver(), customerSatisfactionScore, 3,
                "Customer Satisfaction score"));
        return satisfactionScoreNew;
    }

    public boolean isNoDataAlertRemoved() {
        return isElementRemoved(this.getCurrentDriver(), noDataAlert, 5);
    }
}
