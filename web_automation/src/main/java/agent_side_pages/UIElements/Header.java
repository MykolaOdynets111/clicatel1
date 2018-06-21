package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.header")
public class Header extends AbstractUIElement {

    @FindBy(css = "button#top-menu-dropdown>div")
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

    public Header logOut() {
        waitForElementToBeVisible(iconWithAgentInitials);
        click(iconWithAgentInitials);
        waitForElementToBeVisible(logOutButton, 6);
        logOutButton.click();
        waitForElementsToBeVisibleByCssAgent(topMenuDropdownCSS, 6);
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
}
