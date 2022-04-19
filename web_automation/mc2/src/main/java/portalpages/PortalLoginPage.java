package portalpages;

import mc2api.endpoints.EndpointsPlatform;
import org.openqa.selenium.*;

public class PortalLoginPage extends PortalAbstractPage {

    // == Constructors == //

    public PortalLoginPage() {
        super();
    }
    public PortalLoginPage(String agent) {
        super(agent);
    }
    public PortalLoginPage(WebDriver driver) {
        super(driver);
    }


    public static PortalLoginPage openPortalLoginPage(WebDriver driver) {
        driver.get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage(driver);
    }

}
