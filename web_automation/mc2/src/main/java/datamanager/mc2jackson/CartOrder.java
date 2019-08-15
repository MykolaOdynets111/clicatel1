package datamanager.mc2jackson;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "item",
        "type",
        "country",
        "paymentPlan",
        "price",
        "setupCost",
        "recurringPrice",
        "solution",
        "autorenew",
        "sku",
        "firstPurchase",
        "amount",
        "packageNumber"
})
public class CartOrder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("item")
    private String item;
    @JsonProperty("type")
    private String type;
    @JsonProperty("country")
    private Object country;
    @JsonProperty("paymentPlan")
    private String paymentPlan;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("setupCost")
    private Object setupCost;
    @JsonProperty("recurringPrice")
    private Object recurringPrice;
    @JsonProperty("solution")
    private String solution;
    @JsonProperty("autorenew")
    private Boolean autorenew;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("firstPurchase")
    private Boolean firstPurchase;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("packageNumber")
    private Integer packageNumber;
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

    @JsonProperty("item")
    public String getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(String item) {
        this.item = item;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("country")
    public Object getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(Object country) {
        this.country = country;
    }

    @JsonProperty("paymentPlan")
    public String getPaymentPlan() {
        return paymentPlan;
    }

    @JsonProperty("paymentPlan")
    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("setupCost")
    public Object getSetupCost() {
        return setupCost;
    }

    @JsonProperty("setupCost")
    public void setSetupCost(Object setupCost) {
        this.setupCost = setupCost;
    }

    @JsonProperty("recurringPrice")
    public Object getRecurringPrice() {
        return recurringPrice;
    }

    @JsonProperty("recurringPrice")
    public void setRecurringPrice(Object recurringPrice) {
        this.recurringPrice = recurringPrice;
    }

    @JsonProperty("solution")
    public String getSolution() {
        return solution;
    }

    @JsonProperty("solution")
    public void setSolution(String solution) {
        this.solution = solution;
    }

    @JsonProperty("autorenew")
    public Boolean getAutorenew() {
        return autorenew;
    }

    @JsonProperty("autorenew")
    public void setAutorenew(Boolean autorenew) {
        this.autorenew = autorenew;
    }

    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("firstPurchase")
    public Boolean getFirstPurchase() {
        return firstPurchase;
    }

    @JsonProperty("firstPurchase")
    public void setFirstPurchase(Boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("packageNumber")
    public Integer getPackageNumber() {
        return packageNumber;
    }

    @JsonProperty("packageNumber")
    public void setPackageNumber(Integer packageNumber) {
        this.packageNumber = packageNumber;
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
