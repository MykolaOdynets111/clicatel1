package agentpages.dashboard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

public class DashboardSettingsPage extends PortalAbstractPage {

    public DashboardSettingsPage(WebDriver driver) {
        super(driver);
    }

    @FindAll({
            @FindBy(css = "[selenium-id='tab-navigation-panel-business-profile']"), //toDo old locator
            @FindBy(css = "[data-testid='tab-navigation-panel-business-profile']")
    })
    private WebElement businessProfile;

    @FindAll({
            @FindBy(css = "[selenium-id='tab-navigation-panel-chat-tags']"), //toDo old locator
            @FindBy(css = "[data-testid='tab-navigation-panel-chat-tags']")})
    private WebElement chatTags;

    @FindAll({
            @FindBy(css = "[selenium-id='tab-navigation-panel-auto-responders']"), //toDo old locator
            @FindBy(css = "[data-testid='tab-navigation-panel-auto-responders']")})
    private WebElement autoResponders;

    @FindAll({
            @FindBy(css = "[selenium-id='tab-navigation-panel-preferences']"), //toDo old locator
            @FindBy(css = "[data-testid='tab-navigation-panel-preferences']")
    })
    private WebElement preferences;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-surveys']"),
            @FindBy(css = "[selenium-id='tab-navigation-panel-surveys']")
    })
    private WebElement surveysNavigation;

    @FindBy(xpath = "//div[@data-testid='spinner']")
    private WebElement surveySpinner;

    public void openSettingsPage(String settingsName) {
        switch (settingsName) {
            case "Business Profile":
                clickElem(this.getCurrentDriver(), businessProfile, 5, "Business Profile");
                break;
            case "Chat Tags":
                clickElem(this.getCurrentDriver(), chatTags, 5, "Chat Tags");
                break;
            case "Auto Responders":
                clickElem(this.getCurrentDriver(), autoResponders, 5, "Auto Responders");
                break;
            case "Preferences":
                clickElem(this.getCurrentDriver(), preferences, 5, "Preferences");
                break;
            case "Surveys":
                clickElem(this.getCurrentDriver(), surveysNavigation, 6, "Surveys");
        }
        this.getCurrentDriver().navigate().refresh();
        waitForAppearAndDisappear(this.getCurrentDriver(), surveySpinner, 3, 4);
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
