package com.clickatell.models.mc2.accounts.request;

/**
 * Created by oshchur on 08.08.2016.
 */
public class VerificationClass {
    private String verificationCode;
    private String phoneId;

    public VerificationClass(String verificationCode, String phoneId) {
        this.verificationCode = verificationCode;
        this.phoneId = phoneId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }
}
