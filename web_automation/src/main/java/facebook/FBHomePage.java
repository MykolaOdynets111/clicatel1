package facebook;

import drivermanager.DriverFactory;

public class FBHomePage {

    public static void openTenantPage(String URL){
        DriverFactory.getTouchDriverInstance().get(URL);
    }
}
