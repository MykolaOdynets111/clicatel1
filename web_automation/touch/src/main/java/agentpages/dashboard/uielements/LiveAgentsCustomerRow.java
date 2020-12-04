package agentpages.dashboard.uielements;


import abstractclasses.AbstractWidget;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LiveAgentsCustomerRow extends AbstractWidget {

    @FindBy(css = "td:nth-child(7) .cl-collapsible-row__value")
    private WebElement customerName;

    @FindBy(css = "td:nth-child(8) svg")
    private WebElement sentiment;

    @FindBy(css = "td:nth-child(9) .cl-collapsible-row__value")
    private WebElement intent;

    @FindBy(css = "td:nth-child(10) .cl-collapsible-row__value")
    private WebElement chatDuration;

    public LiveAgentsCustomerRow(WebElement element) {
        super(element);
    }

    public LiveAgentsCustomerRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getCustomerName() {
        return getTextFromElem(this.currentDriver, customerName, 5, "Customer name");
    }

    public String getSentiment() {
        return getAttributeFromElem(this.getCurrentDriver(), sentiment, 5, "Sentiment", "data-selenium-id").replace("sentiment-", "");
    }

    public String getIntent(){
        return getTextFromElem(this.getCurrentDriver(), intent, 2, "Intent");
    }
}
