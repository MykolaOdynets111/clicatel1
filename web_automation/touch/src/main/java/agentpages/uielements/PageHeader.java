package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-app-header")
public class PageHeader extends AbstractUIElement {

    @FindBy(css = ".cl-profile-info__icon-with-status")
    private WebElement icon;

    @FindBy(xpath = ".cl-profile-info__agent-name")
    private WebElement iconAgentName;

    private String topMenuDropdownCSS = "button#top-menu-dropdown";

    @FindBy(xpath = ".//a[text()='Log out']")
    private WebElement logOutButton;

    @FindBy(xpath = ".//a[text()='Profile Settings']")
    private WebElement profileSettingsButton;

    @FindBy(css = "li.user.dropdown-header>p>strong")
    private WebElement agentName;

    @FindBy(css = "li.user.dropdown-header>p>em")
    private WebElement agentRole;

    @FindBy(xpath = "//li[@class= 'user dropdown-header']/p[not(child::*)]")
    private WebElement agentEmail;

    private String statusElem = "div.header div.radio-group label";

    @FindBy(css = "div.header div.radio-group label")
    private List<WebElement> statusElems;

    @FindBy(css = "button#top-menu-dropdown>img")
    private WebElement agentIcon;

    @FindBy(css = "div.logo h1")
    private WebElement tenantName;

    @FindBy(css = "div.logo img")
    private WebElement tenantLogoBorder;

    @FindBy(xpath = "//div[@class='header']//img[@src]")
    private WebElement tenantLogoImage;

    @FindBy(xpath = "//button[@id='agents-list-dropdown']")
    private WebElement headPhones;

    @FindBy(xpath = "//ul[@id='agents-list-menu']/li/div")
    private List<WebElement> headPhonesList;

    private String tenantLogoBorderXpath = "//div[contains(@class, 'logo')]//img";
    private String tenantNameXpath = "//div[contains(@class, 'logo')]//h1";

    public PageHeader logOut() {
            waitForElementToBeVisible(this.getCurrentDriver(), icon, 5);
            icon.click();
            waitForElementToBeVisible(this.getCurrentDriver(), logOutButton, 6);
            logOutButton.click();
        return this;
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
        profileSettingsButton.click();
    }

    public void selectStatus(String status){
        if(!isElementShown(this.getCurrentDriver(), agentName, 2)){
            clickIcon();
        }
        WebElement targetElem = statusElems.stream().filter(e -> e.getText().equalsIgnoreCase(status)).findFirst().get();
        targetElem.click();
    }

    public boolean isAgentImageShown(){
        return isElementShown(this.getCurrentDriver(), agentIcon, 10);
    }

    public boolean isTenantImageShown(){
        return isElementShown(this.getCurrentDriver(), tenantLogoImage, 10);
    }

    public String getTenantNameColor() {
        return Color.fromString(findElemByXPATH(this.getCurrentDriver(), tenantNameXpath)
                .getCssValue("color")).asHex();
    }

    public String getTenantLogoBorderColor() {
        return Color.fromString(findElemByXPATH(this.getCurrentDriver(), tenantLogoBorderXpath)
                .getCssValue("border-bottom-color")).asHex();
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
}
