package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.modal-content")
public class AgentFeedbackWindow extends AbstractUIElement {

    private String closeChatButtonXPATH = "//span[text()='Close Chat']";

    @FindBy(xpath = "//span[text()='Close Chat']")
    private WebElement closeChatButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

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

}
