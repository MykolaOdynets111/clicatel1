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

    @FindBy(css = ".cl-net-promoter-score-pie-section .cl-nps-doughnut-value")
    private WebElement promoterScore;

    @FindBy(css = ".cl-net-promoter-legend-percentage.passives")
    private WebElement passivesPercentage;

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

    public int getNetPromoterScore() {
        return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), promoterScore, 5,
                "Net Promoter Score"));
    }

    public int getPassivePercentage() {
        return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), passivesPercentage, 5,
                "Passive Percentage").replace("%", ""));
    }
}
