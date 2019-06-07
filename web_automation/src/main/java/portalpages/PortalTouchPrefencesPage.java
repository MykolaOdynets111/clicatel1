package portalpages;

import portalpages.uielements.AboutYourBusinessWindow;
import portalpages.uielements.AutoRespondersWindow;
import portalpages.uielements.ChatDeskWindow;
import portalpages.uielements.ConfigureBrandWindow;


public class PortalTouchPrefencesPage extends PortalAbstractPage {

    private AutoRespondersWindow autoRespondersWindow;

    private ConfigureBrandWindow configureBrandWindow;

    private AboutYourBusinessWindow aboutYourBusinessWindow;

    private ChatDeskWindow chatDeskWindow;

    public AutoRespondersWindow getAutoRespondersWindow() {
        return autoRespondersWindow;
    }

    public ChatDeskWindow getChatDeskWindow() {
        return chatDeskWindow;
    }

    public ConfigureBrandWindow getConfigureBrandWindow() {
        return configureBrandWindow;
    }

    public AboutYourBusinessWindow getAboutYourBusinessWindow() {
        return aboutYourBusinessWindow;
    }
}
