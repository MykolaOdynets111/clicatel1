package com.clickatell.models.mc2.users.request;

import com.clickatell.models.mc2.users.response.getallusers.User;

import java.util.Objects;

/**
 * Created by sbryt on 8/17/2016.
 */
public class UpdateUserMetadataRequest {
    private String email = null;
    private String firstName = null;
    private String lastName = null;

    public UpdateUserMetadataRequest(User user) {
        email = user.getEmail();
        lastName = user.getLastName();
        firstName = user.getFirstName();
    }

    /**
     **/
    public UpdateUserMetadataRequest email(String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     **/
    public UpdateUserMetadataRequest firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     **/
    public UpdateUserMetadataRequest lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateUserMetadataRequest updateUserMetadataRequest = (UpdateUserMetadataRequest) o;

        return Objects.equals(this.email, updateUserMetadataRequest.email) &&
                Objects.equals(this.firstName, updateUserMetadataRequest.firstName) &&
                Objects.equals(this.lastName, updateUserMetadataRequest.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateUserMetadataRequest {\n");

        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
