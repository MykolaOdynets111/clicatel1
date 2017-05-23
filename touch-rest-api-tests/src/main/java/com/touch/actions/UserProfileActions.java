package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.user_profile.UserProfileRequest;
import com.touch.utils.StringUtils;
import io.restassured.http.Header;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/21/2016.
 */
public class UserProfileActions {
    com.touch.engines.RequestEngine requestEngine;

    public UserProfileActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public <T> T addUserProfile(Map<String, Object> data, File file, String token, Class<T> clazz) {
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, data, file, new Header("Authorization", token)).as(clazz);
    }
    public int addUserProfile(Map<String, Object> data, File file, String token) {
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, data, file, new Header("Authorization", token)).getStatusCode();
    }
    public int addDefaultUserProfile(String id, String token) {
        String name = "Test"+ StringUtils.generateRandomString(6);
        String surname = "surname"+StringUtils.generateRandomString(6);
        String email = StringUtils.generateRandomString(6)+"@gmail.com";
        String contactPhone = "07700 900039";
        String gender = "male";
        String birthdate = "19901231";
        String country = "Ukraine";
        String city = "Lviv";
        String address = "addressTest";
        String fileName = "tenant_logo.jpg";
        String file = getFullPathToFile("TenantResources/" + fileName);
        Map<String, Object> userProfileData = new HashMap<>();
        userProfileData.put("email",email);
        userProfileData.put("id",id);
        userProfileData.put("contactPhone",contactPhone);
        userProfileData.put("name",name);
        userProfileData.put("surname",surname);
        userProfileData.put("gender",gender);
        userProfileData.put("birthdate",birthdate);
        userProfileData.put("country",country);
        userProfileData.put("city",city);
        userProfileData.put("address",address);
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, userProfileData, new File(file), new Header("Authorization", token)).getStatusCode();
    }
    public int updateUserProfile(String profileId, UserProfileRequest userData, String token) {
        return requestEngine.putRequest(EndPointsClass.TOUCH_USER_PROFILE,profileId, null, userData, new Header("Authorization", token)).getStatusCode();
    }
    public <T> T getUserProfile(String profileId, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TOUCH_USER_PROFILE, profileId, new Header("Authorization", token)).as(clazz);
    }
    public <T> T getAllUserProfiles(String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TOUCH_USER_PROFILES_OLD, new Header("Authorization", token)).as(clazz);
    }
    public int deleteUserProfile(String userProfileId, String token){
        return  requestEngine.deleteRequest(EndPointsClass.TOUCH_USER_PROFILE, userProfileId,  new Header("Authorization", token)).getStatusCode();
    }
    public int updateUserProfileImage(String profileId, File file, String token) {
        return requestEngine.putFile(EndPointsClass.TOUCH_USER_PROFILE_IMAGE,profileId,file, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteUserProfileImage(String profileId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.TOUCH_USER_PROFILE_IMAGE,profileId, new Header("Authorization", token)).getStatusCode();
    }
    private String getFullPathToFile(String pathToFile) {
        return UserProfileActions.class.getClassLoader().getResource(pathToFile).getPath();
    }
}
