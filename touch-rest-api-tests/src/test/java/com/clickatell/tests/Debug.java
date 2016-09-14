package com.clickatell.tests;

import com.clickatell.models.mc2.users.response.getallusers.User;
import org.testng.annotations.Test;

/**
 * Created by kmakohoniuk on 9/8/2016.
 */
public class Debug extends BaseTestClass {
    @Test
    public void createNewTenantWithoutAccount() {
        int size1 = tenantActions.getTenantsList().size();
        User user = userActions.createUserAndLogin();
        int size2 = tenantActions.getTenantsList().size();

    }
}
