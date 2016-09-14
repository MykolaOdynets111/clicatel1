package com.clickatell.models.mc2.accounts.response;

import java.util.List;

/**
 * Created by oshchur on 08.08.2016.
 */
public class SandboxNumberListResponse {
    private List<SandboxNumberEntityClass> sandboxNumberList;

    public SandboxNumberListResponse(List<SandboxNumberEntityClass> sandboxNumberList) {
        this.sandboxNumberList = sandboxNumberList;
    }

    public SandboxNumberListResponse() {
    }

    public List<SandboxNumberEntityClass> getSandboxNumberList() {
        return sandboxNumberList;
    }

    public void setSandboxNumberList(List<SandboxNumberEntityClass> sandboxNumberList) {
        this.sandboxNumberList = sandboxNumberList;
    }

    @Override
    public String toString() {
        return "SandboxNumberListResponse{" +
                "sandboxNumberList=" + sandboxNumberList +
                '}';
    }
}
