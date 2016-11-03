package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ChatSessionResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponse;
import com.touch.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kmakohoniuk on 9/8/2016.
 */
public class Debug extends BaseTestClass {



//        @Test
//    public void deleteAllTestUserProfiles() {
//        ListUserProfilesResponse allUserProfiles = userProfileActions.getAllUserProfiles(ListUserProfilesResponse.class);
//        int size = allUserProfiles.getUserProfiles().size();
//        for (UserProfileResponse userProfile : allUserProfiles.getUserProfiles()) {
//            String id = userProfile.getChatroomJid();
//            if (userProfile.getName().contains("Test"))
//                userProfileActions.deleteUserProfile(userProfile.getChatroomJid());
//        }
//    }

//        @Test
//    public void deleteAllTestTenants() {
//        List<TenantResponse> tenantsList = tenantActions.getTenantsList();
//        for (TenantResponse tenant : tenantsList) {
//            if (tenant.getTenantOrgName().contains("Test") || tenant.getTenantOrgName().contains("test"))
//                tenantActions.deleteTenant(tenant.getChatroomJid());
//        }
//    }


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
