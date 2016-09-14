package com.clickatell.models.mc2.two_way_short.response;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.SubscriptionPlan;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 07.09.2016.
 */
public class ShortNumberSubscriptionPlanResponse {
    private List<SubscriptionPlan> subscriptionPlans = new ArrayList<SubscriptionPlan>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ShortNumberSubscriptionPlanResponse() {
    }

    /**
     *
     * @param subscriptionPlans
     */
    public ShortNumberSubscriptionPlanResponse(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    /**
     *
     * @return
     * The subscriptionPlans
     */
    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    /**
     *
     * @param subscriptionPlans
     * The subscriptionPlans
     */
    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public ShortNumberSubscriptionPlanResponse withSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(subscriptionPlans).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ShortNumberSubscriptionPlanResponse) == false) {
            return false;
        }
        ShortNumberSubscriptionPlanResponse rhs = ((ShortNumberSubscriptionPlanResponse) other);
        return new EqualsBuilder().append(subscriptionPlans, rhs.subscriptionPlans).isEquals();
    }

}
