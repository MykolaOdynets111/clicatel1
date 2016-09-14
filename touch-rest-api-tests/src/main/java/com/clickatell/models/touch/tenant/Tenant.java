package com.clickatell.models.touch.tenant;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/8/2016.
 */
public abstract class Tenant {
    /**
     * Gets or Sets state
     */
    public enum StateEnum {
        @SerializedName("ACTIVE")
        ACTIVE("ACTIVE"),

        @SerializedName("INACTIVE")
        INACTIVE("INACTIVE");

        private String value;

        StateEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }


    @SerializedName("tenantOrgName")
    protected String tenantOrgName = null;

    @SerializedName("contactEmail")
    protected String contactEmail = null;

    @SerializedName("state")
    public Tenant.StateEnum state = null;

    @SerializedName("description")
    protected String description = null;

    @SerializedName("shortDescription")
    protected String shortDescription = null;

    @SerializedName("tenantJid")
    protected String tenantJid = null;

    @SerializedName("tenantBotJid")
    protected String tenantBotJid = null;

    @SerializedName("category")
    protected String category = null;

    @SerializedName("tenantTags")
    protected List<String> tenantTags = new ArrayList<String>();

    @SerializedName("sessionsCapacity")
    protected Integer sessionsCapacity = null;


}
