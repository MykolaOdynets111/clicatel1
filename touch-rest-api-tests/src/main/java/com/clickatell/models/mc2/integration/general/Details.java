
package com.clickatell.models.mc2.integration.general;


import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Details {
    
    private String name;
    private String description = "";
    private String env = RunEnvironment.SANDBOX;
    private String api = SupportedAPI.REST_API;

    /**
     * No args constructor for use in serialization
     */
    public Details() {
        name = "IntegrationName_" + StringUtils.generateRandomString(10);
        description = "IntegrationDescription_" + StringUtils.generateRandomString(10);
    }

    /**
     * @param description
     * @param name
     * @param api
     * @param env
     */
    public Details(String name, String description, String env, String api) {
        this.name = name;
        this.description = description;
        this.env = env;
        this.api = api;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public Details setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public Details setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return The env
     */
    public String getEnv() {
        return env;
    }

    /**
     * @param env The env
     */
    public Details setEnv(String env) {
        this.env = env;
        return this;
    }

    /**
     * @return The api
     */
    public String getApi() {
        return api;
    }

    /**
     * @param api The api
     */
    public Details setApi(String api) {
        this.api = api;
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
        if (!(other instanceof Details)) {
            return false;
        }
        Details rhs = ((Details) other);
        return new EqualsBuilder().append(name, rhs.name).append(description, rhs.description).append(env, rhs.env).append(api, rhs.api).isEquals();
    }

}
