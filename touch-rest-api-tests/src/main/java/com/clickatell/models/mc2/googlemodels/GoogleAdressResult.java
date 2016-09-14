
package com.clickatell.models.mc2.googlemodels;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class GoogleAdressResult {

    private List<Result> results = new ArrayList<Result>();
    private String status;

    /**
     * No args constructor for use in serialization
     */
    public GoogleAdressResult() {
    }

    /**
     * @param results
     * @param status
     */
    public GoogleAdressResult(List<Result> results, String status) {
        this.results = results;
        this.status = status;
    }

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    public GoogleAdressResult withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public GoogleAdressResult withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(results).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GoogleAdressResult) == false) {
            return false;
        }
        GoogleAdressResult rhs = ((GoogleAdressResult) other);
        return new EqualsBuilder().append(results, rhs.results).append(status, rhs.status).isEquals();
    }

}
