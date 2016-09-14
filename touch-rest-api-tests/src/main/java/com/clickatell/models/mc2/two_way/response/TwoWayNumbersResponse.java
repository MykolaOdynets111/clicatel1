package com.clickatell.models.mc2.two_way.response;

import com.clickatell.models.mc2.common_data.response.countries.CountryShortResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 12.09.2016.
 */
public class TwoWayNumbersResponse {

    private String id;
    private String number;
    private String status;
    private String type;
    private List<CountryShortResponse> countries = new ArrayList<>();
    private List<Object> integrations = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public TwoWayNumbersResponse() {
    }

    /**
     * @param id
     * @param status
     * @param number
     * @param countries
     * @param type
     * @param integrations
     */
    public TwoWayNumbersResponse(String id, String number, String status, String type, List<CountryShortResponse> countries, List<Object> integrations) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.type = type;
        this.countries = countries;
        this.integrations = integrations;
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

    public TwoWayNumbersResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    public TwoWayNumbersResponse withNumber(String number) {
        this.number = number;
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

    public TwoWayNumbersResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public TwoWayNumbersResponse withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The countries
     */
    public List<CountryShortResponse> getCountries() {
        return countries;
    }

    /**
     * @param countries The countries
     */
    public void setCountries(List<CountryShortResponse> countries) {
        this.countries = countries;
    }

    public TwoWayNumbersResponse withCountries(List<CountryShortResponse> countries) {
        this.countries = countries;
        return this;
    }

    /**
     * @return The integrations
     */
    public List<Object> getIntegrations() {
        return integrations;
    }

    /**
     * @param integrations The integrations
     */
    public void setIntegrations(List<Object> integrations) {
        this.integrations = integrations;
    }

    public TwoWayNumbersResponse withIntegrations(List<Object> integrations) {
        this.integrations = integrations;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(number).append(status).append(type).append(countries).append(integrations).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TwoWayNumbersResponse) == false) {
            return false;
        }
        TwoWayNumbersResponse rhs = ((TwoWayNumbersResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(number, rhs.number).append(status, rhs.status).append(type, rhs.type).append(countries, rhs.countries).append(integrations, rhs.integrations).isEquals();
    }
}
