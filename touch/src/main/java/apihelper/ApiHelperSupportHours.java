package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import io.restassured.response.Response;

import static datamanager.enums.Days.getDaysValue;
import static java.util.Collections.singletonList;

public class ApiHelperSupportHours extends ApiHelper {

    public static Response setSupportDaysAndHours(String tenantOrgName, String day,
                                                  String startTime, String endTime) {
        GeneralSupportHoursItem body;
        if (day.equalsIgnoreCase("all week")) {
            body = setSupportDaysAndHours(new GeneralSupportHoursItem(getDaysValue()));
        } else {
            body = setSupportDaysAndHours(new GeneralSupportHoursItem(startTime, endTime, singletonList(day)));
        }
        return postQuery(tenantOrgName, Endpoints.SUPPORT_HOURS, body);
    }

    public static GeneralSupportHoursItem setSupportDaysAndHours(GeneralSupportHoursItem supportHoursItem) {

        String tenant = Tenants.getTenantUnderTestOrgName();
        return getPostQueryFor(tenant, Endpoints.SUPPORT_HOURS, supportHoursItem)
                .as(GeneralSupportHoursItem.class);
    }

    public static GeneralSupportHoursItem getSupportDaysAndHoursForMainAgent(String tenantOrgName) {
        return getQueryFor(tenantOrgName, Endpoints.SUPPORT_HOURS)
                .as(GeneralSupportHoursItem.class);
    }
}
