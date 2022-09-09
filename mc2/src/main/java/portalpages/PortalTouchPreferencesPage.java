package portalpages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import portaluielem.*;

@Data
public class PortalTouchPreferencesPage extends PortalAbstractPage {

    private AutoRespondersWindow autoRespondersWindow;

    private BusinessProfileWindow businessProfileWindow;

    private PreferencesWindow preferencesWindow;

    private EditCompanyLogoWindow editCompanyLogoWindow;

    private ChatTagsWindow chatTagsWindow;

    // == Constructors == //

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

    public EditCompanyLogoWindow getEditCompanyLogoWindow() {
        editCompanyLogoWindow.setCurrentDriver(this.getCurrentDriver());
        return editCompanyLogoWindow;
    }

    public ChatTagsWindow getChatTagsWindow() {
        chatTagsWindow.setCurrentDriver(this.getCurrentDriver());
        return chatTagsWindow;
    }
}
