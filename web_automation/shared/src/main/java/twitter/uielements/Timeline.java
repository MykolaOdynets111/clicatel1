package twitter.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.ProfileTimeline ")
public class Timeline extends AbstractUIElement {

    @FindBy(css = "li.stream-item")
    private List<WebElement> tweetsList;



}
