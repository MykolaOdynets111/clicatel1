package agentpages;

import abstractclasses.AgentAbstractPage;
import agentpages.uielements.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touchpages.uielements.AttachmentWindow;
import agentpages.uielements.LocationWindow;

import java.util.List;

public class AgentHomePage extends AgentAbstractPage {

    private final String chatMessageContainer = ".cl-chat-messages";
    private final String cancelCloseChatButtonXPATH = "//span[text()='Cancel']";
    private final String modalWindow = "div.modal-content";

    @FindBy(css = "div.agent-view--main")
    private WebElement conversationAreaContainer;

    @FindBy(css = ".cl-r-suggestions-count")
    private WebElement agentAssistantButton;

    @FindAll({
            @FindBy(xpath = "//li[text()='History']"),
            @FindBy(css = "[selenium-id='tab-right-panel-2']")
    })
    private WebElement agentHistoryButton;

    @FindAll({
            @FindBy(css = "[selenium-id='tab-right-panel-notes']"),
            @FindBy(xpath = " //li[text()='Notes']")
    })
    private WebElement agentNotesButton;

    @FindBy(xpath = "//p[@class='cl-pending-chat-mark-toast-content']")
    private WebElement pendingAlertMessage;

    private final String pinErrorMessageXpath = "//div[text()='You do not have the ability to close the chat when it has been flagged']";

    @FindBy(xpath = "//div[text()='You do not have the ability to close the chat when it has been flagged']")
    private WebElement pinErrorMessage;

    @FindBy(xpath = "//div[text()='Profanity not allowed']")
    private WebElement profanityPopup;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptProfanityPopupButton;

    @FindBy(css = ".toast-content-message")
    private WebElement connectionErrorImage;

    @FindBy(xpath = "//h4[text()='Agent limit reached']")
    private WebElement agentLimitReachedPopup;

    @FindBy(css = "div.history-details")
    private WebElement historyDetails;

    @FindBy(css = "div > div.active")
    private WebElement userProfileButton;

    @FindBy(css = "#right-panel .cl-r-tabs__tab.cl-r-tabs__tab--selected")
    private WebElement selectedTab;

    @FindBy(css = ".scrollable-roster .cl-empty-state")
    private WebElement tipNoteInConversationArea;

    @FindBy(css = ".dashboard .cl-empty-state")
    private WebElement tipNoteInRightArea;

    @FindBy(css = "[selenium-id = transfer-notification-waiting]")
    private List<WebElement> notificationsList;

    @FindBy(css =".cl-c2p-close-modal-note")
    private WebElement c2pMoveToPendingMessage;

    @FindBy(xpath ="//button[text()='Move to Pending']")
    private WebElement moveToPendingButton;

    @FindBy(css = "[role=dialog]")
    private WebElement loginDialog;

    private final String openedProfileWindow = "//div[@class='profile-modal-pageHeader modal-pageHeader']/parent::div";

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
    private Profile profile;
    private ChatHeader chatHeader;
    private AgentFeedbackWindow agentFeedbackWindow;
    private ChatHistoryContainer chatHistoryContainer;
    private HistoryDetailsWindow historyDetailsWindow;
    private ChatForm chatForm;
    private VerifyPhoneNumberWindow verifyPhoneNumberWindow;
    private ChatAttachmentForm chatAttachmentForm;
    private AttachmentWindow attachmentWindow;
    private LocationWindow locationWindow;
    private C2pSendForm c2pSendForm;
    private Extensions extensions;
    private HSMForm hsmForm;

    public AgentHomePage(String agent) {
        super(agent);
    }

    public ChatAttachmentForm getChatAttachmentForm() {
        chatAttachmentForm.setCurrentDriver(this.getCurrentDriver());
        return chatAttachmentForm;
    }

    public ChatForm getChatForm() {
        chatForm.setCurrentDriver(this.getCurrentDriver());
        return chatForm;
    }

    public Extensions getExtensionsForm(){
        extensions.setCurrentDriver(this.getCurrentDriver());
        return extensions;
    }

    public AttachmentWindow openAttachmentWindow(){
        getChatForm().openAttachmentForm();
        attachmentWindow.setCurrentDriver(this.getCurrentDriver());
        return attachmentWindow;
    }

    public LocationWindow openLocationWindow(){
        getChatForm().openLocationForm();
        locationWindow.setCurrentDriver(this.getCurrentDriver());
        return locationWindow;
    }

    public C2pSendForm openc2pSendForm(){
        getChatForm().openExtensionsForm();
        getExtensionsForm().openC2pForm();
        c2pSendForm.setCurrentDriver(this.getCurrentDriver());
        return c2pSendForm;
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
        if(!agentNotesButton.getAttribute("class").contains("selected")){
            agentNotesButton.click();
        }
        crmTicketContainer.setCurrentDriver(this.getCurrentDriver());
        return crmTicketContainer;
    }

    public HistoryDetailsWindow getHistoryDetailsWindow() {
        waitForElementToBeVisible(this.getCurrentDriver(), historyDetailsWindow, 5);
        historyDetailsWindow.setCurrentDriver(this.getCurrentDriver());
        return historyDetailsWindow;
    }

    public ChatHistoryContainer getChatHistoryContainer() {
        if(!agentHistoryButton.getAttribute("class").contains("selected")) {
            agentHistoryButton.click();
        }
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

    public Profile getProfile() {
        profile.setCurrentDriver(this.getCurrentDriver());
        return profile;
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
        profileWindow.setCurrentDriver(this.getCurrentDriver());
        return profileWindow;
    }

    public SuggestedGroup getSuggestedGroup() {
        suggestedGroup.setCurrentDriver(this.getCurrentDriver());
        return suggestedGroup;
    }

    public PageHeader getPageHeader() {
        pageHeader.setCurrentDriver(this.getCurrentDriver());
        return pageHeader;
    }

    public LeftMenuWithChats getLeftMenuWithChats() {
        leftMenuWithChats.setCurrentDriver(this.getCurrentDriver());
        return leftMenuWithChats;
    }

    public VerifyPhoneNumberWindow getVerifyPhoneNumberWindow() {
        verifyPhoneNumberWindow.setCurrentDriver(this.getCurrentDriver());
        return verifyPhoneNumberWindow;
    }

    public ChatBody getChatBody() {
        waitForElementToBePresentByCss(this.getCurrentDriver(), chatMessageContainer, 10);
        chatBody.setCurrentDriver(this.getCurrentDriver());
        return chatBody;
    }

    public HSMForm getHSMForm() {
        hsmForm.setCurrentDriver(this.getCurrentDriver());
        return hsmForm;
    }

    public boolean isAgentSuccessfullyLoggedIn() {
        if (isElementShown(this.getCurrentDriver(), conversationAreaContainer,50)) {
            return waitForLoadingInLeftMenuToDisappear(10, 20);
        } else { return false;}
    }

    public String isConnectionErrorShown(){
             return getTextFromElem(this.getCurrentDriver(), connectionErrorImage, 15, "Connection error");
    }

    public void endChat() {
        getChatHeader().clickEndChatButton();
        if (getAgentFeedbackWindow().isAgentFeedbackWindowShown()) {
            getAgentFeedbackWindow().waitForLoadingData().clickCloseButtonInCloseChatPopup();
        }
        try {
            waitForElementToBeInVisibleByCss(this.getCurrentDriver(), chatMessageContainer, 10);
        } catch (TimeoutException e) {
            Assert.fail("Chat container does not disappear after 10 second wait");
        }
    }

    public void clickAgentAssistantButton(){
        clickElem(this.getCurrentDriver(), agentAssistantButton,3,"Agent Assistant Button" );
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

    public String getPendingMessage(){
        return getTextFromElem(this.getCurrentDriver(), pendingAlertMessage,2,"Pending message");
    }

    public String getUserProfileButtonColor() {
        return Color.fromString(userProfileButton.getCssValue("background-color")).asHex();
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

    public String getC2pMoveToPendingMessage(){
        return getTextFromElem(this.getCurrentDriver(),c2pMoveToPendingMessage, 2, "c2p Move To Pending Message").trim();
    }

    public void clickMoveToPendingButton(){
        clickElem(this.getCurrentDriver(), moveToPendingButton, 1, "Move To Pending Button");
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

    public void waitForAgentPageToBeLoaded() throws TimeoutException{
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), profile, 5);
        } catch (TimeoutException e) {
        }
    }

    public boolean isLoginDialogShown(){
        return isElementShown(this.getCurrentDriver(), loginDialog, 10);
    }
}
