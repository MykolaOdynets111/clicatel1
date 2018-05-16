package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.header")
public class Header extends AbstractUIElement {

    @FindBy(css = "button#top-menu-dropdown")
    private WebElement topMenuDropdown;

    @FindBy(xpath = ".//a[text()='Log out']")
    private WebElement logOutButton;

    public Header logOut() {
        waitForElementToBeVisible(topMenuDropdown);
        click(topMenuDropdown);
        waitForElementToBeVisible(logOutButton, 6);
        logOutButton.click();
        return this;
    }


}
