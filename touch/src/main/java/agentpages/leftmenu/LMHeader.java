package agentpages.leftmenu;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy (css = ".cl-routed-tabs__tab-list")
public class LMHeader extends AbstractUIElement {

    @FindBy(xpath = "//div[@class='cl-routed-tabs__tab-list']//a[1]")
    private WebElement firstElementInHeader;

    @FindBy(css = "[data-testid='tab-navigation-panel-live']")
    private WebElement liveChats;

    @FindBy(css = "[data-testid='tab-navigation-panel-tickets']")
    private WebElement tickets;

    @FindBy(css = "[data-testid='tab-navigation-panel-closed']")
    private WebElement closed;

    @FindBy(css = "[data-testid='tab-navigation-panel-pending']")
    private WebElement pending;

    public void selectChatsMenu(String option) {
        if (option.equalsIgnoreCase("Live Chats")) {
            clickElem(this.getCurrentDriver(), liveChats, 1, "Live chats menu");
        } else if (option.equalsIgnoreCase("Tickets")) {
            clickElem(this.getCurrentDriver(), tickets, 1, "Tickets menu");
        } else if (option.equalsIgnoreCase("Closed")) {
            clickElem(this.getCurrentDriver(), closed, 1, "Closed menu");
        } else if (option.equalsIgnoreCase("Pending")) {
            clickElem(this.getCurrentDriver(), pending, 1, "Closed menu");
        } else {
            throw new AssertionError("Incorrect menu option was provided");
        }
    }

    public String getFirstElementName(){
        return getTextFromElem(this.getCurrentDriver(),firstElementInHeader, 2,"First Element In Header");
    }
}
