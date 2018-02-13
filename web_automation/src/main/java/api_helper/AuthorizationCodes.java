package api_helper;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum AuthorizationCodes {

    DEV_TOKEN("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4MzJjYjE0NjA4NjA0OGE0ODFmNDEzN2VkZjJjZjkzYyIsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNDk1NjI2MjE2fQ.V-4S9om-58t_kL_Bua4vr8uzmbzmtL8f2w0yrXjZJpI");

    String authorizationToken;

    AuthorizationCodes(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getAuthToken() {
        return this.authorizationToken;
    }


}
