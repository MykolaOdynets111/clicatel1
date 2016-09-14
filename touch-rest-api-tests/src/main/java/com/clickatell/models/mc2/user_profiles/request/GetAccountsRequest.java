
package com.clickatell.models.mc2.user_profiles.request;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetAccountsRequest {

    private String email;
    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAccountsRequest() {
        this.email = StringUtils.generateRandomString(5);
        this.password = StringUtils.generateRandomString(8);
    }

    /**
     * 
     * @param email
     * @param password
     */
    public GetAccountsRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public GetAccountsRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public GetAccountsRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
