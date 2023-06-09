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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;


@FindBy(css = "div.cl-modal")
public class AgentFeedbackWindow extends AbstractUIElement {

    @FindBy(css = "[data-testid=exit-chat-modal-cancel]")
    private WebElement cancelButton;

    @FindBy(css = "[data-testid=exit-chat-modal-save]")
    private WebElement closeChatButton;

    @FindBy(xpath = "//*[contains(text(),'Go to chat')]")
    private WebElement goToChatButton ;

    @FindBy(css = "[data-testid=sentiment-icon-negative]")
    private WebElement sentimentUnsatisfied ;

    @FindBy(css = "[data-testid=sentiment-icon-positive]")
    private WebElement sentimentHappy;

    @FindBy(xpath = ".//label[contains(@data-testid, 'sentiment-icon') and contains(@class, 'active')]")
    private WebElement selectedSentiment;

    @FindBy(css = "[data-testid=chat-sentiment-icons]")
    private WebElement sentimentsAll;

    @FindBy(css = "[class='css-1rhbuit-multiValue cl-select__multi-value']")
    private WebElement availableTagsContainer;

    @FindBy(css = ".cl-select__control")
    private WebElement tagsInput;

    @FindBy(css = "div[id^='react-select']")
    private List<WebElement> availableTags;

    @FindBy(xpath = "//label[text() = 'Tags']/..//div/*[name() = 'svg']")
    private WebElement openDropdownButton;

    private final String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(css = ".cl-select__input")
    private WebElement inputTagField;

    private final String tagsOptionsCss = "div[id^='react-select']";

    private final String selectedButtonTagsCss = ".cl-select__multi-value";

    @FindBy(css = "[data-testid='crm-note']")
    private WebElement crmNoteTextField;

    @FindBy(css = "[data-testid='crm-link']")
    private WebElement crmLink;

    @FindBy(css = "[data-testid='crm-ticket-number']")
    private WebElement crmTicketNumber;

    public boolean isAgentFeedbackWindowShown(){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(),2);
    }
    
    public AgentFeedbackWindow waitForLoadingData(){
        waitForAppearAndDisappearByCss(this.getCurrentDriver(), ".cl-animated-ellipsis",  3, 5);
        return this;
    }

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 5, "Cancel button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public void clickCloseButtonInCloseChatPopup(){
        scrollAndClickElem(this.getCurrentDriver(), closeChatButton, 10, "Close button");
    }

    public void clickGoToChatButtonInCloseChatPopup(){
        scrollAndClickElem(this.getCurrentDriver(), goToChatButton, 5, "Go to chat button");
    }

    public boolean isEndChatPopupShown (){
        return isElementShown(this.getCurrentDriver(), closeChatButton,5);
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

    public void setSentimentHappy() {
        clickElem(this.getCurrentDriver(), sentimentHappy, 5,"Sentiment Happy" );
    }


    public void selectTags(int iter) {
        for (int i = 1; i<=iter; i++){
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
        moveToElemAndClick(this.getCurrentDriver(), availableTags.get(ordinalNumber));
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
        inputText(this.getCurrentDriver(), inputTagField, 5,"Tags Input field",tag);
        pressEnterForWebElem(this.getCurrentDriver(), inputTagField, 5,"Tags Input field");
    }

    public AgentFeedbackWindow selectTag(String tag) {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        availableTags.stream().filter(t -> t.getText().equals(tag)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(tag + " tag is absent"))
                .click();

        assertTrue(format("Tag %s should be selected!", tag), getChosenTags().contains(tag));
        return this;
    }

    public List<String> getTagList() {
        waitForElementToBeClickable(this.getCurrentDriver(), openDropdownButton, 6);
        tagsInput.click();
        return availableTags.stream().map(WebElement::getText).collect(Collectors.toList());
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
        return getAttributeFromElem(this.getCurrentDriver(), selectedSentiment, 3, "Selected sentiment", "data-testid");
    }

    public void closeDropdown(){
        clickElem(this.getCurrentDriver(), openDropdownButton, 5,"Close dropdown button" );
    }
}
