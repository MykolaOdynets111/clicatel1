package facebook;

import driverManager.DriverFactory;
import driverManager.FacebookPages;

public class FBHomePage {

    public static void openTenantPage(String URL){
        DriverFactory.getInstance().get(URL);
    }
}
