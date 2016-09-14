
package com.clickatell.models.mc2.two_way_long.response.subscription_plans;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums.RecurrencePeriodEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RecurringPrice {

    private Integer id;
    private String price;
    private RecurrencePeriodEnum recurrencePeriod;

    /**
     * No args constructor for use in serialization
     */
    public RecurringPrice() {
    }

    /**
     * @param id
     * @param price
     * @param recurrencePeriod
     */
    public RecurringPrice(Integer id, String price, RecurrencePeriodEnum recurrencePeriod) {
        this.id = id;
        this.price = price;
        this.recurrencePeriod = recurrencePeriod;
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

    public RecurringPrice withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * @return The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    public RecurringPrice withPrice(String price) {
        this.price = price;
        return this;
    }

    /**
     * @return The recurrencePeriod
     */
    public RecurrencePeriodEnum getRecurrencePeriod() {
        return recurrencePeriod;
    }

    /**
     * @param recurrencePeriod The recurrencePeriod
     */
    public void setRecurrencePeriod(RecurrencePeriodEnum recurrencePeriod) {
        this.recurrencePeriod = recurrencePeriod;
    }

    public RecurringPrice withRecurrencePeriod(RecurrencePeriodEnum recurrencePeriod) {
        this.recurrencePeriod = recurrencePeriod;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(price).append(recurrencePeriod).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RecurringPrice) == false) {
            return false;
        }
        RecurringPrice rhs = ((RecurringPrice) other);
        return new EqualsBuilder().append(id, rhs.id).append(price, rhs.price).append(recurrencePeriod, rhs.recurrencePeriod).isEquals();
    }

}
