package agentpages.dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

public class SettingsPage extends PortalAbstractPage {

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[selenium-id='tab-navigation-panel-business-profile']")
    private WebElement businessProfile;

    @FindBy(css = "[selenium-id='tab-navigation-panel-chat-tags']")
    private WebElement chatTags;

    @FindBy(css = "[selenium-id='tab-navigation-panel-auto-responders']")
    private WebElement autoResponders;

    @FindBy(css = "[selenium-id='tab-navigation-panel-preferences']")
    private WebElement preferences;

    @FindBy(css = "[selenium-id='tab-navigation-panel-surveys']")
    private WebElement surveysNavigation;

    public void openSettingsPage(String settingsName){
        switch (settingsName) {
            case "Business Profile":
                clickElem(this.getCurrentDriver(), businessProfile, 2, "Business Profile");
                break;
            case "Chat Tags":
                clickElem(this.getCurrentDriver(), chatTags, 2, "Chat Tags");
                break;
            case "Auto Responders":
                clickElem(this.getCurrentDriver(), autoResponders, 2, "Auto Responders");
                break;
            case "Preferences":
                clickElem(this.getCurrentDriver(), preferences, 2, "Preferences");
                break;
            case "Surveys":
                clickElem(this.getCurrentDriver(), surveysNavigation, 2, "Surveys");
        }
    }

}
