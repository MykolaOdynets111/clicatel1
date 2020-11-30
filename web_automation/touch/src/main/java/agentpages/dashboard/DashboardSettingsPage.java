package agentpages.dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

public class DashboardSettingsPage extends PortalAbstractPage {

    public DashboardSettingsPage(WebDriver driver) {
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

    public boolean isBusinessProfileTabShown() {
        return isElementShown(this.getCurrentDriver(), businessProfile, 5);
    }

    public boolean isChatTagsTabShown() {
        return isElementShown(this.getCurrentDriver(), chatTags, 5);
    }

    public boolean isAutoRespondersTabShown() {
        return isElementShown(this.getCurrentDriver(), autoResponders, 5);
    }

    public boolean isPreferencesTabShown() {
        return isElementShown(this.getCurrentDriver(), preferences, 5);
    }

    public boolean isSurveysTabShown() {
        return isElementShown(this.getCurrentDriver(), surveysNavigation, 5);
    }

}
