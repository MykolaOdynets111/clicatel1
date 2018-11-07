package portal_pages;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getAgentDriverInstance());
    }

    @FindBy(css = "div.alert-container")
    private WebElement verificationError;

    public String getVerificatinErrorText(){
        if( isElementShownAgent(verificationError, 2)){
            return verificationError.getText();
        } else{
            return "no verification error";
        }
    }
}
