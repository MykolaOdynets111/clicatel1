package portalpages;

import org.openqa.selenium.WebDriver;
import portalpages.uielements.AboutYourBusinessWindow;
import portalpages.uielements.AutoRespondersWindow;
import portalpages.uielements.ChatDeskWindow;
import portalpages.uielements.ConfigureBrandWindow;


public class PortalTouchPreferencesPage extends PortalAbstractPage {

    private AutoRespondersWindow autoRespondersWindow;

    private ConfigureBrandWindow configureBrandWindow;

    private AboutYourBusinessWindow aboutYourBusinessWindow;

    private ChatDeskWindow chatDeskWindow;

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

    public ChatDeskWindow getChatDeskWindow() {
        chatDeskWindow.setCurrentDriver(this.getCurrentDriver());
        return chatDeskWindow;
    }

    public ConfigureBrandWindow getConfigureBrandWindow() {
        configureBrandWindow.setCurrentDriver(this.getCurrentDriver());
        return configureBrandWindow;
    }

    public AboutYourBusinessWindow getAboutYourBusinessWindow() {
        aboutYourBusinessWindow.setCurrentDriver(this.getCurrentDriver());
        return aboutYourBusinessWindow;
    }
}
