package com.clickatell.models.mc2.accounts.request;

/**
 * Created by oshchur on 08.08.2016.
 */
public class SandboxNumberRequest {
    String number;

    public SandboxNumberRequest(String number) {
        this.number = number;
    }

    public SandboxNumberRequest() {
        this.number = "+16305047845";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "SandboxNumberRequest{" +
                "number='" + number + '\'' +
                '}';
    }
}
