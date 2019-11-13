package socialaccounts;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum FacebookPages {

    /** All previous used pages for GB were replaced with new TEST PAGES */
//    QA_GEN_BANK("General-Bank-QA-135125267153030/", 135125267153030l, "General Bank Demo", "qa"),
//    INTEGRATION_GEN_BANK("General-Bank-Integration-307344189832958", 307344189832958l, "General Bank Demo", "integration"),
//    DEMO_GEN_BANK("General-Bank-Demo-178022086085592/", 178022086085592l, "General Bank Demo", "demo"),
//    DEV_GEN_BANK("General-Bank-Dev-662912180789339/", 662912180789339l, "General Bank Demo", "dev"),
//    // pass for General-Bank-Testing "oneclicktouch@gmail.com"  /  "itsallaboutlove"
//    TESTING_GEN_BANK("General-Bank-Testing-662287630896233/", 662287630896233l, "General Bank Demo", "testing"),//egor

    /** ========== Testing pages created by TEST accounts. You can't text to them from the real live Facebook account ==== **/

    DEV_GB_TEST_PAGE("General-Bank-Dev-379139559408480/", 379139559408480l, "General Bank Demo", "dev"),
    INTEGRATION_GB_TEST_PAGE("General-Bank-Integration-1226584247523300/", 1226584247523300l, "General Bank Demo", "integration"),
    DEMO_GB_TEST_PAGE("General-Bank-Demo-639986503163431/", 639986503163431l, "General Bank Demo", "demo"),
    QA_GB_TEST_PAGE("General-Bank-QA-353413438906678/", 353413438906678l, "General Bank Demo", "qa"),
    TESTING_GB_TEST_PAGE("General-Bank-Testing-2636917462985971/", 2636917462985971l, "General Bank Demo", "testing"),

    DEV_AUTOMATION_TEST_PAGE("Automation-Dev-2600073340024307/", 2600073340024307l, "Automation", "dev"),
    INTEGRATION_AUTOMATION_TEST_PAGE("Automation-Integration-485646348878443/", 485646348878443l, "Automation", "integration"),
    DEMO_AUTOMATION_TEST_PAGE("Automation-Demo-2424875384267111/", 2424875384267111l, "Automation", "demo"),
    QA_AUTOMATION_TEST_PAGE("Automation-2564618190237615/", 2564618190237615l, "Automation", "qa"),
    TESTING_AUTOMATION_TEST_PAGE("Automation-Testing-470900040355744/", 470900040355744l, "Automation", "testing"),

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
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot get facebook page for '" + tenantOrgName + "' on " + ConfigManager.getEnv() + " env"));
    }

}
