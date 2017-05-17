
package com.touch.models.touch.tenant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dayOfWeek",
    "startWorkTime",
    "endWorkTime"
})
public class BusinessHour {

    @JsonProperty("dayOfWeek")
    private String dayOfWeek;
    @JsonProperty("startWorkTime")
    private String startWorkTime;
    @JsonProperty("endWorkTime")
    private String endWorkTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BusinessHour() {
        this.dayOfWeek = "FRIDAY";
        this.startWorkTime = "00:00";
        this.endWorkTime = "23:59";
    }

    /**
     * 
     * @param startWorkTime
     * @param endWorkTime
     * @param dayOfWeek
     */
    public BusinessHour(String dayOfWeek, String startWorkTime, String endWorkTime) {
        super();
        this.dayOfWeek = dayOfWeek;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
    }

    @JsonProperty("dayOfWeek")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("dayOfWeek")
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public BusinessHour withDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    @JsonProperty("startWorkTime")
    public String getStartWorkTime() {
        return startWorkTime;
    }

    @JsonProperty("startWorkTime")
    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public BusinessHour withStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
        return this;
    }

    @JsonProperty("endWorkTime")
    public String getEndWorkTime() {
        return endWorkTime;
    }

    @JsonProperty("endWorkTime")
    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public BusinessHour withEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dayOfWeek).append(startWorkTime).append(endWorkTime).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BusinessHour) == false) {
            return false;
        }
        BusinessHour rhs = ((BusinessHour) other);
        return new EqualsBuilder().append(dayOfWeek, rhs.dayOfWeek).append(startWorkTime, rhs.startWorkTime).append(endWorkTime, rhs.endWorkTime).isEquals();
    }

}
