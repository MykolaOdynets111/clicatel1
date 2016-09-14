package com.clickatell.actions.mc2;

import com.clickatell.engines.RequestEngineMC2;
import com.clickatell.models.ErrorMessage;
import com.clickatell.models.mc2.accounts.Account;
import com.clickatell.models.mc2.user_profiles.UserProfile;
import com.clickatell.models.mc2.users.request.sign_in.UserSignInRequest;
import com.clickatell.models.mc2.users.response.getallusers.User;
import com.clickatell.utils.ConfigApp;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import java.util.function.Predicate;

/**
 * Created by oshchur on 12.07.2016.
 */
public class LoginActions {
    RequestEngineMC2 requestEngineMC2;

    public static String currentToken;

    /**
     * Constructor for initialising requestEngineMC2 variable.
     *
     * @param requestEngineMC2 the {@link RequestEngineMC2} variable
     */
    public LoginActions(RequestEngineMC2 requestEngineMC2) {
        this.requestEngineMC2 = requestEngineMC2;
    }

    /**
     * Method for logging to some account
     *
     * @param user        the {@link User} object
     * @param accountName the account name for logging
     * @return boolean value true if user logged in the system
     */
    public boolean loginToAccount(User user, String... accountName) {
        UserProfilesActions userProfilesActions = new UserProfilesActions(requestEngineMC2);
        UserProfile userProfile = userProfilesActions.getListOfAccountsWithToken(user);
        String accountId;
        if (accountName.length > 0) {
            Predicate<Account> predicate = p -> p.getName().equals(accountName[0]);
            accountId = userProfile.getAccounts().stream().filter(predicate).findFirst().get().getId();
        } else {
            accountId = userProfile.getAccounts().get(0).getId();
        }
        UserActions userActions = new UserActions(requestEngineMC2);
        currentToken = userActions.signInUser(new UserSignInRequest(userProfile.getToken(), accountId)).jsonPath().getString("token");
        requestEngineMC2.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", currentToken)
                .setBaseUri(ConfigApp.BASE_API_URL)
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
        return true;
    }

    /**
     * Method for unsuccessful logging in system
     *
     * @param userSignInRequest the request body
     * @param clazz             returned generic class
     * @param <T>               type of returned generic class
     * @return the {@link ErrorMessage} object
     */
    public <T> T unsuccessfulLogin(UserSignInRequest userSignInRequest, Class<T> clazz) {
        UserActions userActions = new UserActions(requestEngineMC2);
        return userActions.signInUser(userSignInRequest).as(clazz);
    }
}
