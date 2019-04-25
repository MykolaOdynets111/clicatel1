package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

@FindBy(css = "div.automated-messages")
public class AutoRespondersWindow extends BasePortalWindow {

    @FindBy(name = "clMessageForm")
    private List<WebElement> autoresponderItems;

    public AutoResponderItem getTargetAutoResponderItem(String autoresponder){
        return autoresponderItems.stream().map(e -> new AutoResponderItem(e))
                            .filter(e -> e.getTitle().contains(autoresponder))
                            .findFirst().get();
    }


    public void waitToBeLoaded(){
        waitForElementToBeVisibleAgent(this.getWrappedElement(), 6);
    }


    public void clickExpandArrowForMessage(String autoresponder){
        waitForElementsToBeVisibleAgent(autoresponderItems, 5, "admin");
        getTargetAutoResponderItem(autoresponder).clickCollapseIcon();
    }

    public void clickResetToDefaultForMessage(String autoresponder){
        waitForElementsToBeVisibleAgent(autoresponderItems, 5, "admin");
        getTargetAutoResponderItem(autoresponder).clickResetToDefaultButton();
    }
}
