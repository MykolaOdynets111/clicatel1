package com.clickatell.models.mc2.user_profiles.request;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oshchur on 13.07.2016.
 */
public class NewPasswordRequest {
    private String newPassword ;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewPasswordRequest() {
        this.newPassword  = StringUtils.generateRandomString(5);
    }

    /**
     *
     * @param newPassword
     */
    public NewPasswordRequest(String newPassword) {
        this.newPassword  = newPassword;
    }

    /**
     *
     * @return
     *     The newPassword
     */
    public String getNewPassword () {
        return newPassword ;
    }

    /**
     *
     * @param newPassword
     *     The newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword  = newPassword;
    }

    public NewPasswordRequest withNewPassword(String newPassword) {
        this.newPassword  = newPassword;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
