package api.clients;

import api.models.request.MerchantBillingInfoBody;
import api.models.request.WidgetBody;
import api.models.response.integration.IntegrationResponse;
import api.models.response.widget.Widget;
import api.models.response.widget.WidgetsContent;
import api.models.response.widgetconfigurations.ApiKeysResponse;
import io.restassured.response.Response;

import java.util.List;
import java.util.Objects;


public class ApiHelperMerchantBillingInfo extends ApiHelperChat2Pay {

    public static Response updateMerchantBillingInfoCreatedWidget(String widgetId, MerchantBillingInfoBody widgetBody) {
        return postQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), widgetBody, token.get());
    }

    public static Response getMerchantBillingInfoCreatedWidget(String widgetId) {
        return getQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), token.get());
    }

}
