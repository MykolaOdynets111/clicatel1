package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@FindBy(css = "div.cl-r-modal")
public class AgentFeedbackWindow extends AbstractUIElement {

    @FindBy(css = "[selenium-id=exit-chat-modal-cancel]")
    private WebElement cancelButton;

    @FindBy(css = "[selenium-id=exit-chat-modal-save]")
    private WebElement saveChatButton;

    @FindBy(css = "[selenium-id=sentiment-icon-negative]")
    private WebElement sentimentUnsatisfied ;

    @FindBy(css = "[selenium-id=sentiment-icon-neutral]")
    private WebElement sentimentNeutral;

    @FindBy(css = "[selenium-id=sentiment-icon-positive]")
    private WebElement sentimentHappy;

    @FindBy(css = "[selenium-id^='sentiment']")
    private WebElement selectedSentiment;

    @FindBy(css = "[selenium-id=chat-sentiment-icons]")
    private WebElement sentimentsAll;

    @FindBy(xpath = "//div[@class='Select-menu-outer']/div[@role='listbox']")
    private WebElement availableTagsContainer;

    @FindBy(css = "div.Select-control")
    private WebElement tagsInput;

    @FindBy(xpath = "//div[@class='Select-menu-outer']//div[@role='option']")
    private List<WebElement> availableTags;

    @FindBy(xpath = "//span[@class='Select-arrow-zone']/span")
    private WebElement openDropdownButton;

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    private String inputTagField =  "div.Select-input > input";

    private String tagsOptionsCss = "div.Select-option";

    private String selectedButtonTagsCss = "span.conclude-conversation-tag";

    private String openDropdownButtonXpathClear = "//div[contains(@class,'Select-placeholder')]";

    private String cleareAll = ".Select-clear";

    @FindBy(css = "[selenium-id=exit-chat-note]")
    private WebElement crmNoteTextField;

    @FindBy(css = "[selenium-id=exit-chat-link]")
    private WebElement crmLink;

    @FindBy(css = "[selenium-id=exit-chat-ticket-number]")
    private WebElement crmTicketNumber;

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 5, "Cancel button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public void clickCloseChat() {
        clickElem(this.getCurrentDriver(), saveChatButton, 5,"Close chat button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public void clickSaveButtonInCloseChatPopup(){
        clickElem(this.getCurrentDriver(), saveChatButton, 5, "Save button");
    }

    public boolean isEndChatPopupShown (){
        return isElementShown(this.getCurrentDriver(), saveChatButton,12);
    }

    public AgentFeedbackWindow typeCRMNoteTextField(String msg) {
        if (msg != null & !msg.equals("")) {
            inputText(this.getCurrentDriver(), crmNoteTextField, 5,"Note", msg);
        }
        return this;
    }

    public AgentFeedbackWindow typeCRMLink(String msg) {
        if (msg != null & !msg.equals("")) {
            inputText(this.getCurrentDriver(), crmLink, 5,"Note link", msg);
        }
        return this;
    }

    public AgentFeedbackWindow typeCRMTicketNumber(String msg) {
        if (msg != null & !msg.equals("")) {
            inputText(this.getCurrentDriver(), crmTicketNumber, 5,"Note number", msg);
        }
        return this;
    }

    public void  fillForm(String note, String link, String number) {
        typeCRMNoteTextField(note);
        typeCRMLink(link);
        typeCRMTicketNumber(number);
    }

    public void setSentimentUnsatisfied() {
        clickElem(this.getCurrentDriver(), sentimentUnsatisfied, 5,"Sentiment Unsatisfied" );
    }

    public void setSentimentNeutral() {
        clickElem(this.getCurrentDriver(), sentimentNeutral, 5,"Sentiment Neutral" );
    }

    public void setSentimentHappy() {
        clickElem(this.getCurrentDriver(), sentimentHappy, 5,"Sentiment Happy" );
    }


    public void selectTags(int iter) {
        for (int i = 0; i<iter; i++){
            selectTag(i);
        }
    }

    public void selectTag(int ordinalNumber){
        waitForElementToBeVisible(this.getCurrentDriver(), tagsInput, 3);
        tagsInput.click();
        if(!isElementShown(this.getCurrentDriver(), availableTagsContainer, 2)) {
            openDropdownButton.click();
        }
        waitForElementsToBeVisibleByCss(this.getCurrentDriver(), tagsOptionsCss, 3);
        List<WebElement> availableTags = findElemsByCSS(this.getCurrentDriver(), tagsOptionsCss);
        if(availableTags.size()==1 && availableTags.get(0).getText().equalsIgnoreCase("No results found")){
            Assert.fail("Have no Tags to be selected");
        }
        availableTags.get(ordinalNumber).click();
        isElementShownByCSS(this.getCurrentDriver(), selectedButtonTagsCss, 2);
    }

    public List<String> getTags() {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        if (isElementShown(this.getCurrentDriver(), availableTagsContainer, 2)){
            return Arrays.asList(availableTagsContainer.getText().split("[\n]"));
        } else {
            clickElem(this.getCurrentDriver(), openDropdownButton, 5,"Open dropdown button" );
        }
        waitForElementToBeClickable(this.getCurrentDriver(), availableTagsContainer, 6);
        if(availableTagsContainer.getText().isEmpty()) {
            Assert.fail("Have not Tags to be selected");
        }
//        String html = MC2DriverFactory.getAgentDriverInstance().findElement(By.cssSelector("div.Select-menu-outer")).getAttribute("innerHTML");
        List<String> result = availableTags.stream().map(e -> e.getAttribute("innerText").trim()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), openDropdownButton, 5,"Open dropdown button" );
        return result;
    }

    public List<String> getChosenTags() {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        if (!isElementShownByXpath(this.getCurrentDriver(), openDropdownButtonXpathClear,2)){
            waitForElementsToBeVisibleByCss(this.getCurrentDriver(), selectedButtonTagsCss, 3);
            return findElemsByCSS(this.getCurrentDriver(), selectedButtonTagsCss).stream()
                    .map(e -> e.getText().trim())
                    .collect(Collectors.toList());
        } else{
            return new ArrayList<>();
        }
    }

    public void typeTags(String tag) {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        findElemByCSS(this.getCurrentDriver(), inputTagField).sendKeys(tag);
    }

    public void selectTagInSearch() {
        waitForElementToBeClickable(this.getCurrentDriver(), availableTagsContainer, 6);
        availableTagsContainer.click();
    }

    public void deleteTags() {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), cleareAll, 6);
        findElemByCSS(this.getCurrentDriver(), cleareAll).click();
    }

    public String getPlaceholder() {
       return crmNoteTextField.getAttribute("placeholder");
    }

    public boolean isValidSentiments(File image) {
        return isWebElementEqualsImage(this.getCurrentDriver(), sentimentsAll, image);
    }

    public String getSelectedSentiment(){
        return getAttributeFromElem(this.getCurrentDriver(), selectedSentiment, 3, "Selected sentiment", "class");
    }

}
