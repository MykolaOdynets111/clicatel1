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
import com.touch.models.mc2.AccountInfoResponse;
import com.touch.utils.MySQLConnector;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import io.restassured.http.Header;

import java.util.ArrayList;
import java.util.List;

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
        String email = "email" + StringUtils.generateRandomString(10) + "@sink.sendgrid.net";
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
    public String loginAsAdminUserAndReturnToken(){
        AuthActions authActions = new AuthActions(this.requestEngine);
        UserSignupRequest userSignupRequest = new UserSignupRequest("Clickatell", TestingEnvProperties.getPropertyByName("touch.user.admin.login"), null, null, TestingEnvProperties.getPropertyByName("touch.user.admin.password"), null);
        User user = new User(userSignupRequest);
        UserProfile userProfile = authActions.getListOfAccountsWithToken(user);
        int accountIndex =0;
        List<Account> accounts = userProfile.getAccounts();
        for(int i=0; i<accounts.size();i++){
            if(accounts.get(i).getName().equals("Clickatell")){
                accountIndex=i;
                break;
            }
        }
        return authActions.signInUser(new UserSignInRequest(userProfile.getToken(), userProfile.getAccounts().get(accountIndex).getId())).jsonPath().getString("token");
    }
    public String loginAsMC2AdminUserAndReturnToken(){
        AuthActions authActions = new AuthActions(this.requestEngine);
        UserSignupRequest userSignupRequest = new UserSignupRequest("Clickatell", TestingEnvProperties.getPropertyByName("mc2.user.admin.login"), null, null, TestingEnvProperties.getPropertyByName("mc2.user.admin.password"), null);
        User user = new User(userSignupRequest);
        UserProfile userProfile = authActions.getListOfAccountsWithToken(user);
        int accountIndex =0;
        List<Account> accounts = userProfile.getAccounts();
        for(int i=0; i<accounts.size();i++){
            if(accounts.get(i).getName().equals("Clickatell")){
                accountIndex=i;
                break;
            }
        }
        return authActions.signInUser(new UserSignInRequest(userProfile.getToken(), userProfile.getAccounts().get(accountIndex).getId())).jsonPath().getString("token");
    }
    public String signUpAndLoginWitthNewUser(String accountId, String accountName, String email, String firstName, String lastName, String password){
        UserSignupRequest userSignedUp = new UserSignupRequest(accountName, email, firstName, lastName, password, null);
        AuthActions authActions = new AuthActions(this.requestEngine);
        new AuthActions(this.requestEngine).activateAccount(
                MySQLConnector.getDbConnection()
                        .getAccountActivationId(accountId), MessageResponse.class);
        User user = new User(userSignedUp);
        UserProfile userProfile = authActions.getListOfAccountsWithToken(user);
        return authActions.signInUser(new UserSignInRequest(userProfile.getToken(), accountId)).jsonPath().getString("token");
    }
    public AccountInfoResponse getAccountInfo(String accountId, String token){
            return requestEngine.getRequest(com.clickatell.models.EndPointsClass.ADMIN_ACCOUNT,accountId, new Header("Authorization", token)).as(AccountInfoResponse.class);
    }
}
