package dataprovider;

import api_helper.ApiHelper;
import dataprovider.jackson_schemas.tenant_address.TenantAddress;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class Tenants {

    private static Response respWithAgentInfo = null;
    private static String TENANT_UNDER_TEST = "";
    private static String TENANT_UNDER_TEST_ORG_NAME = "";

    public static void setTenantUnderTestOrgName(String orgName){
        TENANT_UNDER_TEST_ORG_NAME=orgName;
    }

    public static void setTenantUnderTest(String tenantName){
        TENANT_UNDER_TEST=tenantName;
    }

    public static String getTenantUnderTest(){
        return TENANT_UNDER_TEST;
    }

    public static String getTenantInfo(String tenantORGName, String info) {
        return ApiHelper.getTenantInfoMap(info).get(tenantORGName.toLowerCase().trim());
    }

    public static String getTenantUnderTestOrgName() {
        return TENANT_UNDER_TEST_ORG_NAME;
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
        return ApiHelper.getLastUserSession(userID, getTenantUnderTest()).getState();
    }

    public static Response getPrimarAgentInfoForTenant(String tenantOrgName){
        if (respWithAgentInfo==null){
            ApiHelper.getAgentInfo(tenantOrgName);
        }
            return respWithAgentInfo;
    }
}
