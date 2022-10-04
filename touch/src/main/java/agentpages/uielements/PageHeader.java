package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-app-header")
public class PageHeader extends AbstractUIElement {

    @FindBy(css = ".cl-profile-info__icon-with-status")
    private WebElement icon;

    @FindBy(css = ".cl-profile-info__agent-name")
    private WebElement iconAgentName;

    @FindBy(css = "[data-testid=logout-button]")
    private WebElement logOutButton;

    @FindBy(css = "[data-testid=profile-settings-button]")
    private WebElement profileSettingsButton;

    @FindAll({
        @FindBy(css = "[selenium-id=agent-fullname]"),
        @FindBy(css = "[data-testid=agent-fullname]")})
    private WebElement agentName;

    @FindBy(css = "[selenium-id=agent-role]")
    private WebElement agentRole;

    @FindBy(css = "[selenium-id=agent-email]")
    private WebElement agentEmail;

    @FindBy(xpath = "//input[@value = 'AVAILABLE']/ancestor::label")
    private WebElement statusActive;

    @FindBy(xpath = "//input[@value = 'UNAVAILABLE']/ancestor::label")
    private WebElement statusUnavailable;

    @FindBy(css = "[selenium-id=company-info-logo]")
    private WebElement agentIcon;

    @FindBy(css = "[selenium-id=company-info-text]")
    private WebElement tenantName;

    @FindAll({
            @FindBy(css = "[selenium-id=agents-list-wrapper]"),
            @FindBy(css = "[data-testid=agents-list-wrapper]")
    })
    private WebElement headPhones;
    @FindAll({
            @FindBy(css = "[selenium-id=agents-list]"),
            @FindBy(css = "[data-testid=agents-list]")    })
    private List<WebElement> headPhonesList;

    @FindBy(css= "[name='message-forward']")
    private WebElement forwadIcon;

    @FindAll({
            @FindBy(css = "[selenium-id=header-whats-app-button]"),
            @FindBy(css = "[data-testid=header-whats-app-button]")
    })
    private WebElement sendWhatsAppButton;

    public PageHeader logOut() {
            waitForElementToBeVisible(this.getCurrentDriver(), icon, 5);
            icon.click();
            waitForElementToBeVisible(this.getCurrentDriver(), logOutButton, 6);
            logOutButton.click();
        return this;
    }

    public boolean isIconPresent(){
        return waitForElementToBeVisible(this.getCurrentDriver(), icon, 2).isDisplayed();
    }
    public String getAgentFirstNameFromIcon(){
        return waitForElementToBeVisible(this.getCurrentDriver(), iconAgentName, 5).getText();
    }

    public void clickIcon(){
        waitForElementToBeClickable(this.getCurrentDriver(), icon, 10);
        icon.click();
    }

    public String getAgentName(){
        return agentName.getText();
    }

    public String getAgentRole(){
        return agentRole.getText();
    }

    public String getAgentEmail(){
        return agentEmail.getText();
    }

    public void clickProfileSettingsButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), profileSettingsButton, 10);
        profileSettingsButton.click();
    }

    public void selectStatus(String status) {
        if (!isElementShown(this.getCurrentDriver(), agentName, 2)) {
            clickIcon();
        }
        switch (status.toLowerCase()) {
            case "available":
                clickElem(this.getCurrentDriver(), statusActive, 2, "Active status checkbox");
                break;
            case "unavailable":
                clickElem(this.getCurrentDriver(), statusUnavailable, 2, "Away status checkbox");
                break;
        }
    }

    public boolean isTenantImageShown(){
        return isElementShown(this.getCurrentDriver(), agentIcon, 10);
    }

    public String getTenantNameColor() {
        return Color.fromString(tenantName.getCssValue("color")).asHex();
    }

    public String getTenantLogoBorderColor() {
        return Color.fromString(agentIcon.getCssValue("border-bottom-color")).asHex();
     }

    public void clickHeadPhonesButton(){
        clickElem(this.getCurrentDriver(), headPhones,3, "Head Phones button");
    }

    public List<String> getAvailableAgents(){
        return headPhonesList.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public boolean isExpectedNumbersShown(int expNumber, int wait){
        for(int i = 0; i<wait; i++){
            if(getAvailableAgents().size()==expNumber) return true;
            else waitFor(500);
        }
        return false;
    }

    public boolean isValidIconDisplayedOnHeader() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/profileicons/user_default.png");
        return  isWebElementEqualsImage(this.getCurrentDriver(), icon, image);
    }

    public void clickOnWhatsapp(){
        clickElem(this.getCurrentDriver(),forwadIcon,1,"forward Icon");
        scrollAndClickElem(this.getCurrentDriver(), sendWhatsAppButton, 5,"Click on Whatsapp Icon");
    }

}
