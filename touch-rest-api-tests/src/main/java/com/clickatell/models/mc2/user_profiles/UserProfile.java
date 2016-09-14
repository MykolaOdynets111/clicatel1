package com.clickatell.models.mc2.user_profiles;

import com.clickatell.models.mc2.accounts.Account;
import com.clickatell.models.mc2.integration.IntegrationClass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 6/10/2016.
 */
public class UserProfile {

    private String token;
    private List<Account> accounts = new ArrayList<Account>();
    private boolean active;

    /**
     * No args constructor for use in serialization
     */
    public UserProfile() {
    }

    /**
     * @param accounts
     * @param token
     */
    public UserProfile(String token, List<Account> accounts) {
        this.token = token;
        this.accounts = accounts;
    }

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts The accounts
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(token).append(accounts).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IntegrationClass) == false) {
            return false;
        }
        UserProfile rhs = ((UserProfile) other);
        return new EqualsBuilder().append(token, rhs.token).append(accounts, rhs.accounts).isEquals();
    }

}
