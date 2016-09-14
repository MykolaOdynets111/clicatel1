
package com.clickatell.models.mc2.response;

import com.clickatell.models.mc2.two_way_long.response.subscription_plans.enums.RecurrencePeriodEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OrderDetailResponse {

    private String id;
    private String item;
    private TypeEnum type;
    private String country;
    private RecurrencePeriodEnum paymentPlan;
    private Integer price;
    private SolutionsEnum solution;

    /**
     * No args constructor for use in serialization
     */
    public OrderDetailResponse() {
    }

    /**
     * @param id
     * @param price
     * @param paymentPlan
     * @param item
     * @param type
     * @param solution
     * @param country
     */
    public OrderDetailResponse(String id, String item, TypeEnum type, String country, RecurrencePeriodEnum paymentPlan, Integer price, SolutionsEnum solution) {
        this.id = id;
        this.item = item;
        this.type = type;
        this.country = country;
        this.paymentPlan = paymentPlan;
        this.price = price;
        this.solution = solution;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public OrderDetailResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item The item
     */
    public void setItem(String item) {
        this.item = item;
    }

    public OrderDetailResponse withItem(String item) {
        this.item = item;
        return this;
    }

    /**
     * @return The type
     */
    public TypeEnum getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(TypeEnum type) {
        this.type = type;
    }

    public OrderDetailResponse withType(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public OrderDetailResponse withCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * @return The paymentPlan
     */
    public RecurrencePeriodEnum getPaymentPlan() {
        return paymentPlan;
    }

    /**
     * @param paymentPlan The paymentPlan
     */
    public void setPaymentPlan(RecurrencePeriodEnum paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public OrderDetailResponse withPaymentPlan(RecurrencePeriodEnum paymentPlan) {
        this.paymentPlan = paymentPlan;
        return this;
    }

    /**
     * @return The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    public OrderDetailResponse withPrice(Integer price) {
        this.price = price;
        return this;
    }

    /**
     * @return The solution
     */
    public SolutionsEnum getSolution() {
        return solution;
    }

    /**
     * @param solution The solution
     */
    public void setSolution(SolutionsEnum solution) {
        this.solution = solution;
    }

    public OrderDetailResponse withSolution(SolutionsEnum solution) {
        this.solution = solution;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(item).append(type).append(country).append(paymentPlan).append(price).append(solution).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrderDetailResponse) == false) {
            return false;
        }
        OrderDetailResponse rhs = ((OrderDetailResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(item, rhs.item).append(type, rhs.type).append(country, rhs.country).append(paymentPlan, rhs.paymentPlan).append(price, rhs.price).append(solution, rhs.solution).isEquals();
    }

}
