package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@FindBy(css = "div.modal-content")
public class AgentFeedbackWindow extends AbstractUIElementDeprecated {

    private String windowCss = "div.modal-content";

    private String closeChatButtonXPATH = "//span[text()='Close Chat']";

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[text()='Skip']")
    private WebElement skipButton;

    @FindBy(xpath = "//span[text()='Close Chat']")
    private WebElement closeChatButton;

    @FindBy(css = ".icon-negative")
    private WebElement sentimentUnsatisfied ;

    @FindBy(css = ".icon-neutral")
    private WebElement sentimentNeutral;

    @FindBy(css = ".icon-positive")
    private WebElement sentimentHappy;

    @FindBy(css = ".icons.conclude-chat-sentiment")
    private WebElement sentimentsAll;

    @FindBy(xpath = "//div[@class='Select-menu-outer']/div[@role='listbox']")
    private WebElement availableTagsContainer;

    @FindBy(xpath = "//div[@class='Select-menu-outer']//div[@role='option']")
    private List<WebElement> availableTags;

    private String inputTagField =  "div.Select-input > input";

    @FindBy(xpath = "//span[@class='Select-arrow-zone']")
    private WebElement openDropdownButton;

    private String openDropdownButtoncss = ".Select-control";

    private String openDropdownButtoncssTags = ".Select-multi-value-wrapper";

    private String openDropdownButtonXpathClear = "//div[contains(@class,'Select-placeholder')]";

    private String cleareAll = ".Select-clear";

    @FindBy(id = "CRMNote")
    private WebElement crmNoteTextField;

    @FindBy(id = "CRMLink")
    private WebElement crmLink;

    @FindBy(id = "CRMTicketNumber")
    private WebElement crmTicketNumber;

    public void clickCancel() {
        clickElemAgent(cancelButton, 5, "main agent", "Cancel button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickCloseChat() {
        clickElemAgent(closeChatButton, 5, "main agent", "Close chat button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickSkip() {
        clickElemAgent(skipButton, 5, "main agent", "Skip button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickCloseButtonInCloseChatPopup (){
        if( isElementShownAgentByXpath(closeChatButtonXPATH, 3, "main")){
            waitForElementToBeVisibleByXpathAgent(closeChatButtonXPATH, 5, "main agent");
            findElemByXPATHAgent(closeChatButtonXPATH).click();
        }
    }

    public boolean isEndChatPopupShown (){
        return isElementShownAgent(closeChatButton,12);
    }

    public AgentFeedbackWindow typeCRMNoteTextField(String msg) {
        if (msg != null & !msg.equals("")) {
            inputTextAgent(crmNoteTextField, 5, "main agent", "Note", msg);
        }
        return this;
    }

    public AgentFeedbackWindow typeCRMLink(String msg) {
        if (msg != null & !msg.equals("")) {
            inputTextAgent(crmLink, 5, "main agent", "Note link", msg);
        }
        return this;
    }

    public AgentFeedbackWindow typeCRMTicketNumber(String msg) {
        if (msg != null & !msg.equals("")) {
            inputTextAgent(crmTicketNumber, 5, "main agent", "Note number", msg);
        }
        return this;
    }

    public void  fillForm(String note, String link, String number) {
        typeCRMNoteTextField(note);
        typeCRMLink(link);
        typeCRMTicketNumber(number);
    }

    public void setSentimentUnsatisfied() {
        clickElemAgent(sentimentUnsatisfied, 5, "main agent", "Sentiment Unsatisfied" );
    }

    public void setSentimentNeutral() {
        clickElemAgent(sentimentNeutral, 5, "main agent", "Sentiment Neutral" );
    }

    public void setSentimentHappy() {
        clickElemAgent(sentimentHappy, 5, "main agent", "Sentiment Happy" );
    }


    public void selectTags(int iter) {
        clickElemAgent(openDropdownButton, 5, "main agent", "Open dropdown button" );
        if(availableTagsContainer.getText().isEmpty()) {
            Assert.assertTrue(false, "Have not Tags to be selected");
        } else {
            clickElemAgent(openDropdownButton, 3, "main agent", "Open dropdown button" );
            while (iter > 0) {
                clickElemAgent(openDropdownButton, 3, "main agent", "Open dropdown button" );
                availableTagsContainer.click();
                findElemByCSSAgent(openDropdownButtoncss).click();
                iter--;
            }
        }
    }

    public List<String> getTags() {
        waitForElementToBeClickableAgent(openDropdownButton, 6, "main agent");
        if (isElementShown(availableTagsContainer, 2)){
            return Arrays.asList(availableTagsContainer.getText().split("[\n]"));
        } else {
            clickElemAgent(openDropdownButton, 5, "main agent", "Open dropdown button" );
        }
        waitForElementToBeClickableAgent(availableTagsContainer, 6, "main agent");
        if(availableTagsContainer.getText().isEmpty()) {
            Assert.fail("Have not Tags to be selected");
        }
//        String html = MC2DriverFactory.getAgentDriverInstance().findElement(By.cssSelector("div.Select-menu-outer")).getAttribute("innerHTML");
        List<String> result = availableTags.stream().map(e -> e.getAttribute("innerText").trim()).collect(Collectors.toList());
        clickElemAgent(openDropdownButton, 5, "main agent", "Open dropdown button" );
        return result;
    }

    public List<String> getChosenTags() {
        waitForElementToBeClickableAgent(openDropdownButton, 6, "main agent");
        List<String> result = new ArrayList<>();
        if (!isElementShownAgentByXpath(openDropdownButtonXpathClear,2,"main")){
            result.addAll(Arrays.asList(findElemByCSSAgent(openDropdownButtoncssTags).getText().split("[\n]+[ ]")));
        }
        return result;
    }

    public void typeTags(String tag) {
        waitForElementToBeClickableAgent(openDropdownButton, 6, "main agent");
        findElemByCSSAgent(openDropdownButtoncss).click();
        findElemByCSSAgent(inputTagField).sendKeys(tag);
    }

    public void selectTagInSearch() {
        waitForElementToBeClickableAgent(availableTagsContainer, 6, "main agent");
        availableTagsContainer.click();
    }

    public void deleteTags() {
             findElemByCSSAgent(cleareAll).click();
    }

    public String getPlaceholder() {
       return crmNoteTextField.getAttribute("placeholder");
    }

    public boolean isValidSentiments(File image) {
        return isWebElementEqualsImage(sentimentsAll, image, "main");
    }

}
