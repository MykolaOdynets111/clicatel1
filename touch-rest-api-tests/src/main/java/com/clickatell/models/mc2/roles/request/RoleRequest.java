package com.clickatell.models.mc2.roles.request;

import com.clickatell.utils.StringUtils;

/**
 * Created by oshchur on 05.07.2016.
 */
public class RoleRequest {
    private String name;
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public RoleRequest() {
        this.name = "RoleName_" + StringUtils.generateRandomString(15);
        this.description = "RoleDescription_" + StringUtils.generateRandomString(15);
    }

    /**
     *
     * @param description
     * @param name
     */
    public RoleRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public RoleRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public RoleRequest withDescription(String description) {
        this.description = description;
        return this;
    }
}
