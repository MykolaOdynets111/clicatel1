package dataprovider;

import api_helper.ApiHelper;

import java.util.Map;

public class TenantIDs {

    private static Map<String, String> tenantIds = null;

    public static String getTenantIdFor(String tenantName) {
        if (tenantIds == null) {
            tenantIds = ApiHelper.getTenantIdMap();
        }
        return tenantIds.get(tenantName.toLowerCase());
    }
}
