package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.user_profile.UserProfileRequest;
import com.touch.utils.StringUtils;

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

    public <T> T addUserProfile(Map<String, Object> data, File file, Class<T> clazz) {
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, data, file).as(clazz);
    }
    public int addUserProfile(Map<String, Object> data, File file) {
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, data, file).getStatusCode();
    }
    public int addDefaultUserProfile(String id) {
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
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.TOUCH_USER_PROFILES, null, userProfileData, new File(file)).getStatusCode();
    }
    public int updateUserProfile(String profileId, UserProfileRequest userData) {
        return requestEngine.putRequest(EndPointsClass.TOUCH_USER_PROFILE,profileId, userData).getStatusCode();
    }
    public <T> T getUserProfile(String profileId, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TOUCH_USER_PROFILE, profileId).as(clazz);
    }
    public <T> T getAllUserProfiles(Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TOUCH_USER_PROFILES).as(clazz);
    }
    public int deleteUserProfile(String userProfileId){
        return  requestEngine.deleteRequest(EndPointsClass.TOUCH_USER_PROFILE, userProfileId).getStatusCode();
    }
    public int updateUserProfileImage(String profileId, File file) {
        return requestEngine.putFile(EndPointsClass.TOUCH_USER_PROFILE_IMAGE,profileId,file).getStatusCode();
    }
    public int deleteUserProfileImage(String profileId) {
        return requestEngine.deleteRequest(EndPointsClass.TOUCH_USER_PROFILE_IMAGE,profileId).getStatusCode();
    }
    private String getFullPathToFile(String pathToFile) {
        return UserProfileActions.class.getClassLoader().getResource(pathToFile).getPath();
    }
}
