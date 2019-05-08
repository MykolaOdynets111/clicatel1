package portalpages;

import portalpages.uielements.AboutYourBusinessWindow;
import portalpages.uielements.AutoRespondersWindow;
import portalpages.uielements.ConfigureBrandWindow;


public class PortalTouchPrefencesPage extends PortalAbstractPage {

    private AutoRespondersWindow autoRespondersWindow;

    private ConfigureBrandWindow configureBrandWindow;

    private AboutYourBusinessWindow aboutYourBusinessWindow;

    public AutoRespondersWindow getAutoRespondersWindow() {
        return autoRespondersWindow;
    }

    public ConfigureBrandWindow getconfigureBrandWindow() {
        return configureBrandWindow;
    }

    public AboutYourBusinessWindow getAboutYourBusinessWindow() {
        return aboutYourBusinessWindow;
    }
}
