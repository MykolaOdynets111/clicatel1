package agentpages;

import abstractclasses.AgentAbstractPage;
import agentpages.uielements.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class AgentHomePage extends AgentAbstractPage {

    private String chatContainer = "//ul[@class='chat-container']";
    private String cancelCloseChatButtonXPATH = "//span[text()='Cancel']";
    private String modalWindow = "div.modal-content";

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    @FindBy(xpath = "//div[text()='Agent Assistant']")
    private WebElement agentAssistantButton;

    private String pinErrorMessageXpath = "//div[text()='You do not have the ability to end the chat when it has been pinned']";

    @FindBy(xpath = "//div[text()='You do not have the ability to end the chat when it has been pinned']")
    private WebElement pinErrorMessage;

    @FindBy(xpath = "//h4[text()='Profanity not allowed']")
    private WebElement profanityPopup;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptProfanityPopupButton;

    @FindBy(css = "span.connection-error-img")
    private WebElement connectionErrorImage;

    @FindBy(xpath = "//h4[text()='Agent limit reached']")
    private WebElement agentLimitReachedPopup;

    @FindBy(css = "div.history-details")
    private WebElement historyDetails;

    @FindBy(css = "div > div.active")
    private WebElement customer360Button;

    @FindBy(css = "div.context-menu>div.active")
    private WebElement selectedTab;

    @FindBy(css = "div.tip-note")
    private WebElement tipNoteInConversationArea;

    @FindBy(css = "div.context-wrapper>div.tip-note")
    private WebElement tipNoteInRightArea;

    @FindBy(xpath = "//div[@class='touch-notification']//child::h2[text()='Transfer waiting']")
    private List<WebElement> notificationsList;

    private String openedProfileWindow = "//div[@class='profile-modal-pageHeader modal-pageHeader']/parent::div";

    private DeleteCRMConfirmationPopup deleteCRMConfirmationPopup;
    private EditCRMTicketWindow editCRMTicketWindow;
    private CRMTicketContainer crmTicketContainer;
    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;
    private PageHeader pageHeader;
    private SuggestedGroup suggestedGroup;
    private ProfileWindow profileWindow;
    private TransferChatWindow transferChatWindow;
    private IncomingTransferWindow incomingTransferWindow;
    private Customer360Container customer360Container;
    private ChatHeader chatHeader;
    private AgentFeedbackWindow agentFeedbackWindow;
    private ChatHistoryContainer chatHistoryContainer;
    private HistoryDetailsWindow historyDetailsWindow;
    private ChatForm chatForm;
    private VerifyPhoneNumberWindow verifyPhoneNumberWindow;

    public AgentHomePage(String agent) {
        super(agent);
    }

    public ChatForm getChatForm() {
        chatForm.setCurrentDriver(this.getCurrentDriver());
        return chatForm;
    }

    public DeleteCRMConfirmationPopup getDeleteCRMConfirmationPopup(){
        deleteCRMConfirmationPopup.setCurrentDriver(this.getCurrentDriver());
        return deleteCRMConfirmationPopup;
    }

    public EditCRMTicketWindow getEditCRMTicketWindow() {
        editCRMTicketWindow.setCurrentDriver(this.getCurrentDriver());
        return editCRMTicketWindow;
    }

    public CRMTicketContainer getCrmTicketContainer() {
        crmTicketContainer.setCurrentDriver(this.getCurrentDriver());
        return crmTicketContainer;
    }

    public HistoryDetailsWindow getHistoryDetailsWindow() {
        historyDetailsWindow.setCurrentDriver(this.getCurrentDriver());
        return historyDetailsWindow;
    }

    public ChatHistoryContainer getChatHistoryContainer() {
        chatHistoryContainer.setCurrentDriver(this.getCurrentDriver());
        return chatHistoryContainer;
    }

    public AgentFeedbackWindow getAgentFeedbackWindow() {
        agentFeedbackWindow.setCurrentDriver(this.getCurrentDriver());
        return agentFeedbackWindow;
    }

    public ChatHeader getChatHeader() {
        chatHeader.setCurrentDriver(this.getCurrentDriver());
        return chatHeader;
    }

    public Customer360Container getCustomer360Container() {
        customer360Container.setCurrentDriver(this.getCurrentDriver());
        return customer360Container;
    }

    public IncomingTransferWindow getIncomingTransferWindow() {
        incomingTransferWindow.setCurrentDriver(this.getCurrentDriver());
        return incomingTransferWindow;
    }

    public TransferChatWindow getTransferChatWindow() {
        transferChatWindow.setCurrentDriver(this.getCurrentDriver());
        return transferChatWindow;
    }

    public ProfileWindow getProfileWindow() {
        return profileWindow;
    }

    public SuggestedGroup getSuggestedGroup() {
        return suggestedGroup;
    }

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public LeftMenuWithChats getLeftMenuWithChats() {
        return leftMenuWithChats;
    }

    public VerifyPhoneNumberWindow getVerifyPhoneNumberWindow() {
        verifyPhoneNumberWindow.setCurrentDriver(this.getCurrentDriver());
        return verifyPhoneNumberWindow;
    }

    public ChatBody getChatBody() {
        chatBody.setCurrentDriver(this.getCurrentDriver());
        return chatBody;
    }


    public boolean isAgentSuccessfullyLoggedIn(String ordinalAgentNumber) {
        if (isElementShown(this.getCurrentDriver(), conversationAreaContainer,10)) {
            return waitForLoadingInLeftMenuToDisappear(6, 10);
        } else { return false;}
    }

    public boolean isConnectionErrorShown(String ordinalAgentNumber){
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), connectionErrorImage, 10);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public void endChat(){
        if(getChatHeader().isEndChatShown()){
            getChatHeader().clickEndChatButton();
            getAgentFeedbackWindow().clickCloseButtonInCloseChatPopup();
            try {
                waitForElementToBeInvisibleByXpath(this.getCurrentDriver(),chatContainer, 5);
            }catch (TimeoutException e){
                Assert.fail("Chat container does not disappear after 5 second wait");
            }
        }
    }

    public void clickAgentAssistantButton(){
        agentAssistantButton.click();
    }

    public boolean isProfanityPopupShown(){
        return isElementShown(this.getCurrentDriver(), profanityPopup,10);
    }

    public void clickAcceptProfanityPopupButton(){
        acceptProfanityPopupButton.click();
        for(int i = 0; i<10;i++){
            if (!isElementShown(this.getCurrentDriver(), profanityPopup, 1)){
                break;
            }
        }
    }

    public boolean isAgentLimitReachedPopupShown(int wait){
        return isElementShown(this.getCurrentDriver(), agentLimitReachedPopup,wait);
    }

    public void isPinErrorMassageShown(String Agent){
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), pinErrorMessage, 10);
        }
        catch (TimeoutException e){
            Assert.fail("There is no Error message for pin chat");
        }
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), pinErrorMessageXpath, 10);
    }

    public String getCustomer360ButtonColor() {
        return Color.fromString(customer360Button.getCssValue("background-color")).asHex();
    }

    public String getSelectedTabHeader(){
        return selectedTab.getText();
    }

    public String getTipIfNoChatSelected(){
        return getTextFromElem(this.getCurrentDriver(), tipNoteInConversationArea, 5, "Tips in conversation area if no chat selected");
    }


    public String getTipIfNoChatSelectedFromContextArea(){
        return getTextFromElem(this.getCurrentDriver(), tipNoteInRightArea, 5, "Tips in context area if no chat selected");
    }

    public List<WebElement> getCollapsedTransfers(){
        waitForElementsToBeVisible(this.getCurrentDriver(), notificationsList, 6);
        return notificationsList;
    }

    public void acceptAllTransfers(){
        try {
            for (WebElement elem : getCollapsedTransfers()) {
                elem.click();
                getIncomingTransferWindow().acceptTransfer();
            }
        }catch(TimeoutException o){

        }
    }

    public void waitForModalWindowToDisappear(){
        waitForElementToBeInVisibleByCss(this.getCurrentDriver(), modalWindow, 6);
    }
}
