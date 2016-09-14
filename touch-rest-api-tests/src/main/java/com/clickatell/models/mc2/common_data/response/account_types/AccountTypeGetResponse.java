package com.clickatell.models.mc2.common_data.response.account_types;


import java.util.List;

/**
 * Created by sbryt on 8/9/2016.
 */
public class AccountTypeGetResponse {
    private List<AccountTypeEntity> accountTypeEntities;

    public AccountTypeGetResponse() {
    }

    public AccountTypeGetResponse(List<AccountTypeEntity> accountTypeEntities) {
        this.accountTypeEntities = accountTypeEntities;
    }

    public List<AccountTypeEntity> getAccountTypeEntities() {
        return accountTypeEntities;
    }

    public void setAccountTypeEntities(List<AccountTypeEntity> accountTypeEntities) {
        this.accountTypeEntities = accountTypeEntities;
    }
}
