
package com.clickatell.models.mc2.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class OrdersDetailsResponse {

    private List<OrderDetailResponse> items = new ArrayList<OrderDetailResponse>();
    private Integer subTotal;
    private Integer tax;
    private Integer total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OrdersDetailsResponse() {
    }

    /**
     * 
     * @param total
     * @param tax
     * @param subTotal
     * @param items
     */
    public OrdersDetailsResponse(List<OrderDetailResponse> items, Integer subTotal, Integer tax, Integer total) {
        this.items = items;
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
    }

    /**
     * 
     * @return
     *     The items
     */
    public List<OrderDetailResponse> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<OrderDetailResponse> items) {
        this.items = items;
    }

    public OrdersDetailsResponse withItems(List<OrderDetailResponse> items) {
        this.items = items;
        return this;
    }

    /**
     * 
     * @return
     *     The subTotal
     */
    public Integer getSubTotal() {
        return subTotal;
    }

    /**
     * 
     * @param subTotal
     *     The subTotal
     */
    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public OrdersDetailsResponse withSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    /**
     * 
     * @return
     *     The tax
     */
    public Integer getTax() {
        return tax;
    }

    /**
     * 
     * @param tax
     *     The tax
     */
    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public OrdersDetailsResponse withTax(Integer tax) {
        this.tax = tax;
        return this;
    }

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    public OrdersDetailsResponse withTotal(Integer total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(items).append(subTotal).append(tax).append(total).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OrdersDetailsResponse) == false) {
            return false;
        }
        OrdersDetailsResponse rhs = ((OrdersDetailsResponse) other);
        return new EqualsBuilder().append(items, rhs.items).append(subTotal, rhs.subTotal).append(tax, rhs.tax).append(total, rhs.total).isEquals();
    }

}
