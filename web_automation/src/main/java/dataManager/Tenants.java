package dataManager;

import api_helper.ApiHelper;
import dataManager.jackson_schemas.tenant_address.TenantAddress;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tenants {

    private static Response respWithAgentInfo = null;
    private static ThreadLocal<String> TENANT_UNDER_TEST_NAME = new ThreadLocal<>();
    private static ThreadLocal<String> TENANT_UNDER_TEST_ORG_NAME =  new ThreadLocal<>();
    private static ThreadLocal<Map<String,String>> TENANT_UNDER_TEST =  new ThreadLocal<>();

    private static void setTenantUnderTestOrgName(String orgName){
        TENANT_UNDER_TEST_ORG_NAME.set(orgName);
    }

    private static void setTenantUnderTestName(String tenantName){
        TENANT_UNDER_TEST_NAME.set(tenantName);
    }

    public static String getTenantUnderTestName(){
        return TENANT_UNDER_TEST_NAME.get();
    }

    public static String getTenantInfo(String tenantORGName, String info) {
        return ApiHelper.getTenantInfoMap(info).get(tenantORGName.toLowerCase().trim());
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
        if (respWithAgentInfo==null){
            respWithAgentInfo = ApiHelper.getAgentInfo(tenantOrgName);
        }
            return respWithAgentInfo;
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
                TENANT_UNDER_TEST.get().put("Updating AQA", "updatingplan");
                break;
            case "Automation":
                Tenants.setTenantUnderTestName("agentmode");
                Tenants.setTenantUnderTestOrgName("Automation");
                TENANT_UNDER_TEST.get().put("Automation", "agentmode");
                break;
        }
    }

    public static String getTenantNameByTenantOrgName(String tenantOrgName){
        return TENANT_UNDER_TEST.get().get(tenantOrgName);
    }
}
