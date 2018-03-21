package facebook;

import driverManager.DriverFactory;
import driverManager.FacebookPages;

public class FBHomePage {

    public static void openTenantPage(String tenant, String env){
        DriverFactory.getInstance().get(FacebookPages.getURLByTenantAndURL(tenant, env));
    }
}
