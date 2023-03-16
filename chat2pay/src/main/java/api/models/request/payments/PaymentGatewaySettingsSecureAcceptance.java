package api.models.request.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "merchantId",
        "restApiAccessKey",
        "restApiSecretKey",
        "saProfileId",
        "saSecretKey",
        "saAccessKey",
        "defaultCurrencyId",
        "currenciesIds",
        "transactionType",
        "paymentIntegrationTypeId"
})
@Getter
@NoArgsConstructor
public class PaymentGatewaySettingsSecureAcceptance {

    @JsonProperty("merchantId")
    public String merchantId = "clickatellcommerce_mtn_stage";
    @JsonProperty("saAccessKey")
    public String saAccessKey = "1d414e58bda9324aabada8c1fb481ec5";
    @JsonProperty("transactionType")
    public String transactionType = "authorization";
    @JsonProperty("defaultCurrencyId")
    public int defaultCurrencyId = 156;
    @JsonProperty("currenciesIds")
    public List<Integer> currenciesIds = Collections.singletonList(156);
    @JsonProperty("saSecretKey")
    public String saSecretKey = "07212b3387204789a8b623c85b4b6b91892f86a3234048cda763fea8de2c5b0fd19195dc54e04256ad960dd8d9f72d64f7b94a8a2ba44ab4b089569f6b2b86c5fe234e0055c547b89260b08cb799cfbeb8a44174653b4d77b51e14c5a6a3aec5d2d62bddfb0141f9a6919601c881a98ad9b34597a03c424790cf4aab6634d699";
    @JsonProperty("saProfileId")
    public String saProfileId = "69D769E2-94A5-4D82-ABBE-1CFA4128D3BB";
    @JsonProperty("restApiAccessKey")
    public String restApiAccessKey = "a9e5d216-fda2-4e3d-8adb-8a299de0d0ab";
    @JsonProperty("restApiSecretKey")
    public String restApiSecretKey = "yPmNEtzNu22cWjL325gU2bIl8nF6385tE90Orn48Pt4=";
    @JsonProperty("paymentIntegrationTypeId")
    public int paymentIntegrationTypeId = 1;
}

