package com.touch.tests;

import com.clickatell.models.users.response.getallusers.User;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.tenant.*;
import com.touch.models.touch.user_profile.ListUserProfilesResponse;
import com.touch.models.touch.user_profile.UserProfileRequest;
import com.touch.models.touch.user_profile.UserProfileResponse;
import com.touch.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class UserProfileTests extends BaseTestClass {

    @BeforeClass
    public void beforeClass() {

    }

    @Test
    public void addNewUserProfile() {
        String id = "test" + StringUtils.generateRandomString(3);
        int userAmountBefore = userProfileActions.getAllUserProfiles(token,ListUserProfilesResponse.class).getUserProfiles().size();
        //Verify that we add new user-profile successful
        Assert.assertEquals(userProfileActions.addDefaultUserProfile(id, token), 201);
//        Verify that we have new user-profile in user-profile list
        List<UserProfileResponse> userProfiles = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles();
        Assert.assertEquals(userAmountBefore + 1, userProfiles.size());
        // we will add additional verification step when we have response for POST action for user-profile
//        TODO add changes when this bug will be fixed https://clickatell.atlassian.net/browse/TPLAT-486
        Assert.assertTrue(userProfiles.contains(userProfileActions.getUserProfile(id, token, UserProfileResponse.class)));
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
    }

    @Test
    public void addExitingUserProfile() {
        String id = "test" + StringUtils.generateRandomString(3);
//        create new user-profile
        userProfileActions.addDefaultUserProfile(id, token);
        //Try to create user-profile with same Id and verify that we get correct response
        Assert.assertEquals(userProfileActions.addDefaultUserProfile(id, token), 409);
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
    }

    @Test
    public void addUserProfileWithWrongData() {
        String id = "test" + StringUtils.generateRandomString(3);
        String name = "Test" + StringUtils.generateRandomString(6);
        String surname = "surname" + StringUtils.generateRandomString(6);
        String email = StringUtils.generateRandomString(6);
        String contactPhone = "07700 900039";
        String gender = "male";
        String birthdate = "wrong birth date";
        String country = "Ukraine";
        String city = "Lviv";
        String address = "addressTest";
        String fileName = "tenant_logo.jpg";
        String file = getFullPathToFile("TenantResources/" + fileName);
        Map<String, Object> userProfileData = new HashMap<>();
        userProfileData.put("email", email);
        userProfileData.put("id", id);
        userProfileData.put("contactPhone", contactPhone);
        userProfileData.put("name", name);
        userProfileData.put("surname", surname);
        userProfileData.put("gender", gender);
        userProfileData.put("birthdate", birthdate);
        userProfileData.put("country", country);
        userProfileData.put("city", city);
        userProfileData.put("address", address);
        int userAmountBefore = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles().size();
        String regExpErrorMessage = "Text '" + birthdate + "' could not be parsed at index 0";
        Assert.assertTrue(userProfileActions.addUserProfile(userProfileData, new File(file), token, ErrorMessage.class).getErrorMessage().matches(regExpErrorMessage));
        List<UserProfileResponse> userProfiles = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles();
        Assert.assertEquals(userAmountBefore, userProfiles.size());

    }

    @Test
    public void addUserProfileWithoutData() {
        Map<String, Object> userProfileData = new HashMap<>();
        int userAmountBefore = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles().size();
//        Verify that we can create user-profile with empty fields
        Assert.assertEquals(userProfileActions.addUserProfile(userProfileData, null, token), 201);
//        TODO add verification that Id id not empty in this case and was auto generated
        List<UserProfileResponse> userProfiles = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles();
        Assert.assertEquals(userAmountBefore + 1, userProfiles.size());
    }

    @Test
    public void deleteUserProfile() {
        String id = "test" + StringUtils.generateRandomString(3);
        userProfileActions.addDefaultUserProfile(id, token);
        int userAmountBefore = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles().size();
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
        List<UserProfileResponse> userProfiles = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class).getUserProfiles();
        Assert.assertEquals(userAmountBefore - 1, userProfiles.size());
    }

    @Test
    public void updateUserProfile() {
        String newName = "New name";
        String id = "test" + StringUtils.generateRandomString(3);
//        create new user-profile
        userProfileActions.addDefaultUserProfile(id, token);
//        create userProfile for updating existing user-profile
        UserProfileRequest userProfile = new UserProfileRequest();
//        update name in userProfile
        userProfile.setName(newName);
//        update name for existing user-profile and verify that update have been done successful
        Assert.assertEquals(userProfileActions.updateUserProfile(id, userProfile, token), 200);
        Assert.assertEquals(userProfileActions.getUserProfile(id, token, UserProfileResponse.class).getName(), newName);
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
    }

    @Test
    public void updateUserProfileWithErrors() {
        String newName = "New name";
        String id = "test" + StringUtils.generateRandomString(3);
//        create userProfile for updating not existing user-profile
        UserProfileRequest userProfile = new UserProfileRequest();
//        update name in userProfile
        userProfile.setName(newName);
//        update name for not existing user-profile and verify that update have been done successful
        Assert.assertEquals(userProfileActions.updateUserProfile(id, userProfile, token), 404);
    }

    @Test
    public void addPhotoToUserProfile() {
        String id = "test" + StringUtils.generateRandomString(3);
//        create new user-profile
        userProfileActions.addDefaultUserProfile(id, token);
        String fileName = "bg_chat_image.jpg";
        String file = getFullPathToFile("TenantResources/" + fileName);
//        update photo for existing user-profile
        Assert.assertEquals(userProfileActions.updateUserProfileImage(id, new File(file), token), 200);
//        update photo for not existing user-profile
        Assert.assertEquals(userProfileActions.updateUserProfileImage("not existing", new File(file), token), 404);
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
    }

    @Test
    public void deletePhotoFromUserProfile() {
        String id = "test" + StringUtils.generateRandomString(3);
//        create new user-profile
        userProfileActions.addDefaultUserProfile(id, token);
//        update photo for existing user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfileImage(id, token), 200);
        //Delete user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfile(id, token), 200);
//        update photo for not existing user-profile
        Assert.assertEquals(userProfileActions.deleteUserProfileImage(id, token), 404);
    }

    @AfterClass
    public void afterClass() {
        ListUserProfilesResponse allUserProfiles = userProfileActions.getAllUserProfiles(token, ListUserProfilesResponse.class);
        for (UserProfileResponse userProfile : allUserProfiles.getUserProfiles()) {
            if (userProfile.getName()==null||userProfile.getName().contains("Test"))
                userProfileActions.deleteUserProfile(userProfile.getId(), token);
        }
    }

    private String getFullPathToFile(String pathToFile) {
        return UserProfileTests.class.getClassLoader().getResource(pathToFile).getPath();
    }


}
