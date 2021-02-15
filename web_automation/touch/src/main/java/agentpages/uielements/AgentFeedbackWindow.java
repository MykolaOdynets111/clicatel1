package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@FindBy(css = "div.cl-r-modal")
public class AgentFeedbackWindow extends AbstractUIElement {

    @FindBy(css = "[selenium-id=exit-chat-modal-cancel]")
    private WebElement cancelButton;

    @FindBy(css = "[selenium-id=exit-chat-modal-save]")
    private WebElement closeChatButton;

    @FindBy(css = "[selenium-id=sentiment-icon-negative]")
    private WebElement sentimentUnsatisfied ;

    @FindBy(css = "[selenium-id=sentiment-icon-neutral]")
    private WebElement sentimentNeutral;

    @FindBy(css = "[selenium-id=sentiment-icon-positive]")
    private WebElement sentimentHappy;

    @FindBy(xpath = ".//label[contains(@selenium-id, 'sentiment-icon') and contains(@class, 'active')]")
    private WebElement selectedSentiment;

    @FindBy(css = "[selenium-id=chat-sentiment-icons]")
    private WebElement sentimentsAll;

    @FindBy(css = ".cl-r-select__menu-list.cl-r-select__menu-list--is-multi.css-11unzgr")
    private WebElement availableTagsContainer;

    @FindBy(css = ".cl-r-select__control")
    private WebElement tagsInput;

    @FindBy(css = "div[id^='react-select']")
    private List<WebElement> availableTags;

    @FindBy(css = ".cl-r-select__indicators")
    private WebElement openDropdownButton;

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    private String inputTagField =  ".cl-r-select__input input";

    private String tagsOptionsCss = "div[id^='react-select']";

    private String selectedButtonTagsCss = ".cl-r-select__multi-value";

    private String cleareAll = ".Select-clear";

    @FindBy(css = "[selenium-id='exit-chat-note']")
    private WebElement crmNoteTextField;

    @FindBy(css = "[selenium-id=exit-chat-link]")
    private WebElement crmLink;

    @FindBy(css = "[selenium-id=exit-chat-ticket-number]")
    private WebElement crmTicketNumber;

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 5, "Cancel button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public void clickCloseButtonInCloseChatPopup(){
        clickElem(this.getCurrentDriver(), closeChatButton, 5, "Close button");
    }

    public boolean isEndChatPopupShown (){
        return isElementShown(this.getCurrentDriver(), closeChatButton,12);
    }

    public AgentFeedbackWindow typeCRMNoteTextField(String msg) {
        if (msg != null & !msg.equals("")) {
            crmNoteTextField.sendKeys(msg);
            //inputText(this.getCurrentDriver(), crmNoteTextField, 5,"Note", msg);
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
        try {
            waitForElementsToBeVisibleByCss(this.getCurrentDriver(), selectedButtonTagsCss, 3);
        } catch (TimeoutException e){
            return Collections.emptyList();
        }
        return findElemsByCSS(this.getCurrentDriver(), selectedButtonTagsCss).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public void typeTags(String tag) {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        findElemByCSS(this.getCurrentDriver(), inputTagField).sendKeys(tag);
    }

    public AgentFeedbackWindow selectTagInSearch() {
        waitForElementToBeClickable(this.getCurrentDriver(), availableTagsContainer, 6);
        availableTagsContainer.click();
        return this;
    }

    public void deleteTags() {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        for (WebElement tag : this.getCurrentDriver().findElements(By.cssSelector(selectedButtonTagsCss))){
            tagsInput.findElement(By.cssSelector("svg")).click();
        }
//        waitForElementToBeVisibleByCss(this.getCurrentDriver(), cleareAll, 6);
//        findElemByCSS(this.getCurrentDriver(), cleareAll).click();
    }

    public String getPlaceholder() {
       return crmNoteTextField.getAttribute("placeholder");
    }

    public boolean isValidSentiments(File image) {
        return isWebElementEqualsImage(this.getCurrentDriver(), sentimentsAll, image);
    }

    public String getSelectedSentiment(){
        return getAttributeFromElem(this.getCurrentDriver(), selectedSentiment, 3, "Selected sentiment", "selenium-id");
    }

    public void closeDropdown(){
        clickElem(this.getCurrentDriver(), openDropdownButton, 5,"Close dropdown button" );
    }

}
