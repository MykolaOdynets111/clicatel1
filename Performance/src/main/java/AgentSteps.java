import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AgentSteps implements Waits {

    private WebDriver driver;

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindBy(css = "div.cl-card--details-avatar")
    private WebElement adminIcon;

    @FindBy(xpath = "//span[@class='menu-item-title ng-binding' and text()='Touch']/ancestor::div[@ng-repeat]")
    private WebElement touchMenu;

    @FindBy(xpath = "//span[@class='menu-item-title ng-binding' and text()='Agent Desk']/ancestor::div[@class='menu-item menu-item-deskAgent test']")
    private WebElement agentDesk;

    @FindBy(css = "div.account-form button.button.button-primary")
    private WebElement loginButton;

    @FindBy(css = "textarea[selenium-id=message-composer-textarea]")
    private WebElement messageInput;

    @FindBy(css=".cl-r-message-composer__send-button")
    private WebElement sendButton;

    @FindBy(xpath ="//div[@class='cl-r-chat-header-btn-label' and text() = 'Transfer']")
    private WebElement transferButton;

    @FindBy(xpath = "//div[text()='Select an agent']")
    private WebElement agentDropdown;

    @FindBy(css =".cl-r-select__menu")
    private WebElement selectMenu;

    @FindBy(css = ".cl-r-modal-buttons .cl-r-button.cl-r-button--primary")
    private WebElement transferButtonOnModal;

    @FindBy(css = ".cl-r-form-control.cl-r-form-control--textarea")
    private WebElement noteForm;

    @FindBy(css = ".cl-r-transfer-chat-flyout .cl-r-button.cl-r-button--primary")
    private WebElement acceptIncomingTransferButton;

    @FindBy(xpath = "//div[text()='Close Chat']")
    private WebElement closeChatButton;


    String connectionMessage = "//span[text()='Connected']";

    String chatName = "//h2[@class = 'cl-r-roster-item-user-name' and text() = '%s']";

    String connectionMessageFromBot = "//li[@class = 'cl-r-message msg-bot_message to']//div[contains(text(), 'You have now been connected with')]";

    String messageFromUser = "//li[@class = 'cl-r-message msg-client_message from']//div[contains(text(), '%s')]";

    String transferAgentXpath = "//div[contains(@id, 'react-select') and text() = '%s']";

    public AgentSteps(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AgentSteps openManePage(){
        driver.get(URL.agentLoginPage);
        return this;
    }

    public AgentSteps loginWithAgent(Credentials agent){
        waitForElementToBeClickable(driver, emailInput, 30);
        emailInput.clear();
        emailInput.sendKeys(agent.getEmail());
        passInput.clear();
        passInput.sendKeys(agent.getUserPass());
        waitForElementToBeClickable(driver, loginButton, 20);
        loginButton.click();
        waitForElementToBeClickable(driver, adminIcon, 60);
        return this;
    }

    public AgentSteps loginWithAgents(Agents agent){
        waitForElementToBeClickable(driver, emailInput, 30);
        emailInput.clear();
        emailInput.sendKeys(agent.getEmail());
        passInput.clear();
        passInput.sendKeys(agent.getUserPass());
        waitForElementToBeClickable(driver, loginButton, 20);
        loginButton.click();
//        waitForElementToBeClickable(driver, adminIcon, 60);
        return this;
    }

    public void takeScreenshot(Agents agent){
        System.out.println(agent.name() + " take screenshot " + agent.getEmail());
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        String pathForSave = System.getProperty("user.dir") +"\\src\\test\\screenshots\\" + agent.name() +".png";
        File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(pathForSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public AgentSteps openAgentDesk(){
       driver.get(URL.agentDesk);
       return this;
    }

    public AgentSteps openSupervisorDesk(){
        driver.get(URL.supervisorDesk);
        return this;
    }

    public AgentSteps waitForConnectionToAgentDesk(){
        waitForElementToBePresentByXpath(driver, connectionMessage, 200);
        return this;
    }

    public AgentSteps waitForChatArrived(String userName){
        waitForElementToBeVisibleByXpath(driver,String.format(chatName, userName), 200);
        return this;
    }

    public AgentSteps openChat(String userName){
        driver.findElement(By.xpath(String.format(chatName, userName))).click();
        return this;
    }

    public AgentSteps waitForBotMessageLoad(){
        waitForElementToBeVisibleByXpath(driver, connectionMessageFromBot,100);
        return this;
    }

    public AgentSteps sendMessage(String message){
        waitForElementToBeClickable(driver, messageInput, 100);
        messageInput.clear();
        messageInput.sendKeys(message);
        sendButton.click();
        return this;
    }

    public AgentSteps waitForMessageFromUser(String message){
        waitForElementToBePresentByXpath(driver, String.format(messageFromUser,message), 100);
        return this;
    }

    public AgentSteps openTransferWindow(){
        transferButton.click();
        waitForElementToBeClickable(driver, agentDropdown, 100);
        return this;
    }

    public AgentSteps selectAgentAndNote(String name){
        selectAgent(name);
        noteForm.sendKeys("performance");
        return this;
    }

    private void selectAgent(String name){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        agentDropdown.click();
        for(int i =0; i<500; i++){
                if (isElementShown(driver, selectMenu, 0)){
                      if (isElementShownByXpath(driver,String.format(transferAgentXpath, name),0)){
                          driver.findElement(By.xpath(String.format(transferAgentXpath, name))).click();
                            break;
                      }
                }else {
                    agentDropdown.click();
                }
            wait(100);
        }
        driver.manage().timeouts().implicitlyWait(BrowserImplementation.WAIT, TimeUnit.SECONDS);
    }

    public AgentSteps clickTransferButton(){
        waitForElementToBeClickable(driver, transferButtonOnModal, 100);
        transferButtonOnModal.click();
        return this;
    }

    public AgentSteps waitForIncomingTransferAppear(){
        waitForElementToBeClickable(driver, acceptIncomingTransferButton, 100);
        return this;
    }

    public AgentSteps acceptIncomingTransfer(){
        acceptIncomingTransferButton.click();
        return this;
    }

    public AgentSteps closeChat(String userName){
        closeChatButton.click();
        waitUntilElementNotDisplayed(driver, driver.findElement(By.xpath(String.format(chatName, userName))), 100);
        return this;
    }

}
