package com.clickatell.actions.mc2;

import com.clickatell.engines.RequestEngineMC2;
import com.clickatell.models.EndPointsClass;
import com.clickatell.models.ErrorMessage;
import com.clickatell.models.MessageResponse;
import com.clickatell.models.mc2.user_profiles.UserProfile;
import com.clickatell.models.mc2.user_profiles.request.GetAccountsRequest;
import com.clickatell.models.mc2.user_profiles.request.SendPasswordResetEmailRequest;
import com.clickatell.models.mc2.users.response.getallusers.User;

/**
 * Created by oshchur on 12.07.2016.
 */
public class UserProfilesActions {
    RequestEngineMC2 requestEngineMC2;


    /**
     * Constructor for initialising requestEngineMC2 variable.
     *
     * @param requestEngineMC2 the {@link RequestEngineMC2} variable
     */
    public UserProfilesActions(RequestEngineMC2 requestEngineMC2) {
        this.requestEngineMC2 = requestEngineMC2;
    }

    /**
     * Post request for getting list of accounts with token
     *
     * @param user the {@link User} variable
     * @return the {@link UserProfile} object
     */
    public UserProfile getListOfAccountsWithToken(User user) {
        return requestEngineMC2.postRequest(EndPointsClass.AUTH_ACCOUNT, new GetAccountsRequest(user.getEmail(), user.getPassword())).as(UserProfile.class);
    }

    /**
     * Post request for getting list of accounts with token
     *
     * @param accountsRequest the GetAccountsRequest variable
     * @param clazz           returned generic class
     * @param <T>             type of returned generic class
     * @return the result of getting list of accounts with token
     * @see UserProfile class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T getListOfAccounts(GetAccountsRequest accountsRequest, Class<T> clazz) {
        return requestEngineMC2.postRequest(EndPointsClass.AUTH_ACCOUNT, accountsRequest).as(clazz);
    }

    /**
     * Post request for sending email for password reset
     *
     * @param sendPasswordResetEmailRequest the request body
     * @param clazz                         returned generic class
     * @param <T>                           type of returned generic class
     * @return the result of sending email for password reset
     * @see MessageResponse class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T resetPasswordEmail(SendPasswordResetEmailRequest sendPasswordResetEmailRequest, Class<T> clazz) {
        return requestEngineMC2.postRequest(EndPointsClass.USER_PROFILES_RESET_PASSWORD, sendPasswordResetEmailRequest).as(clazz);
    }

    /**
     * Put request for saving new password
     *
     * @param passwordResetId the path parameter
     * @param body            the request body
     * @param clazz           returned generic class
     * @param <T>             type of returned generic class
     * @return the result of saving new password
     * @see MessageResponse class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T saveNewPassword(String passwordResetId, Object body, Class<T> clazz) {
        return requestEngineMC2.putRequest(EndPointsClass.SAVE_NEW_PASSWORD, passwordResetId, body).as(clazz);
    }

    /**
     * Put request for updating user quota settings
     *
     * @param userMetadataId the path parameter
     * @param body           the request body
     * @param clazz          returned generic class
     * @param <T>            type of returned generic class
     * @return the result of updating user quota settings
     * @see MessageResponse class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T updateUserQuota(String userMetadataId, Object body, Class<T> clazz) {
        return requestEngineMC2.putRequest(EndPointsClass.ADMIN_USER_PROFILES_USER_QUOTA, userMetadataId, body).as(clazz);
    }
}
