package com.touch.models.touch.auth;

/**
 * Created by kmakohoniuk on 5/22/2017.
 */
public class AccessTokenRequest {

    String clientId;

    public AccessTokenRequest() {
        clientId = "test1";
    }

    public AccessTokenRequest(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "AccessTokenRequest{" +
                "clientId='" + clientId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessTokenRequest that = (AccessTokenRequest) o;

        return clientId != null ? clientId.equals(that.clientId) : that.clientId == null;
    }

    @Override
    public int hashCode() {
        return clientId != null ? clientId.hashCode() : 0;
    }
}
