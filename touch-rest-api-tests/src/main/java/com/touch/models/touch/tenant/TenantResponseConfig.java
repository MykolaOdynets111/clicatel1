package com.touch.models.touch.tenant;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oshcherbatyy on 19-07-17.
 */
public class TenantResponseConfig {

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @SerializedName("welcomeMessage")
    private String welcomeMessage = null;





}
