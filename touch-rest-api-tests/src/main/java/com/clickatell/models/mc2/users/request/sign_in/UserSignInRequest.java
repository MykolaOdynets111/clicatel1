
package com.clickatell.models.mc2.users.request.sign_in;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class UserSignInRequest {

    private String token;
    private String accountId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserSignInRequest() {
    }

    /**
     * 
     * @param accountId
     * @param token
     */
    public UserSignInRequest(String token, String accountId) {
        this.token = token;
        this.accountId = accountId;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public UserSignInRequest withToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 
     * @return
     *     The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 
     * @param accountId
     *     The accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public UserSignInRequest withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
