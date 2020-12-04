package agentpages.dashboard.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerHistoryGraph extends AbstractWidget {

    @FindBy(css = "text[dominant-baseline='text-before-edge']")
    private List<WebElement> timelines;

    public CustomerHistoryGraph(WebElement element) {
        super(element);
    }

    public CustomerHistoryGraph setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public List<String> getTimeLines() {
        return timelines.stream()
                .map(timeline -> getTextFromElem(this.getCurrentDriver(), timeline, 5, "Graph timeline"))
                .collect(Collectors.toList());
    }
}
