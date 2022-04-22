package com.touch.models.mc2.userprofile;

import com.clickatell.actions.UserActions;
import com.clickatell.models.accounts.Account;
import com.clickatell.models.roles.response.allroles.Role;
import com.clickatell.models.users.request.newuser.UserSignupRequest;
import com.clickatell.utils.ApplicationProperties;
import com.clickatell.utils.ConfigApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbryt on 6/7/2016.
 */
public class User {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private List<Role> roles = new ArrayList<Role>();
    private String password;
    private Account mainAccountId = null;
    private List<Account> additionalAccounts = new ArrayList<>();
    private UserActions.UserStatus status;
    private String createdDate;
    private String accountId;
    //TODO need to add tests for this field
    private String phoneNumber;
    private String accountOwner;


    public User(String id, String email, String firstName, String lastName, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public User() {
        email = ConfigApp.USER_EMAIL;
        firstName = ApplicationProperties.getInstance().getPropertyByName("user.firstname");
        lastName = ApplicationProperties.getInstance().getPropertyByName("user.lastname");
        password = ConfigApp.USER_PASSWORD;
        mainAccountId = new Account("2c9f9a215608720801560dedd9b30d39", "serg");
    }

    public User(UserSignupRequest userSignupRequest) {
        email = userSignupRequest.getEmail();
        firstName = userSignupRequest.getFirstName();
        lastName = userSignupRequest.getLastName();
        password = userSignupRequest.getPassword();
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public User setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return The roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles The roles
     */
    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Account getMainAccount() {
        return mainAccountId;
    }

    public User setMainAccount(Account account) {
        this.mainAccountId = account;
        return this;
    }

    public void addAdditionalAccount(Account additionalAccountId) {
        additionalAccounts.add(additionalAccountId);
    }

    public List<Account> getAdditionalAccounts() {
        return additionalAccounts;
    }

    public String getAccountIdByName(String accountByName) {
        for (Account account : additionalAccounts) {
            if (account.getName().equals(accountByName)) {
                return account.getId();
            }
        }
        return null;
    }

    public String getAccountId() {
        return accountId;
    }

    public User setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public User withAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
        return this;
    }
    public UserActions.UserStatus getStatus() {
        return this.status;
    }

    public void setStatus(UserActions.UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.clickatell.models.users.response.getallusers.User user = (com.clickatell.models.users.response.getallusers.User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        return getLastName() != null ? getLastName().equals(user.getLastName()) : user.getLastName() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }


}
