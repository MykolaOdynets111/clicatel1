package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.agent-assist")
public class SuggestedGroup extends AbstractUIElement {

    @FindBy(css = "div.suggestion")
    private List<WebElement> listOfSuggestion;

    public boolean isSuggestionListEmpty() {
        if (listOfSuggestion.size()==0){
            return true;
        } else{
            return false;
        }
    }
}
