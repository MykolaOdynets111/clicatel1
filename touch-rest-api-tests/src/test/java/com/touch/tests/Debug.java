package com.touch.tests;

import com.clickatell.models.users.response.getallusers.User;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.tenant.*;
import com.touch.models.touch.user_profile.ListUserProfilesResponse;
import com.touch.models.touch.user_profile.UserProfileResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/8/2016.
 */
public class Debug extends BaseTestClass {
    //    @Test
    public void createNewTenantWithoutAccount() {
        int size1 = tenantActions.getTenantsList().size();
        User user = userActions.createUserWithTouchPlatformAndLogin();
        int size2 = tenantActions.getTenantsList().size();

    }

    //    @Test
    public void deleteAllTestUserProfiles() {
        ListUserProfilesResponse allUserProfiles = userProfileActions.getAllUserProfiles(ListUserProfilesResponse.class);
        int size = allUserProfiles.getUserProfiles().size();
        for (UserProfileResponse userProfile : allUserProfiles.getUserProfiles()) {
            if (userProfile.getName().contains("Test"))
                userProfileActions.deleteUserProfile(userProfile.getId());
        }
    }

        @Test
    public void deleteAllTestTenants() {
        List<TenantResponse> tenantsList = tenantActions.getTenantsList();
        for (TenantResponse tenant : tenantsList) {
            if (tenant.getTenantOrgName().contains("Test") || tenant.getTenantOrgName().contains("test"))
                tenantActions.deleteTenant(tenant.getId());
        }
    }


//    @Test
    public void getNotExistingTenantFlow() throws IOException {
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse tenant1 = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        tenantRequest = new TenantRequest();
        TenantResponse tenant2 = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        String tenantId1 = tenant1.getId();
        String tenantId2 = tenant2.getId();
        GpsRequest gpsRequest1 = new GpsRequest(60f, 60f);
        GpsRequest gpsRequest2 = new GpsRequest(70f, 70f);
        String addressId1 = tenant1.getTenantAddresses().get(0).getId();
        String addressId2 = tenant2.getTenantAddresses().get(0).getId();
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId1, addressId1, gpsRequest1);
        tenantActions.updateTenantAddressLongitudeAndLatitude(tenantId2, addressId2, gpsRequest2);
        List<TenantResponse> nearestTenantsList = tenantActions.getNearestTenantsList(gpsRequest1.getLat().toString(), gpsRequest1.getLng().toString(), "1500000");
        List<TenantResponse> newTenantsList = Arrays.asList(tenant1, tenant2);
        Assert.assertTrue(nearestTenantsList.containsAll(newTenantsList));

        Assert.assertEquals(tenantActions.deleteTenant(tenantId1), 200);
        Assert.assertEquals(tenantActions.deleteTenant(tenantId2), 200);
    }

    private String getFullPathToFile(String pathToFile) {
        return Debug.class.getClassLoader().getResource(pathToFile).getPath();
    }

    private boolean isEqualInputStreams(InputStream i1, InputStream i2) throws IOException {

        try {
            // do the compare
            while (true) {
                int fr = i1.read();
                int tr = i2.read();

                if (fr != tr)
                    return false;

                if (fr == -1)
                    return true;
            }

        } finally {
            if (i1 != null)
                i1.close();
            if (i2 != null)
                i2.close();
        }
    }
}
