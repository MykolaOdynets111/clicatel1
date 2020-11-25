package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".customers-history-tab")
public class CustomersHistory extends AbstractUIElement {
    private final String graphInDivXpath =
            "//h3[text()='%s']/../following-sibling::div[contains(@class,'chart-container')]";
    private final String graphInH3Xpath =
            "//h3[text()='%s']/following-sibling::div[contains(@class,'chart-container')]";

    private final String noDataForGraphInDivXpath = graphInDivXpath + "//div[contains(@class,'no-data-overlay')]";
    private final String noDataForGraphInH3Xpath = graphInH3Xpath + "//div[contains(@class,'no-data-overlay')]";

    public boolean isGraphDisplayed(String graphName) {
        try {
            WebElement graphInDiv = findElemByXPATH(this.getCurrentDriver(), String.format(graphInDivXpath, graphName));
            wheelScrollDownToElement(this.getCurrentDriver(), this.getWrappedElement(), graphInDiv, 3);
            return isElementShown(this.getCurrentDriver(), graphInDiv, 5);
        } catch (TimeoutException | NoSuchElementException e) {
            try {
                WebElement graphInH3 = findElemByXPATH(this.getCurrentDriver(), String.format(graphInH3Xpath, graphName));
                wheelScrollDownToElement(this.getCurrentDriver(), this.getWrappedElement(), graphInH3, 3);
                return isElementShown(this.getCurrentDriver(), graphInH3, 5);
            } catch (TimeoutException | NoSuchElementException e2) {
                return false;
            }
        }
    }

    public boolean isNoDataRemovedForGraph(String graphName) {
        try {
            WebElement noDataForGraphInDiv = findElemByXPATH(this.getCurrentDriver(), String.format(noDataForGraphInDivXpath, graphName));
            return isElementRemoved(this.getCurrentDriver(), noDataForGraphInDiv, 3);
        } catch (TimeoutException | NoSuchElementException e) {
            try {
                WebElement noDataForGraphInH3 = findElemByXPATH(this.getCurrentDriver(), String.format(noDataForGraphInH3Xpath, graphName));
                return isElementRemoved(this.getCurrentDriver(), noDataForGraphInH3, 3);
            } catch (TimeoutException | NoSuchElementException e2) {
                return true;
            }
        }
    }
}
