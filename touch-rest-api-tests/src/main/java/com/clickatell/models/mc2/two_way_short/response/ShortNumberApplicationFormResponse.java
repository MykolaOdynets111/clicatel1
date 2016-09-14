package com.clickatell.models.mc2.two_way_short.response;

import com.clickatell.models.mc2.two_way_short.request.ShortNumberApplicationFormRequest;
import com.clickatell.models.mc2.two_way_short.request.enums.TypeEnum;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by oshchur on 02.09.2016.
 */
public class ShortNumberApplicationFormResponse {
    @SerializedName("countryId")
    private Integer countryId = null;

    @SerializedName("type")
    private TypeEnum type = null;

    @SerializedName("shortNumber")
    private String shortNumber = null;

    @SerializedName("additionalData")
    private Map<String, Object> additionalData = new HashMap<>();

    @SerializedName("id")
    private String id = null;

    @SerializedName("status")
    private String status = null;

    public ShortNumberApplicationFormResponse() {
    }

    public ShortNumberApplicationFormResponse(ShortNumberApplicationFormRequest shortNumberApplicationFormRequest, String id) {
        this.countryId = shortNumberApplicationFormRequest.getCountryId();
        this.type = shortNumberApplicationFormRequest.getType();
        this.shortNumber = shortNumberApplicationFormRequest.getShortNumber();
        this.id = id;
        this.status = "DRAFT";
    }

    public ShortNumberApplicationFormResponse(ShortNumberApplicationFormRequest shortNumberApplicationFormRequest, String id, String status) {
        this.countryId = shortNumberApplicationFormRequest.getCountryId();
        this.type = shortNumberApplicationFormRequest.getType();
        this.shortNumber = shortNumberApplicationFormRequest.getShortNumber();
        this.id = id;
        this.status = status;
    }

    public ShortNumberApplicationFormResponse countryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    /**
     * Get countryId
     *
     * @return countryId
     **/
    @ApiModelProperty(example = "null", required = true, value = "")
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public ShortNumberApplicationFormResponse type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    @ApiModelProperty(example = "null", required = true, value = "")
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public ShortNumberApplicationFormResponse shortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
        return this;
    }

    /**
     * Get shortNumber
     *
     * @return shortNumber
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }

    public ShortNumberApplicationFormResponse additionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
        return this;
    }

    public ShortNumberApplicationFormResponse putAdditionalDataItem(String key, Object additionalDataItem) {
        this.additionalData.put(key, additionalDataItem);
        return this;
    }

    /**
     * Get additionalData
     *
     * @return additionalData
     **/
    @ApiModelProperty(example = "null", required = true, value = "")
    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, Object> additionalData) {
        this.additionalData = additionalData;
    }

    public ShortNumberApplicationFormResponse id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShortNumberApplicationFormResponse status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortNumberApplicationFormResponse shortNumberApplicationFormResponse = (ShortNumberApplicationFormResponse) o;
        return Objects.equals(this.countryId, shortNumberApplicationFormResponse.countryId) &&
                Objects.equals(this.type, shortNumberApplicationFormResponse.type) &&
                Objects.equals(this.shortNumber, shortNumberApplicationFormResponse.shortNumber) &&
                Objects.equals(this.additionalData, shortNumberApplicationFormResponse.additionalData) &&
                Objects.equals(this.id, shortNumberApplicationFormResponse.id) &&
                Objects.equals(this.status, shortNumberApplicationFormResponse.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, type, shortNumber, additionalData, id, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShortNumberApplicationFormResponse {\n");

        sb.append("    countryId: ").append(toIndentedString(countryId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    shortNumber: ").append(toIndentedString(shortNumber)).append("\n");
        sb.append("    additionalData: ").append(toIndentedString(additionalData)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
