package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portaluielem.*;


public class PortalMainPage extends PortalAbstractPage {

    // == Constructors == //

    public PortalMainPage(WebDriver driver) {
        super(driver);
//        closeUpdatePolicyPopup();
    }
    public PortalMainPage(String agent) {
        super(agent);
    }
    public PortalMainPage() {
        super();
    }

}
