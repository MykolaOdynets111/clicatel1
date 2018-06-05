package twitter;

import abstract_classes.AbstractPage;
import driverManager.DriverFactory;
import twitter.uielements.TwitterHeader;
import twitter4j.Twitter;

public class TwitterHomePage extends AbstractPage {

    private TwitterHeader twitterHeader;

    public TwitterHeader getTwitterHeader() {
        return twitterHeader;
    }

    public static void openTenantPage(String URL){
        DriverFactory.getInstance().get(URL);
    }
}
