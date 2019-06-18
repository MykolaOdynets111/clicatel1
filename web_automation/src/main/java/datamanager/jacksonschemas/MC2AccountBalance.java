package datamanager.jacksonschemas;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "balance",
        "currency",
        "billingBalance",
        "billingCurrency"
})
public class MC2AccountBalance {

    @JsonProperty("balance")
    private Integer balance;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("billingBalance")
    private Integer billingBalance;
    @JsonProperty("billingCurrency")
    private String billingCurrency;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("balance")
    public Integer getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("billingBalance")
    public Integer getBillingBalance() {
        return billingBalance;
    }

    @JsonProperty("billingBalance")
    public void setBillingBalance(Integer billingBalance) {
        this.billingBalance = billingBalance;
    }

    @JsonProperty("billingCurrency")
    public String getBillingCurrency() {
        return billingCurrency;
    }

    @JsonProperty("billingCurrency")
    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
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
