package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import io.restassured.response.ResponseBody;

import static datamanager.enums.Days.getDaysValue;
import static java.util.Collections.singletonList;

public class ApiHelperSupportHours extends ApiHelper {

    public static ResponseBody setSupportDaysAndHours(String tenantOrgName, String day,
                                                      String startTime, String endTime) {
        GeneralSupportHoursItem body;
        if (day.equalsIgnoreCase("all week")) {
            body = setSupportDaysAndHours(new GeneralSupportHoursItem(getDaysValue()));
        } else {
            body = setSupportDaysAndHours(new GeneralSupportHoursItem(startTime, endTime, singletonList(day)));
        }
        return postTouchQuery(tenantOrgName, Endpoints.SUPPORT_HOURS, body,"main");
    }

    public static GeneralSupportHoursItem setSupportDaysAndHours(GeneralSupportHoursItem supportHoursItem) {

        String tenant = Tenants.getTenantUnderTestOrgName();
        return postTouchQuery(tenant, Endpoints.SUPPORT_HOURS, supportHoursItem,"main")
                .as(GeneralSupportHoursItem.class);
    }

    public static GeneralSupportHoursItem getSupportDaysAndHoursForMainAgent(String tenantOrgName) {
        return getTouchQuery(tenantOrgName, Endpoints.SUPPORT_HOURS)
                .as(GeneralSupportHoursItem.class);
    }
}
