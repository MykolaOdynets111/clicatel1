package api.clients;

import api.models.request.ApplicationBody;
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

}
