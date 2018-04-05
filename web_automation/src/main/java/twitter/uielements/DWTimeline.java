package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.ProfileTimeline ")
public class DWTimeline extends AbstractUIElement {

    @FindBy(css = "li.stream-item")
    private List<WebElement> tweetsList;



}
