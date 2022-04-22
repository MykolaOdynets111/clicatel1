package datamanager.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "paymentType",
        "status",
        "defaultPaymentMethod",
        "cardType",
        "cardNumber",
        "cardHolderFirstName",
        "cardHolderLastName",
        "expirationYear",
        "expirationMonth"
})
public class PaymentMethod {

    @JsonProperty("id")
    private String id;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("status")
    private String status;
    @JsonProperty("defaultPaymentMethod")
    private Boolean defaultPaymentMethod;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardHolderFirstName")
    private String cardHolderFirstName;
    @JsonProperty("cardHolderLastName")
    private String cardHolderLastName;
    @JsonProperty("expirationYear")
    private Integer expirationYear;
    @JsonProperty("expirationMonth")
    private Integer expirationMonth;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("paymentType")
    public String getPaymentType() {
        return paymentType;
    }

    @JsonProperty("paymentType")
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("defaultPaymentMethod")
    public Boolean getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    @JsonProperty("defaultPaymentMethod")
    public void setDefaultPaymentMethod(Boolean defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }

    @JsonProperty("cardType")
    public String getCardType() {
        return cardType;
    }

    @JsonProperty("cardType")
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @JsonProperty("cardNumber")
    public String getCardNumber() {
        return cardNumber;
    }

    @JsonProperty("cardNumber")
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @JsonProperty("cardHolderFirstName")
    public String getCardHolderFirstName() {
        return cardHolderFirstName;
    }

    @JsonProperty("cardHolderFirstName")
    public void setCardHolderFirstName(String cardHolderFirstName) {
        this.cardHolderFirstName = cardHolderFirstName;
    }

    @JsonProperty("cardHolderLastName")
    public String getCardHolderLastName() {
        return cardHolderLastName;
    }

    @JsonProperty("cardHolderLastName")
    public void setCardHolderLastName(String cardHolderLastName) {
        this.cardHolderLastName = cardHolderLastName;
    }

    @JsonProperty("expirationYear")
    public Integer getExpirationYear() {
        return expirationYear;
    }

    @JsonProperty("expirationYear")
    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @JsonProperty("expirationMonth")
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    @JsonProperty("expirationMonth")
    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}