package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import interfaces.WebActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.agent-assist")
public class SuggestedGroup extends AbstractUIElement {

    @FindBy(css = "div.suggestion")
    private List<WebElement> listOfSuggestion;

    @FindBy(css = "div.info-message__description")
    private WebElement suggestionsNotAvailableMessage;

    public boolean isSuggestionListEmpty() {
        for (int i=0; i<2; i++){
            if (listOfSuggestion.size()==0){
                waitFor(200);
            }else{
                return false;
            }
        }
        return true;
    }

    public List<Suggestion> getSuggestionsList() {
        return listOfSuggestion.stream().map(e -> new Suggestion(e)).collect(Collectors.toList());
    }

    public String getSuggestionsNotAvailableMessage(){
        return suggestionsNotAvailableMessage.getText();
    }
}
