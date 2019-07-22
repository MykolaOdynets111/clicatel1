package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.header")
public class PageHeader extends AbstractUIElementDeprecated {

    @FindBy(css = "button#top-menu-dropdown")
    private WebElement iconWithAgentInitials;

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

    public PageHeader logOut(String agent) {
            waitForElementToBeVisibleAgent(iconWithAgentInitials, 5, agent);
            iconWithAgentInitials.click();
            waitForElementToBeVisibleAgent(logOutButton, 6, agent);
            logOutButton.click();
//        waitForElementsToBeVisibleByCssAgent(topMenuDropdownCSS, 6,agent);
        return this;
    }

    public String getTextFromIcon(){
        return waitForElementToBeVisibleAgent(iconWithAgentInitials, 5).getText();
    }

    public void clickIconWithInitials(){
        waitForElementToBeClickableAgent(iconWithAgentInitials, 10, "main agent");
        iconWithAgentInitials.click();
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
        if(!isElementShown(agentName)){
            clickIconWithInitials();
        }
//        WebElement targetElem = findElemsByCSSAgent(statusElem).stream().filter(e -> e.getText().equalsIgnoreCase(status)).findFirst().get();
        WebElement targetElem = statusElems.stream().filter(e -> e.getText().equalsIgnoreCase(status)).findFirst().get();
        targetElem.click();
        //    executeJSclick(targetElem);
//        targetElem.findElement(By.cssSelector("input")).click();
    }

    public boolean isAgentImageShown(){
        return isElementShownAgent(agentIcon, 10, "main agent");
    }

    public boolean isTenantImageShown(){
        return isElementShownAgent(tenantLogoImage, 10, "main agent");
    }

    public String getTenantNameColor() {
        return Color.fromString(findElemByXPATHAgent(tenantNameXpath,"second agent").getCssValue("color")).asHex();
    }

    public String getTenantLogoBorderColor() {
        return Color.fromString(findElemByXPATHAgent(tenantLogoBorderXpath,"second agent").getCssValue("border-bottom-color")).asHex();
    }

    public void clickHeadPhonesButton(String agent){
        clickElemAgent(headPhones,3,agent, "Head Phones button");
    }

    public List<String> getAvailableAgents(){
        return headPhonesList.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }
}
