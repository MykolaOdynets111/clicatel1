
package com.clickatell.models.mc2.users.response.newuser;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserSignupResponse {

    private String accountId;
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserSignupResponse() {
    }

    /**
     * 
     * @param accountId
     * @param userId
     */
    public UserSignupResponse(String accountId, String userId) {
        this.accountId = accountId;
        this.userId = userId;
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

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
