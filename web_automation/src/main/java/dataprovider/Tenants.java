package dataprovider;

import api_helper.ApiHelper;

import java.util.Map;

public class Tenants {

    private static String TENANT_UNDER_TEST = "";


    public static void setTenantUnderTest(String tenantName){
        TENANT_UNDER_TEST=tenantName;
    }

    public static String getTenantUnderTest(){
        return TENANT_UNDER_TEST;
    }

    public static String getTenantInfo(String tenantName, String info) {
        return ApiHelper.getTenantInfoMap(info).get(tenantName.toLowerCase().trim());
    }
}
