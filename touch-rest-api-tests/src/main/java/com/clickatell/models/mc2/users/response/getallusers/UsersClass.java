package com.clickatell.models.mc2.users.response.getallusers;

/**
 * Created by sbryt on 6/7/2016.
 */


import java.util.ArrayList;
import java.util.List;


public class UsersClass {

    private String accountName;
    private List<User> users = new ArrayList<User>();

    public UsersClass() {
    }

    public UsersClass(String accountName, List<User> users) {
        this.accountName = accountName;
        this.users = users;
    }

    /**
     * @return The accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName The accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return The users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users The users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UsersClass{" +
                "accountName='" + accountName + '\'' +
                ", \nusers=" + users +
                '}';
    }
}