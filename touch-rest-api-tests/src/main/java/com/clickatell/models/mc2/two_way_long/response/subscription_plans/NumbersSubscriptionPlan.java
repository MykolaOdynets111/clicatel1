
package com.clickatell.models.mc2.two_way_long.response.subscription_plans;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums.RecurrencePeriodEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class NumbersSubscriptionPlan {

    private String numberId;
    private List<SubscriptionPlan> subscriptionPlans = new ArrayList<SubscriptionPlan>();

    /**
     * No args constructor for use in serialization
     */
    public NumbersSubscriptionPlan() {
    }

    /**
     * @param subscriptionPlans
     * @param numberId
     */
    public NumbersSubscriptionPlan(String numberId, List<SubscriptionPlan> subscriptionPlans) {
        this.numberId = numberId;
        this.subscriptionPlans = subscriptionPlans;
    }

    /**
     * @return The numberId
     */
    public String getNumberId() {
        return numberId;
    }

    /**
     * @param numberId The numberId
     */
    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public NumbersSubscriptionPlan withNumberId(String numberId) {
        this.numberId = numberId;
        return this;
    }

    /**
     * @return The subscriptionPlans
     */
    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }


    public SubscriptionPlan getSuscriptionPlanByRecurencePeriod(RecurrencePeriodEnum recurrencePeriodEnum){
        return subscriptionPlans.stream().filter(p -> p.getRecurringPrice().getRecurrencePeriod().equals(RecurrencePeriodEnum.MONTH)).findFirst().get();
    }


    /**
     * @param subscriptionPlans The subscriptionPlans
     */
    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public NumbersSubscriptionPlan withSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
        return this;
    }

    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(numberId).append(subscriptionPlans).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NumbersSubscriptionPlan) == false) {
            return false;
        }
        NumbersSubscriptionPlan rhs = ((NumbersSubscriptionPlan) other);
        return new EqualsBuilder().append(numberId, rhs.numberId).append(subscriptionPlans, rhs.subscriptionPlans).isEquals();
    }

}
