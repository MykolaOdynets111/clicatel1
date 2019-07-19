package datamanager;

import apihelper.ApiHelper;
import cucumber.runtime.CucumberException;
import datamanager.jacksonschemas.tenantaddress.TenantAddress;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.SkipException;

import java.io.*;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tenants {

    private static ThreadLocal<Response> respWithAgentInfo = null;
    private static ThreadLocal<String> TENANT_UNDER_TEST_NAME = new ThreadLocal<>();
    private static ThreadLocal<String> TENANT_UNDER_TEST_ORG_NAME =  new ThreadLocal<>();
    private static ThreadLocal<Map<String,String>> TENANT_UNDER_TEST =  new ThreadLocal<>();

    public static void setTenantUnderTestOrgName(String orgName){
        TENANT_UNDER_TEST_ORG_NAME.set(orgName);
    }

    public static void setTenantUnderTestName(String tenantName){
        TENANT_UNDER_TEST_NAME.set(tenantName);
    }

    public static String getTenantUnderTestName(){
        return TENANT_UNDER_TEST_NAME.get();
    }

    public static String getTenantInfo(String tenantORGName, String info) {
        return ApiHelper.getAllTenantsInfoMap(info).get(tenantORGName.toLowerCase().trim());
    }

    public static String getTenantUnderTestOrgName() {
        return TENANT_UNDER_TEST_ORG_NAME.get();
    }

    public static String getTenantBranchLocationAddress(String tenantName){
        List<TenantAddress> addreses = ApiHelper.getTenantAddressInfo(tenantName);
        String branchLocation  ="Loaction address: " + addreses.get(0).getFirstAddressLine() + ", " + addreses.get(0).getSecondAddressLine() + ", " + addreses.get(0).getPostalCode()+ ", ";
        if((addreses.get(0).getPhones()).size()>0){
            branchLocation = branchLocation +  addreses.get(0).getPhones().get(0);
        }
        return branchLocation;
    }

    public static String getLastUserSessionStatus(String userID){
        return ApiHelper.getLastUserSession(userID, getTenantUnderTestName()).getState();
    }

    public static Response getPrimaryAgentInfoForTenant(String tenantOrgName){
        if (respWithAgentInfo.get()==null){
            respWithAgentInfo.set(ApiHelper.getAgentInfo(tenantOrgName, "main"));
        }
            return respWithAgentInfo.get();
    }

    public static ZoneId getTenantZoneId(String tenantOrgName){
        String tenantTimeZone = ApiHelper.getInternalTenantConfig(ApiHelper.getTenantInfoMap(tenantOrgName).get("id"), "timezone");
        String zoneOffset = tenantTimeZone.split(":")[0].replace("GMT", "");
        return ZoneId.of(zoneOffset);
    }

    public static void setTenantUnderTestNames(String tenantOrgName) {
        TENANT_UNDER_TEST.set(new HashMap<>());
        switch (tenantOrgName) {
            case "General Bank Demo":
                Tenants.setTenantUnderTestName("generalbank");
                Tenants.setTenantUnderTestOrgName("General Bank Demo");
                TENANT_UNDER_TEST.get().put("General Bank Demo", "generalbank");
                break;
            case "Virgin Money":
                Tenants.setTenantUnderTestName("virgin-money");
                Tenants.setTenantUnderTestOrgName("Virgin Money");
                TENANT_UNDER_TEST.get().put("Virgin Money", "virgin-money");
                break;
            case "Starter AQA" :
                Tenants.setTenantUnderTestName("starter-aqa");
                Tenants.setTenantUnderTestOrgName("Starter AQA");
                TENANT_UNDER_TEST.get().put("Starter AQA", "starter-aqa");
                break;
            case "Standard AQA" :
                Tenants.setTenantUnderTestName("standardplan");
                Tenants.setTenantUnderTestOrgName("Standard AQA");
                TENANT_UNDER_TEST.get().put("Standard AQA", "standardplan");
                break;
            case "Updating AQA" :
                Tenants.setTenantUnderTestName("updatingplan");
                Tenants.setTenantUnderTestOrgName("Updating AQA");
                if(ConfigManager.getEnv().equals("qa")) Tenants.setTenantUnderTestName("updatingaccount");
                TENANT_UNDER_TEST.get().put("Updating AQA", "updatingplan");
                break;
            case "Automation":
                Tenants.setTenantUnderTestName("agentmode");
                Tenants.setTenantUnderTestOrgName("Automation");
                TENANT_UNDER_TEST.get().put("Automation", "agentmode");
                break;
            case "Automation Bot":
                Tenants.setTenantUnderTestName("automationbot");
                Tenants.setTenantUnderTestOrgName("Automation Bot");
                TENANT_UNDER_TEST.get().put("Automation Bot", "agentmode");
                break;
            case "Automation Common":
                Tenants.setTenantUnderTestName("aqacomon");
                Tenants.setTenantUnderTestOrgName("Automation Common");
                TENANT_UNDER_TEST.get().put("Automation Common", "aqacomon");
                break;
            case "Standard Billing":
                Tenants.setTenantUnderTestName("standardbilling");
                Tenants.setTenantUnderTestOrgName("Standard Billing");
                TENANT_UNDER_TEST.get().put("Standard Billing", "standardbilling");
                break;
            default:
                Tenants.setTenantUnderTestOrgName(tenantOrgName);
                Tenants.setTenantUnderTestName(
                        MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName).getAccountName());
        }
    }

    public static void clearTenantUnderTest(){
        TENANT_UNDER_TEST.remove();
        TENANT_UNDER_TEST_ORG_NAME.remove();
        TENANT_UNDER_TEST_NAME.remove();
    }

    public static String getTenantNameByTenantOrgName(String tenantOrgName){
        return TENANT_UNDER_TEST.get().get(tenantOrgName);
    }

    public static void checkWidgetConnectionStatus(){
        File testng_xml = new File("build/reports/tests/runBaseTests/All tenants Master check/testng-failed.xml");
        if(ConfigManager.getEnv().equalsIgnoreCase("integration")){
            testng_xml = new File("build/reports/tests/runBaseTests/General Bank Master check/testng-failed.xml");
        }
        if(testng_xml.exists()){
                try {
                    if( FileUtils.readFileToString(testng_xml, "UTF-8").contains(getTenantUnderTestOrgName())) {
                        throw new CucumberException(new SkipException("Widget is not connecting for '" + getTenantUnderTestOrgName() + "' tenant"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
