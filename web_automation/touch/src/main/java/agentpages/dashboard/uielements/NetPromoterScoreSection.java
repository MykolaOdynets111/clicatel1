package agentpages.dashboard.uielements;


import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-net-promoter-score-average-section")
public class NetPromoterScoreSection extends AbstractUIElement {

    @FindBy(css = ".cl-net-promoter-score-bars-section")
    private WebElement promoterScoreBars;

    @FindBy(css = ".cl-net-promoter-score-pie-section")
    private WebElement promoterScorePie;

    @FindBy(css = ".cl-no-data-alert")
    private WebElement noDataAlert;

    public boolean isPromoterScoreBarsDisplayed() {
        return isElementShown(this.getCurrentDriver(), promoterScoreBars, 5);
    }

    public boolean isPromoterScorePieDisplayed() {
        return isElementShown(this.getCurrentDriver(), promoterScorePie, 5);
    }

    public boolean isNoDataAlertRemoved() {
        return isElementRemoved(this.getCurrentDriver(), noDataAlert, 5);
    }

}
