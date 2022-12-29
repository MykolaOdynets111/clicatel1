package api.models.response;

import api.models.response.paymentgatewaysettingsresponse.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentGatewaySettingsId",
        "paymentGatewayId",
        "paymentIntegrationTypeId",
        "merchantId",
        "saProfileId",
        "saAccessKey",
        "locale",
        "country",
        "shipToCountries",
        "defaultCurrency",
        "currencies",
        "cardNetworks",
        "paymentTypes",
        "createdTime",
        "modifiedTime",
        "requestEmail",
        "requestPhone",
        "requestShipping",
        "transactionType",
        "restApiAccessKey",
        "restApiSecretKey",
        "logo",
        "billingType"
})

@Data
public class PaymentGatewaySettingsResponse {
    @JsonProperty("paymentGatewaySettingsId")
    private String paymentGatewaySettingsId;

    @JsonProperty("paymentGatewayId")
    private int paymentGatewayId;

    @JsonProperty("paymentIntegrationTypeId")
    private int paymentIntegrationTypeId;

    @JsonProperty("merchantId")
    private String merchantId;

    @JsonProperty("saProfileId")
    private String saProfileId;

    @JsonProperty("saAccessKey")
    private String saAccessKey;

    @JsonProperty("locale")
    private Locale locale;

    @JsonProperty("country")
    private Country country;

    @JsonProperty("shipToCountries")
    private List<Object> shipToCountries = null;

    @JsonProperty("defaultCurrency")
    private DefaultCurrency defaultCurrency;

    @JsonProperty("currencies")
    private List<Currency> currencies = null;

    @JsonProperty("cardNetworks")
    private List<CardNetwork> cardNetworks = null;

    @JsonProperty("paymentTypes")
    private List<PaymentType> paymentTypes = null;

    @JsonProperty("createdTime")
    private String createdTime;

    @JsonProperty("modifiedTime")
    private String modifiedTime;

    @JsonProperty("requestEmail")
    private boolean requestEmail;

    @JsonProperty("requestPhone")
    private boolean requestPhone;

    @JsonProperty("requestShipping")
    private boolean requestShipping;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("restApiAccessKey")
    private String restApiAccessKey;

    @JsonProperty("restApiSecretKey")
    private String restApiSecretKey;

    @JsonProperty("logo")
    private Logo logo;

    @JsonProperty("billingType")
    private String billingType;

}
