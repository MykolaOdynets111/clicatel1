package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum FacebookPages {

    QA_GEN_BANK("General-Bank-QA-135125267153030/", 135125267153030l, "General Bank Demo", "qa"),
    INTEGRATION_GEN_BANK("General-Bank-Integration-307344189832958", 307344189832958l, "General Bank Demo", "integration"),
    DEMO_GEN_BANK("General-Bank-Demo-178022086085592/", 178022086085592l, "General Bank Demo", "demo"),
    DEV_GEN_BANK("General-Bank-Dev-662912180789339/", 662912180789339l, "General Bank Demo", "dev"),
    TESTING_GEN_BANK("General-Bank-Testing-662287630896233/", 662287630896233l, "General Bank Demo", "testing"),//egor
    ;

    String pageLink;
    long id;
    String tenant;
    String env;

    FacebookPages(String page, long id, String tenant, String env) {
        this.id = id;
        this.pageLink = page;
        this.tenant = tenant;
        this.env = env;
    }

    public String getFBPageLink() {
        return this.pageLink;
    }

    public String getFBPageTenant() {
        return this.tenant;
    }

    public String getFBPageEnv() {
        return this.env;
    }

    public long getFBPageId() {
        return this.id;
    }

    public static FacebookPages getFBPageFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        FacebookPages[] pagesArray = FacebookPages.values();
        List<FacebookPages> pagesList = Arrays.asList(pagesArray);
        return pagesList.stream()
                .filter(e -> e.getFBPageEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getFBPageTenant().equalsIgnoreCase(tenantOrgName))
                .findFirst().get();
    }

}
