package dataManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum FacebookPages {

    QA_GEN_BANK("General-Bank-QA-135125267153030/", "General Bank Demo", "qa")

    ;

    String page;
    String tenant;
    String env;

    FacebookPages(String page, String tenant, String env) {
        this.page = page;
        this.tenant = tenant;
        this.env = env;
    }

    public String getFBPage() {
        return this.page;
    }

    public String getFBPageTenant() {
        return this.tenant;
    }

    public String getFBPageEnv() {
        return this.env;
    }

    public static String getFBPageFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        FacebookPages[] pagesArray = FacebookPages.values();
        List<FacebookPages> pagesList = Arrays.asList(pagesArray);
        return pagesList.stream()
                .filter(e -> e.getFBPageEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getFBPageTenant().equalsIgnoreCase(tenantOrgName))
                .findFirst().get().getFBPage();
    }

}
