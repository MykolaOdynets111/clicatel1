package com.clickatell.models.mc2.two_way_short.request;

import com.clickatell.models.mc2.two_way_short.request.enums.ShortNumberTypeEnum;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Created by oshchur on 07.09.2016.
 */
public class ShortNumberSubscriptionPlanRequest {
    @SerializedName("countryId")
    private Integer countryId = null;

    @SerializedName("type")
    private ShortNumberTypeEnum type = null;

    public ShortNumberSubscriptionPlanRequest() {
    }

    public ShortNumberSubscriptionPlanRequest(Integer countryId, ShortNumberTypeEnum type) {
        this.countryId = countryId;
        this.type = type;
    }

    public ShortNumberSubscriptionPlanRequest countryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    /**
     * Get countryId
     *
     * @return countryId
     **/
    @ApiModelProperty(example = "null", required = true)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public ShortNumberSubscriptionPlanRequest type(ShortNumberTypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @ApiModelProperty(example = "null", required = true)
    public ShortNumberTypeEnum getType() {
        return type;
    }

    public void setType(ShortNumberTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortNumberSubscriptionPlanRequest shortNumberSubscriptionPlanRequest = (ShortNumberSubscriptionPlanRequest) o;
        return Objects.equals(this.countryId, shortNumberSubscriptionPlanRequest.countryId) &&
                Objects.equals(this.type, shortNumberSubscriptionPlanRequest.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShortNumberApplicationFormResponse {\n");

        sb.append("    countryId: ").append(toIndentedString(countryId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
