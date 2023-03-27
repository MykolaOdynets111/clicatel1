package data.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "paymentGatewaySettingsId",
        "paymentGatewayId",
        "paymentIntegrationTypeId",
        "merchantId",
        "saProfileId",
        "saAccessKey",
        "saSecretKey",
        "defaultCurrency",
        "currencies",
        "createdTime",
        "modifiedTime",
        "transactionType",
        "restApiAccessKey",
        "restApiSecretKey"
})

@Getter
public class SecureAcceptanceGatewaySettingsResponse {
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

    @JsonProperty("saSecretKey")
    public String saSecretKey;

    @JsonProperty("defaultCurrency")
    public DefaultCurrency defaultCurrency;

    @JsonProperty("currencies")
    public List<Currency> currencies = null;

    @JsonProperty("createdTime")
    private String createdTime;

    @JsonProperty("modifiedTime")
    private String modifiedTime;

    @JsonProperty("transactionType")
    public String transactionType;

    @JsonProperty("restApiAccessKey")
    public String restApiAccessKey;

    @JsonProperty("restApiSecretKey")
    public String restApiSecretKey;

    public LocalDate getCreatedTime() {
        return parseToLocalDate(createdTime);
    }

    public LocalDate getModifiedTime() {
        return parseToLocalDate(modifiedTime);
    }
}
