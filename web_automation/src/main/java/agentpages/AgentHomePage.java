package agentpages;

import abstractclasses.AgentAbstractPage;
import agentpages.uielements.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AgentHomePage extends AgentAbstractPage {

    private String chatContainer = "//ul[@class='chat-container']";
    private String cancelCloseChatButtonXPATH = "//span[text()='Cancel']";

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    @FindBy(xpath = "//div[text()='Agent Assistant']")
    private WebElement agentAssistantButton;

    private String pinErrorMessageXpath = "//div[text()='You do not have the ability to end the chat when it has been pinned']";

    @FindBy(xpath = "//div[text()='You do not have the ability to end the chat when it has been pinned']")
    private WebElement pinErrorMessage;

    @FindBy(xpath = "//div[text()='Profanity not allowed']")
    private WebElement profanityPopup;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptProfanityPopupButton;

    @FindBy(css = "span.connection-error-img")
    private WebElement connectionErrorImage;

    @FindBy(xpath = "//div[text()='Agent limit reached']")
    private WebElement agentLimitReachedPopup;

    @FindBy(css = "div.history-details")
    private WebElement historyDetails;

    @FindBy(css = "div > div.active")
    private WebElement customer360Button;

    @FindBy(css = "div.touch-button")
    private WebElement touchButton;

    @FindBy(css = "div.context-menu>div.active")
    private WebElement selectedTab;

    @FindBy(css = "div.tip-note")
    private WebElement tipNoteInConversationArea;

    @FindBy(css = "div.context-wrapper>div.tip-note")
    private WebElement tipNoteInRightArea;

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

    public AgentHomePage(String agent) {
        super(agent);
    }

    public ChatForm getChatForm() {
        chatForm.setCurrectAgent(this.getCurrentAgent());
        return chatForm;
    }

    public DeleteCRMConfirmationPopup getDeleteCRMConfirmationPopup(){
        return deleteCRMConfirmationPopup;
    }

    public EditCRMTicketWindow getEditCRMTicketWindow() {
        return editCRMTicketWindow;
    }

    public CRMTicketContainer getCrmTicketContainer() {
        return crmTicketContainer;
    }

    public HistoryDetailsWindow getHistoryDetailsWindow() {
        return historyDetailsWindow;
    }

    public ChatHistoryContainer getChatHistoryContainer() {
        return chatHistoryContainer;
    }

    public AgentFeedbackWindow getAgentFeedbackWindow() {
        return agentFeedbackWindow;
    }

    public ChatHeader getChatHeader() {
        return chatHeader;
    }

    public Customer360Container getCustomer360Container() {
        return customer360Container;
    }

    public IncomingTransferWindow getIncomingTransferWindow() {
        return incomingTransferWindow;
    }

    public TransferChatWindow getTransferChatWindow() {return transferChatWindow;}

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

    public ChatBody getChatBody() {
        return chatBody;
    }


    public boolean isAgentSuccessfullyLoggedIn(String ordinalAgentNumber) {
        if (isElementShownAgent(conversationAreaContainer,10, ordinalAgentNumber)) {
            return waitForLoadingInLeftMenuToDisappear(ordinalAgentNumber, 6, 10);
        } else { return false;}
    }

    public boolean isConnectionErrorShown(String ordinalAgentNumber){
        try{
            waitForElementToBeVisibleAgent(connectionErrorImage, 10, ordinalAgentNumber);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public void endChat(){
        if(getChatHeader().isEndChatShown(getCurrentAgent())){
            getChatHeader().clickEndChatButton();
            getAgentFeedbackWindow().clickCloseButtonInCloseChatPopup();
            try {
                waitForElementsToBeInvisibleByXpathAgent(chatContainer, 5, getCurrentAgent());
            }catch (TimeoutException e){
                Assert.fail("Chat container does not disappear after 5 second wait");
            }
        }
    }

    public void clickAgentAssistantButton(){
        agentAssistantButton.click();
    }

    public boolean isProfanityPopupShown(){
        return isElementShownAgent(profanityPopup,10);
    }

    public void clickAcceptProfanityPopupButton(){
        acceptProfanityPopupButton.click();
        for(int i = 0; i<10;i++){
            if (!isElementShownAgent(profanityPopup, 1, "main agent")){
                break;
            }
        }
    }

    public boolean isAgentLimitReachedPopupShown(int wait){
        return isElementShownAgent(agentLimitReachedPopup,wait);
    }

    public void isPinErrorMassageShown(String Agent){
        try{
            waitForElementToBeVisibleAgent(pinErrorMessage, 10, Agent);
        }
        catch (TimeoutException e){
            Assert.assertTrue(false,
                    "There is no Error message for pin chat");
        }
        waitForElementsToBeInvisibleByXpathAgent(pinErrorMessageXpath,10, Agent);
    }

    public String getCustomer360ButtonColor() {
        return Color.fromString(customer360Button.getCssValue("background-color")).asHex();
    }

    public String getTouchButtonColor() {
        return Color.fromString(touchButton.getCssValue("background-color")).asHex();
    }

    public String getSelectedTabHeader(){
        return selectedTab.getText();
    }

    public String getTipIfNoChatSelected(){
        return getTextFromElemAgent(tipNoteInConversationArea, 5, getCurrentAgent(), "Tips in conversation area if no chat selected");
    }


    public String getTipIfNoChatSelectedFromContextArea(){
        return getTextFromElemAgent(tipNoteInRightArea, 5, getCurrentAgent(), "Tips in context area if no chat selected");
    }

}
