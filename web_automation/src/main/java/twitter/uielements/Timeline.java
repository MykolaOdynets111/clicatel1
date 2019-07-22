package twitter.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.ProfileTimeline ")
public class Timeline extends AbstractUIElementDeprecated {

    @FindBy(css = "li.stream-item")
    private List<WebElement> tweetsList;



}
