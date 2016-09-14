
package com.clickatell.models.mc2.user_profiles.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class GetAccountsResponse {

    private String token;
    private List<Account> accounts = new ArrayList<Account>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetAccountsResponse() {
    }

    /**
     * 
     * @param accounts
     * @param token
     */
    public GetAccountsResponse(String token, List<Account> accounts) {
        this.token = token;
        this.accounts = accounts;
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

    public GetAccountsResponse withToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 
     * @return
     *     The accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * 
     * @param accounts
     *     The accounts
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public GetAccountsResponse withAccounts(List<Account> accounts) {
        this.accounts = accounts;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
