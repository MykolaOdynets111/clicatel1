package driverManager;

import java.util.Arrays;
import java.util.List;

public enum TwitterURLs {

    GEN_BANK_QA("General Bank Demo", "testing", "https://twitter.com/Olegtest7");

    String tenantName;
    String env;
    String url;

    TwitterURLs(String tenantName, String env, String url) {
        this.tenantName = tenantName;
        this.env = env;
        this.url = url;
    }

    public static String getURLByTenantAndURL(String tenantName, String env){
        TwitterURLs[] pages = TwitterURLs.values();
        List<TwitterURLs> pagesList = Arrays.asList(pages);
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