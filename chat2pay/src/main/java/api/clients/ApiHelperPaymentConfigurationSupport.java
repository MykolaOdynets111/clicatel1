package api.clients;

import api.MainApi;
import io.restassured.response.Response;


public class ApiHelperPaymentConfigurationSupport extends MainApi {

    public static Response getBillingType(String authToken) {
        return getQuery(Endpoints.BILLING_TYPE, authToken);
    }

    public static Response getCardNetwork(String authToken) {
        return getQuery(Endpoints.CARD_NETWORK, authToken);
    }
}
