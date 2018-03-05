package dataprovider;

import api_helper.ApiHelper;

import java.util.Map;

public class Tenants {

    private static String TENANT_UNDER_TEST = "";

    private static Map<String, String> tenantIds = null;

    public static String getTenantIdFor(String tenantName) {
        if (tenantIds == null) {
            tenantIds = ApiHelper.getTenantIdMap();
        }
        return tenantIds.get(tenantName.toLowerCase());
    }

    public static void setTenantUnderTest(String tenantName){
        TENANT_UNDER_TEST=tenantName;
    }

    public static String getTenantUnderTest(){
        return TENANT_UNDER_TEST;
    }
}
