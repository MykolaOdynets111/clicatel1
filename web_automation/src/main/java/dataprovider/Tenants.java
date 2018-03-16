package dataprovider;

import api_helper.ApiHelper;

import java.util.Map;

public class Tenants {

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
}
