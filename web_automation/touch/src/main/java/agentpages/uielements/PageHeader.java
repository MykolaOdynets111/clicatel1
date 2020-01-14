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

    private String topMenuDropdownCSS = "button#top-menu-dropdown";

    @FindAll({
         @FindBy(xpath = ".//a[text()='Log out']"), //old location
         @FindBy(xpath = ".//button[text()='Log out']")
    })
    private WebElement logOutButton;

    @FindAll({
        @FindBy(xpath = ".//a[text()='Profile Settings']"), //old location
        @FindBy(xpath = ".//button[text()='Profile settings']")
    })
    private WebElement profileSettingsButton;

    @FindAll({
        @FindBy(css = "li.user.dropdown-header>p>strong"), //old locator
        @FindBy(css = ".cl-profile-info__name")
    })
    private WebElement agentName;

    @FindAll({
        @FindBy(css = "li.user.dropdown-header>p>em"), //old locator
        @FindBy(css = ".cl-profile-info__type")
    })
    private WebElement agentRole;

    @FindAll({
            @FindBy(xpath = "//li[@class= 'user dropdown-header']/p[not(child::*)]"), //old locator
            @FindBy(css = ".cl-profile-info__email")
    })
    private WebElement agentEmail;

    private String statusElem = ".cl-label.cl-radio"; //""div.header div.radio-group label"; old locator

    @FindAll({
            @FindBy(css = "div.header div.radio-group label"), //old locator
            @FindBy(css = ".cl-label.cl-radio")
    })
    private List<WebElement> statusElems;

    @FindAll({
            @FindBy(css = "button#top-menu-dropdown>img"),
            @FindBy(css = ".cl-company-info img")
    })
    private WebElement agentIcon;

    @FindBy(css = "div.logo h1")
    private WebElement tenantName;

    @FindBy(css = "div.logo img")
    private WebElement tenantLogoBorder;

    @FindAll({
            @FindBy(xpath = "//div[@class='header']//img[@src]"), //old locator
            @FindBy(css = "div.cl-company-info img")
    })
    private WebElement tenantLogoImage;

    @FindAll({
            @FindBy(xpath = "//button[@id='agents-list-dropdown']"), //old locator
            @FindBy(css = ".cl-app-header__actions-box-item.cl-online-agents.cl-dropdown-box-h-trigger svg")
    })
    private WebElement headPhones;

    @FindAll({
            @FindBy(xpath = "//ul[@id='agents-list-menu']/li/div"), //old locator
            @FindBy(css = ".cl-dropdown-box__list-item.cl-dropdown-box__list-item--agents-list")
    })
    private List<WebElement> headPhonesList;

    private String tenantLogoBorderXpath = "//div[@class = 'cl-company-info']/img"; //"//div[contains(@class, 'logo')]//img"; old locator
    private String tenantNameXpath = "//div[@class = 'cl-company-info']" ;//"//div[contains(@class, 'logo')]//h1"; old locator

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
