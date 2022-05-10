package mc2api.endpoints;

import driverfactory.MC2URLs;

public class WaHsmTemplateApi {
    public static String PLATFORM_WA_HSM_TEMPLATE = MC2URLs.getBasePlatformUrl() + "/wa/templates";

    public static String PLATFORM_WA_HSM_TEMPLATE_GET_BY_ID = MC2URLs.getBasePlatformUrl() + "/wa/templates/%s";

    public static String PLATFORM_WA_HSM_TEMPLATE_APPROVE = MC2URLs.getBasePlatformUrl() + "/admin/wa/templates/%s/approved";

    public static String PLATFORM_WA_HSM_TEMPLATE_DECLINE = MC2URLs.getBasePlatformUrl() + "/admin/wa/templates/%s/declined";

    public static String PLATFORM_ADMIN_WA_HSM_TEMPLATE_UPDATE = MC2URLs.getBasePlatformUrl() + "/admin/wa/templates/%s";
}
