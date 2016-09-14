
package com.clickatell.models.mc2.two_way_long.response.subscription_plans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class NumbersSubscriptionPlans {

    private List<NumbersSubscriptionPlan> numbersSubscriptionPlans = new ArrayList<NumbersSubscriptionPlan>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public NumbersSubscriptionPlans() {
    }

    /**
     * 
     * @param numbersSubscriptionPlans
     */
    public NumbersSubscriptionPlans(List<NumbersSubscriptionPlan> numbersSubscriptionPlans) {
        this.numbersSubscriptionPlans = numbersSubscriptionPlans;
    }

    /**
     * 
     * @return
     *     The numbersSubscriptionPlans
     */
    public List<NumbersSubscriptionPlan> getNumbersSubscriptionPlans() {
        return numbersSubscriptionPlans;
    }

    public NumbersSubscriptionPlan getSubscriptionPlansByPhoneId(String phoneId){
        return numbersSubscriptionPlans.stream().filter(p->p.getNumberId().equals(phoneId)).findFirst().get();
    }

    /**
     * 
     * @param numbersSubscriptionPlans
     *     The numbersSubscriptionPlans
     */
    public void setNumbersSubscriptionPlans(List<NumbersSubscriptionPlan> numbersSubscriptionPlans) {
        this.numbersSubscriptionPlans = numbersSubscriptionPlans;
    }

    public NumbersSubscriptionPlans withNumbersSubscriptionPlans(List<NumbersSubscriptionPlan> numbersSubscriptionPlans) {
        this.numbersSubscriptionPlans = numbersSubscriptionPlans;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(numbersSubscriptionPlans).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NumbersSubscriptionPlans) == false) {
            return false;
        }
        NumbersSubscriptionPlans rhs = ((NumbersSubscriptionPlans) other);
        return new EqualsBuilder().append(numbersSubscriptionPlans, rhs.numbersSubscriptionPlans).isEquals();
    }

}
