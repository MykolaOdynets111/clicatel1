package api.clients;

import api.MainApi;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperPaymentConfigurationSupport extends MainApi {

    public static Response getBillingType(String authToken) {
        return getQuery(Endpoints.BILLING_TYPE, authToken);
    }

    public static Response getCardNetwork(String authToken) {
        return getQuery(Endpoints.CARD_NETWORK, authToken);
    }

    public static Response getCountry(String authToken) {
        return getQuery(Endpoints.COUNTRY, authToken);
    }

    public static Response getCurrency(String authToken, String paymentType) {
        return getQuery(format(Endpoints.CURRENCY_FOR_PAYMENT_INTEGRATION_TYPE, paymentType), authToken);
    }

    public static Response getIntegrationType(String authToken) {
        return getQuery(Endpoints.INTEGRATION_TYPE, authToken);
    }

    public static Response getLocale(String authToken, String paymentGatewayId) {
        return getQuery(format(Endpoints.LOCALE_FOR_PAYMENT_GATEWAY, paymentGatewayId), authToken);
    }
}
