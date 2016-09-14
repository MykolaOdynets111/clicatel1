package com.clickatell.models.mc2.user_profiles.request;

import com.clickatell.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oshchur on 13.07.2016.
 */
public class SendPasswordResetEmailRequest {
    private String email;

    /**
     * No args constructor for use in serialization
     *
     */
    public SendPasswordResetEmailRequest() {
        this.email = StringUtils.generateRandomString(5);
    }

    /**
     *
     * @param email
     */
    public SendPasswordResetEmailRequest(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public SendPasswordResetEmailRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
