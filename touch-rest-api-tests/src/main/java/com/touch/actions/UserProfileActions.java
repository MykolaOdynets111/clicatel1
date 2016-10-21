package com.touch.actions;

import com.touch.models.EndPointsClass;

/**
 * Created by kmakohoniuk on 9/21/2016.
 */
public class UserProfileActions {
    com.touch.engines.RequestEngine requestEngine;

    public UserProfileActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }
    public <T> T getAllUserProfiles(Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TOUCH_USER_PROFILE).as(clazz);
    }
    public int deleteUserProfile(String userProfileId){
        return  requestEngine.deleteRequest(EndPointsClass.TOUCH_USER_PROFILE, userProfileId).statusCode();
    }
}
