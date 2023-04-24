package api;


import data.models.request.ApplicationBody;
import io.restassured.response.Response;

public class ApiHelperCustomerApplication extends ApiHelperChat2Pay {

    public static Response postCustomerApplication(String widgetId, ApplicationBody applicationBody) {
        return postQuery(token.get(), String.format(Endpoints.CUSTOMER_APPLICATION_ENDPOINT, widgetId), applicationBody);
    }

    public static Response updateCustomerApplication(String widgetId, ApplicationBody applicationBody, String applicationID) {
        String putPath = String.format(Endpoints.CUSTOMER_APPLICATION_ENDPOINT, widgetId) + "/" + applicationID;
        return putQuery(token.get(), putPath, applicationBody);
    }

    public static Response deleteCustomerApplication(String widgetId, String applicationId) {
        return deleteQuery(token.get(), String.format(Endpoints.CUSTOMER_APPLICATION_ENDPOINT, widgetId) + "/" + applicationId);
    }

    public static Response deleteAllCustomerApplication(String widgetId) {
        return deleteQuery(token.get(), String.format(Endpoints.CUSTOMER_APPLICATION_ENDPOINT, widgetId) + "/all");
    }

    public static Response postOrderManagementSystem(String widgetId, ApplicationBody applicationBody) {
        return postQuery(token.get(), String.format(Endpoints.ORDER_MANAGEMENT_SYSTEM_ENDPOINT, widgetId), applicationBody);
    }

    public static Response putOrderManagementSystem(String widgetId, String applicationId, ApplicationBody applicationBody) {
        return putQuery(token.get(), String.format(Endpoints.ORDER_MANAGEMENT_SYSTEM_ENDPOINT, widgetId)
                + "/" + applicationId, applicationBody);
    }
}