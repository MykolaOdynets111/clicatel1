package apihelper;

import datamanager.jacksonschemas.customerhistory.CustomerSatisfactionReport;
import datamanager.jacksonschemas.customerhistory.PastSentimentReport;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelperCustomerHistory {
    private static final Map<String, String> periods = new HashMap<String, String>() {{
        put("Past day", "DAY");
        put("Past week", "ONE_WEEK");
        put("Past 2 week", "TWO_WEEKS");
        put("Past 3 week", "THREE_WEEKS");
        put("Past 4 week", "FOUR_WEEKS");
    }};

    private static final Map<String, String> channelTypes = new HashMap<String, String>() {{
        put("Web Chat", "webchat");
        put("Facebook", "facebook");
        put("Twitter", "twitter");
        put("WhatsApp", "whatsapp");
        put("Apple Business Chat", "abc");
        put("SMS", "sms");
    }};

    public static List<PastSentimentReport> getPastSentimentReport(String tenantOrgName, String period, String channelType) {
        List<PastSentimentReport> pastSentimentReports = new ArrayList<>();
        Response response = CustomerHistoryReportAPI.createPastSentimentReport(tenantOrgName,
                periods.get(period),
                channelTypes.get(channelType));
        Assert.assertEquals(response.statusCode(), 200, "Wrong status code for past sentimental report");
        try {
            pastSentimentReports = response.getBody().jsonPath().getList("", PastSentimentReport.class);
        } catch (ClassCastException | JsonPathException e) {
            Assert.fail("Creating past sentimental report was not successful \n" +
                    "Resp body: " + response.getBody().asString());
        }
        return pastSentimentReports;
    }

    public static List<CustomerSatisfactionReport> getCustomerSatisfactionReport(String tenantOrgName, String period, String channelType) {
        List<CustomerSatisfactionReport> customerSatisfactionReports = new ArrayList<>();
        Response response = CustomerHistoryReportAPI.createAverageCustomerSatisfactionReport(tenantOrgName,
                periods.get(period),
                channelTypes.get(channelType));
        Assert.assertEquals(response.statusCode(), 200, "Wrong status code for past sentimental report");
        try {
            customerSatisfactionReports = response.getBody().jsonPath().getList("averageCustomerSatisfactionReportViewDtoList", CustomerSatisfactionReport.class);
        } catch (ClassCastException | JsonPathException e) {
            Assert.fail("Creating Сustomer Satisfaction Report was not successful \n" +
                    "Resp body: " + response.getBody().asString());
        }
        return customerSatisfactionReports;
    }
}
