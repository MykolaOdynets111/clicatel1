package com.clickatell.models.mc2.two_way_short.request;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.SubscriptionPlan;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 07.09.2016.
 */
public class AddShortNumberToOrderRequest {
    private Integer tractProductId;
    private Integer tractRecurrencePriceId;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddShortNumberToOrderRequest() {
    }

    /**
     *
     * @param tractRecurrencePriceId
     * @param tractProductId
     */
    public AddShortNumberToOrderRequest(Integer tractProductId, Integer tractRecurrencePriceId) {
        this.tractProductId = tractProductId;
        this.tractRecurrencePriceId = tractRecurrencePriceId;
    }

    /**
     *
     * @param subscriptionPlan
     */
    public AddShortNumberToOrderRequest(SubscriptionPlan subscriptionPlan) {
        this.tractProductId = subscriptionPlan.getId();
        this.tractRecurrencePriceId = subscriptionPlan.getRecurringPrice().getId();
    }

    /**
     *
     * @return
     * The tractProductId
     */
    public Integer getTractProductId() {
        return tractProductId;
    }

    /**
     *
     * @param tractProductId
     * The tractProductId
     */
    public void setTractProductId(Integer tractProductId) {
        this.tractProductId = tractProductId;
    }

    public AddShortNumberToOrderRequest withTractProductId(Integer tractProductId) {
        this.tractProductId = tractProductId;
        return this;
    }

    /**
     *
     * @return
     * The tractRecurrencePriceId
     */
    public Integer getTractRecurrencePriceId() {
        return tractRecurrencePriceId;
    }

    /**
     *
     * @param tractRecurrencePriceId
     * The tractRecurrencePriceId
     */
    public void setTractRecurrencePriceId(Integer tractRecurrencePriceId) {
        this.tractRecurrencePriceId = tractRecurrencePriceId;
    }

    public AddShortNumberToOrderRequest withTractRecurrencePriceId(Integer tractRecurrencePriceId) {
        this.tractRecurrencePriceId = tractRecurrencePriceId;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tractProductId).append(tractRecurrencePriceId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AddShortNumberToOrderRequest) == false) {
            return false;
        }
        AddShortNumberToOrderRequest rhs = ((AddShortNumberToOrderRequest) other);
        return new EqualsBuilder().append(tractProductId, rhs.tractProductId).append(tractRecurrencePriceId, rhs.tractRecurrencePriceId).isEquals();
    }
}
