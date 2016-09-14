
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Features {

    private String messageType = MessageType.ONE_WAY;
    private String deliveryType = DeliveryType.STANDARD;
    private String trackingType = TrackingType.BILLABLE;

    /**
     * No args constructor for use in serialization
     */
    public Features() {
    }

    /**
     * @param deliveryType
     * @param messageType
     * @param trackingType
     */
    public Features(String messageType, String deliveryType, String trackingType) {
        this.messageType = messageType;
        this.deliveryType = deliveryType;
        this.trackingType = trackingType;
    }

    /**
     * @return The messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType The messageType
     */
    public Features setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * @return The deliveryType
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * @param deliveryType The deliveryType
     */
    public Features setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    /**
     * @return The trackingType
     */
    public String getTrackingType() {
        return trackingType;
    }

    /**
     * @param trackingType The trackingType
     */
    public Features setTrackingType(String trackingType) {
        this.trackingType = trackingType;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Features)) {
            return false;
        }
        Features rhs = ((Features) other);
        return new EqualsBuilder().append(messageType, rhs.messageType).append(deliveryType, rhs.deliveryType).append(trackingType, rhs.trackingType).isEquals();
    }

}
