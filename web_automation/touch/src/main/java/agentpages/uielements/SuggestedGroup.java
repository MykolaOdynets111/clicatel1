package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "[selenium-id=rp-agent-assist]")
public class SuggestedGroup extends AbstractUIElement {

    @FindBy(css = "[selenium-id=agent-assist-suggestion]")
    private List<WebElement> listOfSuggestion;

    @FindBy(css = "[selenium-id=agent-assist-disabled]")
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
        return listOfSuggestion.stream().map(e -> new Suggestion(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
    }

    public String getSuggestionsNotAvailableMessage(){
        return suggestionsNotAvailableMessage.getText();
    }
}
