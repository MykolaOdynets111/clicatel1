package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@FindBy(css = ".customers-history-tab")
public class CustomersHistory extends AbstractUIElement {

    private final String graphByNameXpath = "//h3[text()='%s']/../following-sibling::div[contains(@class,'chart-container')]";

    private final String csatScoreGaugeByNameXpath = "//h3[text()='%s']/../following-sibling::div[contains(@class,'cl-gauge-section')]";

    private final String noDataByGraphNameXpath = graphByNameXpath + "//div[contains(@class,'no-data-overlay')]";

    private final String gaugeNameXpath = csatScoreGaugeByNameXpath + "//div[contains(@class,'cl-no-data-alert')]";

    private final String filteredByInfoXpath = "//span[text()='%s']/../span[contains(@class, 'cl-default-text-muted')]";

    @FindBy(css = "h3")
    private List<WebElement> graphHeaders;

    @FindBy(css = ".chart-container")
    private List<WebElement> graphs;

    @FindBy(css = ".gauge-labels")
    private WebElement gaugeLabels;

    @FindBy(css = ".live-chats-section:nth-child(2) [dominant-baseline='central']")
    private List<WebElement> yAxisScaleCustomerSatisfaction;

    private WebElement getGraphSectionByName(String name) {
        return findElementByXpath(currentDriver,
                format("//h3[text() = '%s']/ancestor::section[@class = 'live-chats-section']", name), 10);
    }

    public List<String> getVerticalLineValuesForGraph(String name) {
        return getGraphSectionByName(name)
                .findElements(By.xpath(".//*[name() = 'svg']//*[@text-anchor='end']"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

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

    public boolean isYAxisContainsScale(String downScale, String upScale) {
        List<WebElement> finalList = yAxisScaleCustomerSatisfaction.stream().filter(e -> e.getText().contains(downScale) ||
                e.getText().contains(upScale)).collect(Collectors.toList());

        return finalList.size() == 2;
    }

    public boolean isGraphContainsScale(String downScale, String upScale) {
        String text= getTextFromElem(this.getCurrentDriver(), gaugeLabels, 5, "Customer satisfaction scale");

        return text.contains(downScale) && text.contains(upScale);
    }

    public boolean isNoDataRemovedForGraph(String graphName) {
        return isElementRemovedByXPATH(this.getCurrentDriver(), String.format(noDataByGraphNameXpath, graphName), 3);
    }

    public boolean isNoDataDisplayedForGraph(String graphName) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(noDataByGraphNameXpath, graphName), 5);
    }

    public String isNoDataAlertMessageText(String graphName) {
        return getTextFromElem(this.getCurrentDriver(), findElementByXpath(this.getCurrentDriver(), String.format(noDataByGraphNameXpath, graphName), 5), 5, "No data alert");
    }

    public boolean isNoGaugeDisplayedForGraph(String graphName) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(gaugeNameXpath, graphName), 5);
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
            System.out.println("Graph is working fine for: " + graphName + " , " + Arrays.stream(filters).findFirst());
            return foundFilters.containsAll(Arrays.asList(filters));
        } catch (TimeoutException | NoSuchElementException e2) {
            return false;
        }
    }
}
