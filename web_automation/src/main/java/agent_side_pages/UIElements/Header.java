package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.header")
public class Header extends AbstractUIElement {

    @FindBy(css = "button#top-menu-dropdown>div")
    private WebElement iconWithAgentInitials;

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

    public Header logOut() {
        waitForElementToBeVisible(iconWithAgentInitials);
        click(iconWithAgentInitials);
        waitForElementToBeVisible(logOutButton, 6);
        logOutButton.click();
        return this;
    }

    public String getTextFromIcon(){
        return waitForElementToBeVisibleAgent(iconWithAgentInitials, 5).getText();
    }

    public void clickIconWithInitials(){
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
//        WebElement targetElem = findElemsByCSSAgent(statusElem).stream().filter(e -> e.getText().equalsIgnoreCase(status)).findFirst().get();
        WebElement targetElem = statusElems.stream().filter(e -> e.getText().equalsIgnoreCase(status)).findFirst().get();
        targetElem.click();
        //    executeJSclick(targetElem);
//        targetElem.findElement(By.cssSelector("input")).click();

    }
}
