package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


@FindBy(css = "div.modal-content")
public class AgentFeedbackWindow extends AbstractUIElement {

    private String closeChatButtonXPATH = "//span[text()='Close Chat']";



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

    @FindBy(css = ".Select-arrow")
    private WebElement TagsDropdown;

    @FindBy(id = "CRMNote")
    private WebElement CRMNoteTextField;

    @FindBy(id = "CRMLink")
    private WebElement CRMLink;

    @FindBy(id = "CRMTicketNumber")
    private WebElement CRMTicketNumber;


    public void clickCancel() {
        waitForElementToBeVisibleAgent(cancelButton,7);
        cancelButton.click();
    }

    public void clickCloseButtonInCloseChatPopup (){
        if(ApiHelper.getFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_FEEDBACK")){
            waitForElementToBeVisibleByXpathAgent(closeChatButtonXPATH, 10, "main agent");
            findElemByXPATHAgent(closeChatButtonXPATH).click();
            waitForElementToBeInVisibleByXpathAgent(closeChatButtonXPATH, 5);
        }
    }

    public boolean isEndChatPopupShown (){
        return isElementShownAgent(closeChatButton,12);
    }

    public void typeCRMNoteTextField(String msg) {
        try {
            CRMNoteTextField.sendKeys(msg);
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with CRMNoteTextField.");
        }
    }

    public void typeCRMLink(String msg) {
        try {
            CRMLink.sendKeys(msg);
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with CRMLink.");
        }
    }

    public void typeCRMTicketNumber(String msg) {
        try {
            CRMTicketNumber.sendKeys(msg);
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with CRMTicketNumber.");
        }
    }

    public void setSentimentUnsatisfied() { sentimentUnsatisfied.click();
    }

    public void setSentimentNeutral() { sentimentNeutral.click();
    }

    public void setSentimentHappy() { sentimentHappy.click();
    }

}
