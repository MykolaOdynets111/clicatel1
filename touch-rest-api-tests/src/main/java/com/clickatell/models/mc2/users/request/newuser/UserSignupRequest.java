
package com.clickatell.models.mc2.users.request.newuser;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserSignupRequest {

    private String accountName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> solutions = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public UserSignupRequest() {
        accountName = "touch_test_account_" + StringUtils.generateRandomString(3);
        email = "email" + StringUtils.generateRandomString(10) + "@fake.perfectial.com";
        firstName = "FirstName_" + StringUtils.generateRandomString(8);
        lastName = "LastName_" + StringUtils.generateRandomString(8);
        password = "12345678";
        solutions.add("PLATFORM");
        solutions.add("TOUCH");
    }

    /**
     * @param lastName
     * @param accountName
     * @param solutions
     * @param email
     * @param firstName
     * @param password
     */
    public UserSignupRequest(String accountName, String email, String firstName, String lastName, String password, List<String> solutions) {
        this.accountName = accountName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.solutions = solutions;
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

    public UserSignupRequest withAccountName(String accountName) {
        this.accountName = accountName;
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
    public void setEmail(String email) {
        this.email = email;
    }

    public UserSignupRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserSignupRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserSignupRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public UserSignupRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * @return The solutions
     */
    public List<String> getSolutions() {
        return solutions;
    }

    /**
     * @param solutions The solutions
     */
    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }

    public UserSignupRequest withSolutions(List<String> solutions) {
        this.solutions = solutions;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accountName).append(email).append(firstName).append(lastName).append(password).append(solutions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserSignupRequest)) {
            return false;
        }
        UserSignupRequest rhs = ((UserSignupRequest) other);
        return new EqualsBuilder().append(accountName, rhs.accountName).append(email, rhs.email).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(password, rhs.password).append(solutions, rhs.solutions).isEquals();
    }

}
