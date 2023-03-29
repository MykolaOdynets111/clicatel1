package datamanager;

import apihelper.ApiHelper;
import drivermanager.ConfigManager;
import io.cucumber.core.exception.CucumberException;
import org.apache.commons.io.FileUtils;
import org.testng.SkipException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.PropertiesReader.getProperty;


public class Tenants {

    private static ThreadLocal<Map<String, String>> respWithAgentInfo = new ThreadLocal<>();
    private static ThreadLocal<String> TENANT_UNDER_TEST_NAME = new ThreadLocal<>();
    private static ThreadLocal<String> TENANT_UNDER_TEST_ORG_NAME =  new ThreadLocal<>();
    private static ThreadLocal<Map<String,String>> TENANT_UNDER_TEST =  new ThreadLocal<>();
    private static ThreadLocal<String> TENANT_ID = new ThreadLocal<>();
    private static ThreadLocal<String> MC2_ID = new ThreadLocal<>();
    private static ThreadLocal<Map<String,String>> TENANT_INFO_MAP = new ThreadLocal<>();

    public static void setTenantUnderTestOrgName(String orgName){
        TENANT_UNDER_TEST_ORG_NAME.set(orgName);
    }

    public static void setTenantUnderTestName(String tenantName){
        TENANT_UNDER_TEST_NAME.set(tenantName);
    }

    public static Map<String,String> getTenantInfoMap() {
        if(TENANT_INFO_MAP.get() == null){
            TENANT_INFO_MAP.set(ApiHelper.getTenantInfoMap(TENANT_UNDER_TEST_ORG_NAME.get()));
        };
        return TENANT_INFO_MAP.get();
    }

    public static String getTenantId() {
        if(TENANT_ID.get() == null){
            TENANT_ID.set(getTenantInfoMap().get("id"));
        };
        return TENANT_ID.get();
    }

    public static String getMC2Id() {
        if(MC2_ID.get() == null){
            MC2_ID.set(getTenantInfoMap().get("mc2AccountId"));
        };
        return MC2_ID.get();
    }

    public static void setTenantInfo(String tenantName, String tenantOrgName){
        Tenants.setTenantUnderTestName(tenantName);
        Tenants.setTenantUnderTestOrgName(tenantOrgName);
        TENANT_UNDER_TEST.get().put(tenantOrgName, tenantName);
    }

    public static String getTenantUnderTestName(){
        return TENANT_UNDER_TEST_NAME.get();
    }

    public static String getTenantUnderTestOrgName() {
        return TENANT_UNDER_TEST_ORG_NAME.get();
    }

    public static String getLastUserSessionStatus(String userID){
        return ApiHelper.getLastUserSession(userID, getTenantUnderTestName()).getState();
    }

    public static Map<String, String> getPrimaryAgentInfoForTenant(String tenantOrgName){
        if (respWithAgentInfo.get()==null){
            respWithAgentInfo.set(ApiHelper.getAgentInfo(tenantOrgName, "main"));
        }
        return respWithAgentInfo.get();
    }

    public static void setTenantUnderTestNames(String tenantOrgName) {
        TENANT_UNDER_TEST.set(new HashMap<>());
        switch (tenantOrgName) {
            case "General Bank Demo":
                setTenantInfo("generalbank", "General Bank Demo");
                break;
            case "Virgin Money":
                setTenantInfo("virgin-money", "Virgin Money");
                break;
            case "Starter AQA" :
                setTenantInfo("starter-aqa", "Starter AQA");
                break;
            case "Standard AQA" :
                setTenantInfo("standardplan", "Standard AQA");
                break;
            case "Updating AQA" :
                setTenantInfo("updatingplan", "Updating AQA");
                if(ConfigManager.getEnv().equals("qa")) setTenantInfo("updatingaccount", "Updating AQA");
                break;
            case "Automation":
                setTenantInfo("agentmode", "Automation");
                break;
            case "Automation Bot":
                setTenantInfo("automationbot", "Automation Bot");
                break;
            case "Automation Common":
                setTenantInfo("aqacomon", "Automation Common");
                break;
            case "Standard Billing":
                setTenantInfo("standardbilling", "Standard Billing");
                break;
            case "Attachments":
                setTenantInfo("attachments", "Attachments");
                break;
            case "Performance":
                setTenantInfo("performance", "Performance");
                break;
            case "WhatsAppFullFlowTenant":
                String agentName = getProperty(getProperty("environment") + ".WhatsAppAgentName");
                String tenantName = getProperty(getProperty("environment") + ".WhatsAppTenantName");
                setTenantInfo(tenantName, agentName);
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
        TENANT_ID.remove();
        MC2_ID.remove();
        TENANT_INFO_MAP.remove();
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
