package facebook;

import driverManager.DriverFactory;

public class FBHomePage {

    public static void openTenantPage(String URL){
        DriverFactory.getInstance().get(URL);
    }
}
