package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".customers-history-tab")
public class CustomersHistory extends AbstractUIElement {

    private final String graphByNameXpath = "//h3[text()='%s']/../following-sibling::div[contains(@class,'chart-container')]";
    private final String noDataByGraphNameXpath = graphByNameXpath + "//div[contains(@class,'no-data-overlay')]";
    private final String filteredByInfoXpath = "//span[text()='%s']/../span[contains(@class, 'cl-default-text-muted')]";


    @FindBy(css = "h3")
    private List<WebElement> graphHeaders;

    @FindBy(css = ".chart-container")
    private List<WebElement> graphs;

    public List<List<String>> getGraphsTimelines() {
        waitFor(2000);
        return graphs.stream()
                .map(graph -> new CustomerHistoryGraph(graph).setCurrentDriver(this.getCurrentDriver()).scrollToCustomerHistoryGraph())
                .map(CustomerHistoryGraph::getTimeLines).collect(Collectors.toList());
    }

    public boolean isCustomerHistoryTabOpened() {
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }

    public boolean isGraphDisplayed(String graphName) {
        String graphXpath= String.format(graphByNameXpath, graphName);
        if (isElementExistsInDOMXpath(this.getCurrentDriver(), graphXpath, 5)){
         scrollToElem(this.getCurrentDriver(), graphXpath, graphName);
        }
        return isElementShownByXpath(this.getCurrentDriver(), graphXpath, 1);
    }

    public boolean isNoDataRemovedForGraph(String graphName) {
        return isElementRemovedByXPATH(this.getCurrentDriver(), String.format(noDataByGraphNameXpath, graphName), 3);
    }

    public boolean isNoDataDisplayedForGraph(String graphName) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(noDataByGraphNameXpath, graphName), 5);
    }

    public List<String> getAllGraphs() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), graphHeaders, 5);
        return graphHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isGraphFilteredBy(String graphName, String... filters) {
        try {
            List<WebElement> filteredByInfInH3 = findElemsByXPATH(this.getCurrentDriver(),
                    String.format(filteredByInfoXpath, graphName));
            waitForFirstElementToBeVisible(getCurrentDriver(), filteredByInfInH3, 3);
            List<String> foundFilters = filteredByInfInH3.stream().map(WebElement::getText).collect(Collectors.toList());
            return foundFilters.containsAll(Arrays.asList(filters));
        } catch (TimeoutException | NoSuchElementException e2) {
            return false;
        }
    }
}
