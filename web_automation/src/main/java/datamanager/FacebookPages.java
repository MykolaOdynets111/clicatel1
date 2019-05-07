package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum FacebookPages {

    QA_GEN_BANK("General-Bank-QA-135125267153030/", 135125267153030l, "General Bank Demo", "qa"),
    INTEGRATION_GEN_BANK("General-Bank-Integration-307344189832958", 307344189832958l, "General Bank Demo", "for integration testing")

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
