package com.clickatell.models.mc2.two_way.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oshchur on 30.08.2016.
 */
public class TwoWayRequest {
    private String type;
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public TwoWayRequest() {
    }

    /**
     *
     * @param type
     * @param status
     */
    public TwoWayRequest(String type, String status) {
        this.type = type;
        this.status = status;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setPassword(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
