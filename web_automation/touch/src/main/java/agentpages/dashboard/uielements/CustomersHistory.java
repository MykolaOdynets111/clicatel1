package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".customers-history-tab")
public class CustomersHistory extends AbstractUIElement {
    private final String graphInDivXpath =
            "//h3[text()='%s']/../following-sibling::div[contains(@class,'chart-container')]";
    private final String graphInH3Xpath =
            "//h3[text()='%s']/following-sibling::div[contains(@class,'chart-container')]";

    private final String noDataForGraphInDivXpath = graphInDivXpath + "//div[contains(@class,'no-data-overlay')]";
    private final String noDataForGraphInH3Xpath = graphInH3Xpath + "//div[contains(@class,'no-data-overlay')]";

    private final String filteredByInfoInDivXpath =
            "//h3[text()='%s']/span[contains(@class, 'cl-default-text-muted')]";
    private final String filteredByInfoInH3Xpath =
            "//h3[text()='%s']/../span[contains(@class, 'cl-default-text-muted')]";

    @FindBy(css = "h3")
    private List<WebElement> graphHeaders;

    @FindBy(css = ".chart-container")
    private List<WebElement> graphs;

    public List<List<String>> getGraphsTimelines() {
        return graphs.stream()
                .map(graph -> new CustomerHistoryGraph(graph).setCurrentDriver(this.getCurrentDriver()))
                .map(CustomerHistoryGraph::getTimeLines).collect(Collectors.toList());
    }

    public boolean isCustomerHistoryTabOpened() {
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }

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

    public boolean isNoDataDisplayedForGraph(String graphName) {
        try {
            WebElement noDataForGraphInDiv = findElemByXPATH(this.getCurrentDriver(), String.format(noDataForGraphInDivXpath, graphName));
            return isElementShown(this.getCurrentDriver(), noDataForGraphInDiv, 5);
        } catch (TimeoutException | NoSuchElementException e) {
            try {
                WebElement noDataForGraphInH3 = findElemByXPATH(this.getCurrentDriver(), String.format(noDataForGraphInH3Xpath, graphName));
                return isElementShown(this.getCurrentDriver(), noDataForGraphInH3, 5);
            } catch (TimeoutException | NoSuchElementException e2) {
                return false;
            }
        }
    }

    public List<String> getAllGraphs() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), graphHeaders, 5);
        return graphHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isGraphFilteredBy(String graphName, String... filters) {
        try {
            List<WebElement> filteredByInfInDiv = findElemsByXPATH(this.getCurrentDriver(),
                    String.format(filteredByInfoInDivXpath, graphName));
            waitForFirstElementToBeVisible(getCurrentDriver(), filteredByInfInDiv, 3);
            if(filteredByInfInDiv.size() == 0)
                throw new NoSuchElementException("");
            List<String> foundFilters = filteredByInfInDiv.stream().map(WebElement::getText).collect(Collectors.toList());
            return foundFilters.containsAll(Arrays.asList(filters));

        } catch (TimeoutException | NoSuchElementException e) {
            try {
                List<WebElement> filteredByInfInH3 = findElemsByXPATH(this.getCurrentDriver(),
                        String.format(filteredByInfoInH3Xpath, graphName));
                waitForFirstElementToBeVisible(getCurrentDriver(), filteredByInfInH3, 3);
                List<String> foundFilters = filteredByInfInH3.stream().map(WebElement::getText).collect(Collectors.toList());
                return foundFilters.containsAll(Arrays.asList(filters));
            } catch (TimeoutException | NoSuchElementException e2) {
                return false;
            }
        }
    }
}
