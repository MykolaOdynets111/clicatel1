package portalpages;

import org.openqa.selenium.WebDriver;
import portaluielem.*;


public class PortalTouchPreferencesPage extends PortalAbstractPage {

    private AutoRespondersWindow autoRespondersWindow;

    private BusinessProfileWindow businessProfileWindow;

    private AboutYourBusinessWindow aboutYourBusinessWindow;

    private PreferencesWindow preferencesWindow;

    private EditCompanyLogoWindow editCompanyLogoWindow;

    // == Constructors == //

    public PortalTouchPreferencesPage() {
        super();
    }
    public PortalTouchPreferencesPage(String agent) {
        super(agent);
    }
    public PortalTouchPreferencesPage(WebDriver driver) {
        super(driver);
    }

    public AutoRespondersWindow getAutoRespondersWindow() {
        autoRespondersWindow.setCurrentDriver(this.getCurrentDriver());
        return autoRespondersWindow;
    }

    public PreferencesWindow getPreferencesWindow() {
        preferencesWindow.setCurrentDriver(this.getCurrentDriver());
        return preferencesWindow;
    }

    public BusinessProfileWindow getBusinessProfileWindow() {
        businessProfileWindow.setCurrentDriver(this.getCurrentDriver());
        return businessProfileWindow;
    }

    public AboutYourBusinessWindow getAboutYourBusinessWindow() {
        aboutYourBusinessWindow.setCurrentDriver(this.getCurrentDriver());
        return aboutYourBusinessWindow;
    }

    public EditCompanyLogoWindow getEditCompanyLogoWindow() {
        editCompanyLogoWindow.setCurrentDriver(this.getCurrentDriver());
        return editCompanyLogoWindow;
    }
}
