package api.models.request.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "merchantId",
        "restApiAccessKey",
        "restApiSecretKey",
        "saProfileId",
        "saAccessKey",
        "intermediatePaymentPageRequestUrl",
        "localeId",
        "countryId",
        "shipToCountriesIds",
        "defaultCurrencyId",
        "currenciesIds",
        "cardNetworksIds",
        "paymentTypesIds",
        "requestEmail",
        "requestPhone",
        "requestShipping",
        "transactionType",
        "billingType",
        "logoId",
        "paymentIntegrationTypeId"
})
@Getter
@NoArgsConstructor
public class PaymentGatewaySettingsUnifiedPayments {

    @JsonProperty("merchantId")
    public String merchantId = "clickatellcommerce_mtn_stage";
    @JsonProperty("saProfileId")
    public String saProfileId = "666965A6-F975-402B-9D9D-AA22C616BB16";
    @JsonProperty("saAccessKey")
    public String saAccessKey = "3dc3b8ee05ca33e29ab8bcc11fb165db";
    @JsonProperty("restApiAccessKey")
    public String restApiAccessKey = "0a7c1d5a-db4e-4c0e-8d70-f740254d8167";
    @JsonProperty("restApiSecretKey")
    public String restApiSecretKey = "UvQM/sT6AyEuyf+tXHk4gJtaM5Ez8QL2lWk1QXsYXcQ=";
    @JsonProperty("intermediatePaymentPageRequestUrl")
    public String intermediatePaymentPageRequestUrl = null;
    @JsonProperty("localeId")
    public Integer localeId = 13;
    @JsonProperty("countryId")
    public Integer countryId = 40;
    @JsonProperty("shipToCountriesIds")
    public List<Integer> shipToCountriesIds = null;
    @JsonProperty("defaultCurrencyId")
    public int defaultCurrencyId = 156;
    @JsonProperty("currenciesIds")
    public List<Integer> currenciesIds = Collections.singletonList(156);
    @JsonProperty("cardNetworksIds")
    public List<Integer> cardNetworksIds = Arrays.asList(2, 1, 6, 4, 5, 3);
    @JsonProperty("paymentTypesIds")
    public List<Integer> paymentTypesIds = Arrays.asList(1, 2);
    @JsonProperty("requestEmail")
    public Boolean requestEmail = true;
    @JsonProperty("requestPhone")
    public Boolean requestPhone = false;
    @JsonProperty("requestShipping")
    public Boolean requestShipping = false;
    @JsonProperty("transactionType")
    public String transactionType = "authorization";
    @JsonProperty("billingType")
    public String billingType = "FULL";
    @JsonProperty("logoId")
    public String logoId = null;
    @JsonProperty("paymentIntegrationTypeId")
    public int paymentIntegrationTypeId = 2;
}
