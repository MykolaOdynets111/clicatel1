package apihelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.PortalAuthToken;


public class CustomerHistoryReportAPI {

    public static Response createPastSentimentReport(String tenantOrgName, String period, String channelType) {
        return RestAssured.given()
                .cookie("clickatell-auth", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .contentType(ContentType.JSON)
                .body(getFilterModel(period, channelType))
                .post(Endpoints.PAST_SENTIMENT_REPORT);
    }

    public static Response createAverageCustomerSatisfactionReport(String tenantOrgName, String period, String channelType) {
        return RestAssured.given()
                .cookie("clickatell-auth", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .contentType(ContentType.JSON)
                .body(getFilterModel(period, channelType))
                .post(Endpoints.AVERAGE_CUSTOMER_SATISFACTION_REPORT);
    }

    private static String getFilterModel(String period, String channelType) {
        return String.format("{\"period\": \"%s\", \"channelType\": \"%s\"}", period, channelType);
    }
}
