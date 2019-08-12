package socialaccounts;

import java.util.Arrays;
import java.util.List;

public enum TwitterPages {

    GEN_BANK_TESTING("General Bank Demo", "testing", "https://twitter.com/Olegtest7"),
    GEN_BANK_QA("General Bank Demo", "qa", "https://twitter.com/TestBotPerf"),
    GEN_BANK_DEMO("General Bank Demo", "demo", "https://twitter.com/generalbankdemo")

    ;

    String tenantName;
    String env;
    String url;

    TwitterPages(String tenantName, String env, String url) {
        this.tenantName = tenantName;
        this.env = env;
        this.url = url;
    }

    public static String getURLByTenantAndURL(String tenantName, String env){
        TwitterPages[] pages = TwitterPages.values();
        List<TwitterPages> pagesList = Arrays.asList(pages);
        return pagesList.stream().filter(e -> e.getPageTenantName().equals(tenantName) &&
                e.getPageEnv().equals(env))
                .findFirst().get()
                .getPageURL();
    }

    public String getPageTenantName() {
        return this.tenantName;
    }

    public String getPageEnv() {
        return this.env;
    }

    public String getPageURL() {
        return this.url;
    }
}
