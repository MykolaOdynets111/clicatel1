package com.clickatell.models.mc2.accounts.request.billing_info;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 08.08.2016.
 */
public class ContactInfoRequest {
    private String cellPhone;
    private String emailAddress;
    private String firstName;
    private String lastName;

    /**
     * No args constructor for use in serialization
     */
    public ContactInfoRequest() {
        cellPhone = "3800011111";
        emailAddress = "email.address_" + StringUtils.generateRandomString(5) + "@fakeemail.perfectial.com";
        firstName = "FirstName_" + StringUtils.generateRandomString(6);
        lastName = "LastName_" + StringUtils.generateRandomString(6);
    }

    /**
     * @param lastName
     * @param cellPhone
     * @param emailAddress
     * @param firstName
     */
    public ContactInfoRequest(String cellPhone, String emailAddress, String firstName, String lastName) {
        this.cellPhone = cellPhone;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return The cellPhone
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * @param cellPhone The cellPhone
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public ContactInfoRequest withCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    /**
     * @return The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress The emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ContactInfoRequest withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public ContactInfoRequest withFirstName(String firstName) {
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

    public ContactInfoRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactInfoRequest {\n");

        sb.append("    cellPhone: ").append(toIndentedString(cellPhone)).append("\n");
        sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cellPhone).append(emailAddress).append(firstName).append(lastName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactInfoRequest) == false) {
            return false;
        }
        ContactInfoRequest rhs = ((ContactInfoRequest) other);
        return new EqualsBuilder().append(cellPhone, rhs.cellPhone).append(emailAddress, rhs.emailAddress).append(firstName, rhs.firstName).append(lastName, rhs.lastName).isEquals();
    }
}
