package twitter;

import driverManager.DriverFactory;

public class TwitterHomePage {

    public static void openTenantPage(String URL){
        DriverFactory.getInstance().get(URL);
    }
}
