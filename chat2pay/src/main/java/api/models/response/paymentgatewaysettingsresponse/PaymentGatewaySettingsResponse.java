package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

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

@Getter
public class PaymentGatewaySettingsResponse {
    @JsonProperty("paymentGatewaySettingsId")
    public String paymentGatewaySettingsId;

    @JsonProperty("paymentGatewayId")
    public int paymentGatewayId;

    @JsonProperty("paymentIntegrationTypeId")
    public int paymentIntegrationTypeId;

    @JsonProperty("merchantId")
    public String merchantId;

    @JsonProperty("saProfileId")
    public String saProfileId;

    @JsonProperty("saAccessKey")
    public String saAccessKey;

    @JsonProperty("locale")
    public Locale locale;

    @JsonProperty("country")
    public Country country;

    @JsonProperty("shipToCountries")
    public List<Object> shipToCountries = null;

    @JsonProperty("defaultCurrency")
    public DefaultCurrency defaultCurrency;

    @JsonProperty("currencies")
    public List<Currency> currencies = null;

    @JsonProperty("cardNetworks")
    public List<CardNetwork> cardNetworks = null;

    @JsonProperty("paymentTypes")
    public List<PaymentType> paymentTypes = null;

    @JsonProperty("createdTime")
    public String createdTime;

    @JsonProperty("modifiedTime")
    public String modifiedTime;

    @JsonProperty("requestEmail")
    public boolean requestEmail;

    @JsonProperty("requestPhone")
    public boolean requestPhone;

    @JsonProperty("requestShipping")
    public boolean requestShipping;

    @JsonProperty("transactionType")
    public String transactionType;

    @JsonProperty("restApiAccessKey")
    public String restApiAccessKey;

    @JsonProperty("restApiSecretKey")
    public String restApiSecretKey;

    @JsonProperty("logo")
    public Logo logo;

    @JsonProperty("billingType")
    public String billingType;
}
