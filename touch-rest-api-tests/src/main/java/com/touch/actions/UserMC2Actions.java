package com.touch.actions;

import com.clickatell.actions.AuthActions;
import com.clickatell.engines.RequestEngine;
import com.clickatell.models.MessageResponse;
import com.clickatell.models.accounts.Account;
import com.clickatell.models.user_profiles.UserProfile;
import com.clickatell.models.users.request.newuser.UserSignupRequest;
import com.clickatell.models.users.request.sign_in.UserSignInRequest;
import com.clickatell.models.users.response.getallusers.User;
import com.clickatell.models.users.response.newuser.UserSignupResponse;
import com.clickatell.utils.MySQLConnector;
import com.touch.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by kmakohoniuk on 10/20/2016.
 */
public class UserMC2Actions extends com.clickatell.actions.UserActions {
    private RequestEngine requestEngine;
    public UserMC2Actions(RequestEngine requestEngine) {
        super(requestEngine);
        this.requestEngine =requestEngine;
    }
    public User createUserWithTouchPlatformAndLogin() {
        String accountName = "accountname_" + StringUtils.generateRandomString(10);
        String email = "email" + StringUtils.generateRandomString(10) + "@fake.perfectial.com";
        String firstName = "FirstName_" + StringUtils.generateRandomString(8);
        String lastName = "LastName_" + StringUtils.generateRandomString(8);
        String password = "12345678";
        List<String> solutions = new ArrayList();
        solutions.add("TOUCH");

        UserSignupRequest userSignupRequest = new UserSignupRequest();
        AuthActions authActions = new AuthActions(this.requestEngine);
        UserSignupResponse userSignupResponse = authActions.createNewUser(userSignupRequest);
        new AuthActions(this.requestEngine).activateAccount(
                MySQLConnector.getDbConnection()
                        .getAccountActivationId(userSignupResponse.getAccountId()), MessageResponse.class);
        User user = new User(userSignupRequest);
        user.setId(userSignupResponse.getUserId());
        user.setMainAccount(new Account(userSignupResponse.getAccountId(), userSignupRequest.getAccountName(), Boolean.valueOf(true)));
        return this.loginWithUser(user, new String[0])?user:null;
    }

    public UserProfile createNewUser(){
        String accountName = "accountname_" + StringUtils.generateRandomString(10);
        String email = "email" + StringUtils.generateRandomString(10) + "@fake.perfectial.com";
        String firstName = "FirstName_" + StringUtils.generateRandomString(8);
        String lastName = "LastName_" + StringUtils.generateRandomString(8);
        String password = "12345678";
        List<String> solutions = new ArrayList();
        solutions.add("TOUCH");

        UserSignupRequest userSignupRequest = new UserSignupRequest(accountName, email, firstName, lastName, password, solutions);
        AuthActions authActions = new AuthActions(this.requestEngine);
        UserSignupResponse userSignupResponse = authActions.createNewUser(userSignupRequest);
        new AuthActions(this.requestEngine).activateAccount(
                MySQLConnector.getDbConnection()
                        .getAccountActivationId(userSignupResponse.getAccountId()), MessageResponse.class);
        User user = new User(userSignupRequest);
        user.setId(userSignupResponse.getUserId());
        user.setMainAccount(new Account(userSignupResponse.getAccountId(), userSignupRequest.getAccountName(), Boolean.valueOf(true)));
        return authActions.getListOfAccountsWithToken(user);
    }

    public String loginWithNewUserAndReturnToken(UserProfile userProfile){
        String accountId = userProfile.getAccounts().get(0).getId();
        AuthActions authActions = new AuthActions(this.requestEngine);
        return authActions.signInUser(new UserSignInRequest(userProfile.getToken(), accountId)).jsonPath().getString("token");
    }
}
