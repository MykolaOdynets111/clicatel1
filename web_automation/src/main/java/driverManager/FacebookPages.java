package driverManager;

import java.util.Arrays;
import java.util.List;

public enum FacebookPages {

    GEN_BANK_QA("General Bank", "qa", "https://www.facebook.com/General-Bank-QA-135125267153030/");

    String tenantName;
    String env;
    String url;

    FacebookPages(String tenantName, String env, String url) {
        this.tenantName = tenantName;
        this.env = env;
        this.url = url;
    }

    public static String getURLByTenantAndURL(String tenantName, String env){
        FacebookPages[] pages = FacebookPages.values();
        List<FacebookPages> pagesList = Arrays.asList(pages);
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
