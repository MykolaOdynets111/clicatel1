package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.agent-assist")
public class SuggestedGroup extends AbstractUIElement {

    @FindBy(css = "div.suggestion")
    private List<WebElement> listOfSuggestion;

    public boolean isSuggestionListEmpty() {
        for (int i=0; i<5; i++){
            if (listOfSuggestion.size()==0){
                waitFor(500);
            }else{
                return false;
            }
        }
        return true;
    }

    public List<Suggestion> getSuggestionsList() {
        return listOfSuggestion.stream().map(e -> new Suggestion(e)).collect(Collectors.toList());
    }
}
