
package com.clickatell.models.mc2.two_way_long.response.subscription_plans;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums.CurrencyEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SubscriptionPlan {

    private Integer id;
    private String sku;
    private String name;
    private CurrencyEnum currency;
    private String subscriptionPeriod;
    private SetupCost setupCost;
    private RecurringPrice recurringPrice;

    /**
     * No args constructor for use in serialization
     */
    public SubscriptionPlan() {
    }

    /**
     * @param id
     * @param name
     * @param recurringPrice
     * @param sku
     * @param subscriptionPeriod
     * @param setupCost
     * @param currency
     */
    public SubscriptionPlan(Integer id, String sku, String name, CurrencyEnum currency, String subscriptionPeriod, SetupCost setupCost, RecurringPrice recurringPrice) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.currency = currency;
        this.subscriptionPeriod = subscriptionPeriod;
        this.setupCost = setupCost;
        this.recurringPrice = recurringPrice;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public SubscriptionPlan withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * @return The sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku The sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    public SubscriptionPlan withSku(String sku) {
        this.sku = sku;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public SubscriptionPlan withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The currency
     */
    public CurrencyEnum getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public SubscriptionPlan withCurrency(CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    /**
     * @return The subscriptionPeriod
     */
    public String getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    /**
     * @param subscriptionPeriod The subscriptionPeriod
     */
    public void setSubscriptionPeriod(String subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public SubscriptionPlan withSubscriptionPeriod(String subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
        return this;
    }

    /**
     * @return The setupCost
     */
    public SetupCost getSetupCost() {
        return setupCost;
    }

    /**
     * @param setupCost The setupCost
     */
    public void setSetupCost(SetupCost setupCost) {
        this.setupCost = setupCost;
    }

    public SubscriptionPlan withSetupCost(SetupCost setupCost) {
        this.setupCost = setupCost;
        return this;
    }

    /**
     * @return The recurringPrice
     */
    public RecurringPrice getRecurringPrice() {
        return recurringPrice;
    }

    /**
     * @param recurringPrice The recurringPrice
     */
    public void setRecurringPrice(RecurringPrice recurringPrice) {
        this.recurringPrice = recurringPrice;
    }

    public SubscriptionPlan withRecurringPrice(RecurringPrice recurringPrice) {
        this.recurringPrice = recurringPrice;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(sku).append(name).append(currency).append(subscriptionPeriod).append(setupCost).append(recurringPrice).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SubscriptionPlan) == false) {
            return false;
        }
        SubscriptionPlan rhs = ((SubscriptionPlan) other);
        return new EqualsBuilder().append(id, rhs.id).append(sku, rhs.sku).append(name, rhs.name).append(currency, rhs.currency).append(subscriptionPeriod, rhs.subscriptionPeriod).append(setupCost, rhs.setupCost).append(recurringPrice, rhs.recurringPrice).isEquals();
    }

}
