package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@FindBy(css = ".cl-app-header")
public class PageHeader extends AbstractUIElement {

    @FindBy(css = ".cl-profile-info__icon-with-status")
    private WebElement icon;

    @FindBy(css = "[data-testid=logout-button]")
    private WebElement logOutButton;

    @FindBy(css = "[data-testid=profile-settings-button]")
    private WebElement profileSettingsButton;

    @FindAll({
            @FindBy(css = "[selenium-id=agent-fullname]"),
            @FindBy(css = "[data-testid=agent-fullname]")})
    private WebElement agentName;

    @FindBy(xpath = "//input[@value = 'AVAILABLE']/ancestor::label")
    private WebElement statusActive;

    @FindBy(xpath = "//input[@value = 'UNAVAILABLE']/ancestor::label")
    private WebElement statusUnavailable;

    @FindBy(css = "[selenium-id=company-info-logo]")
    private WebElement agentIcon;

    @FindBy(css = "[selenium-id=company-info-text]")
    private WebElement tenantName;

    @FindBy(css = "img[data-testid = 'company-info-logo']")
    private WebElement tenantLogo;

    @FindBy(css = "[data-testid=agents-list-wrapper] svg")
    private WebElement headPhones;
    @FindAll({
            @FindBy(css = "[selenium-id=agents-list]"),
            @FindBy(css = "[data-testid=agents-list]")})
    private List<WebElement> headPhonesList;

    @FindBy(css = "[name='message-forward']")
    private WebElement forwadIcon;

    @FindBy(css = "[selenium-id=agent-email]")
    private WebElement agentEmail;

    @FindBy(css = ".cl-profile-info__agent-name")
    private WebElement profileInitials;

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

    public boolean verifyUserStatusOnIcon(String status) {
        isElementShown(this.getCurrentDriver(), icon, 5);
        WebElement statusIcon = icon.findElement(By.cssSelector(format(" [data-status = '%s']", status.toUpperCase())));
        return isElementShown(this.getCurrentDriver(), statusIcon, 10);
    }

    public void clickIcon() {
        waitForElementToBeClickable(this.getCurrentDriver(), icon, 10);
        moveToElemAndClick(this.getCurrentDriver(), icon);
    }

    public String getAgentName() {
        return agentName.getText();
    }

    public void clickProfileSettingsButton() {
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

    public boolean isTenantImageShown() {
        return isElementShown(this.getCurrentDriver(), agentIcon, 10);
    }

    public String getTenantNameColor() {
        return Color.fromString(tenantName.getCssValue("color")).asHex();
    }

    public String getTenantLogoBorderColor() {
        return Color.fromString(agentIcon.getCssValue("border-bottom-color")).asHex();
    }

    public String getAgentEmail(){
        return agentEmail.getText();
    }

    public String getAgentInitials() {
        return profileInitials.getText();
    }

    public void clickHeadPhonesButton() {
        clickElem(this.getCurrentDriver(), headPhones, 3, "Head Phones button");
    }

    public List<String> getAvailableAgents() {
        return headPhonesList.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public boolean isExpectedNumbersShown(int expNumber, int wait) {
        for (int i = 0; i < wait; i++) {
            if (getAvailableAgents().size() == expNumber) return true;
            else waitFor(500);
        }
        return false;
    }

    public void clickOnWhatsapp() {
        clickElem(this.getCurrentDriver(), forwadIcon, 1, "forward Icon");
        scrollAndClickElem(this.getCurrentDriver(), sendWhatsAppButton, 5, "Click on Whatsapp Icon");
    }

    public boolean isTenantLogoShown() {
        return isElementShown(this.getCurrentDriver(), tenantLogo, 10);
    }
}
