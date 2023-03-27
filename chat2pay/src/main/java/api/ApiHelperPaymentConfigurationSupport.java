package api;

import api.MainApi;
import api.Endpoints;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperPaymentConfigurationSupport extends MainApi {

    public static Response getBillingType(String authToken) {
        return getQuery(authToken, Endpoints.BILLING_TYPE);
    }

    public static Response getCardNetwork(String authToken) {
        return getQuery(authToken, Endpoints.CARD_NETWORK);
    }

    public static Response getCountry(String authToken) {
        return getQuery(authToken, Endpoints.COUNTRY);
    }

    public static Response getCurrency(String authToken, String paymentType) {
        return getQuery(authToken, format(Endpoints.CURRENCY_FOR_PAYMENT_INTEGRATION_TYPE, paymentType));
    }

    public static Response getIntegrationType(String authToken) {
        return getQuery(authToken, Endpoints.INTEGRATION_TYPE);
    }

    public static Response getLocale(String authToken, String paymentGatewayId) {
        return getQuery(authToken, format(Endpoints.LOCALE_FOR_PAYMENT_GATEWAY, paymentGatewayId));
    }

    public static Response getPaymentType(String authToken) {
        return getQuery(authToken, Endpoints.PAYMENT_TYPE);
    }

    public static Response getState(String authToken) {
        return getQuery(authToken, Endpoints.STATE);
    }

    public static Response getTransactionType(String authToken) {
        return getQuery(authToken, Endpoints.TRANSACTION_TYPE);
    }
}
