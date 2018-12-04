package portal_pages;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class PortalFBIntegrationPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[contains(text(), 'Delink account')]")
    private WebElement delinkAccountButton;

    public void delinkFBAccount(){
        waitForElementToBeVisibleAgent(delinkAccountButton, 5);
        delinkAccountButton.click();
        delinkAccountButton.click();

    }

}
