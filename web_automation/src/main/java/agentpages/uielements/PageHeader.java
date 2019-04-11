package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.header")
public class PageHeader extends AbstractUIElement {

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
}