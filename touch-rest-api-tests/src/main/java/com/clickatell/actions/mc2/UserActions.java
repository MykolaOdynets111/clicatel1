package com.clickatell.actions.mc2;

import com.clickatell.engines.RequestEngineMC2;
import com.clickatell.engines.RequestEngineMC2;
import com.clickatell.models.EndPointsClass;
import com.clickatell.models.ErrorMessage;
import com.clickatell.models.MessageResponse;
import com.clickatell.models.mc2.accounts.Account;
import com.clickatell.models.mc2.invitations.request.UpdateUserRequest;
import com.clickatell.models.mc2.roles.response.Permission;
import com.clickatell.models.mc2.roles.response.allroles.AllRolesResponse;
import com.clickatell.models.mc2.roles.response.allroles.Role;
import com.clickatell.models.mc2.users.request.UpdateUserMetadataRequest;
import com.clickatell.models.mc2.users.request.invitations.UserSignupInvitationRequest;
import com.clickatell.models.mc2.users.request.newuser.UserSignupRequest;
import com.clickatell.models.mc2.users.request.sign_in.UserSignInRequest;
import com.clickatell.models.mc2.users.response.getallusers.User;
import com.clickatell.models.mc2.users.response.getallusers.UsersClass;
import com.clickatell.models.mc2.users.response.my_permissions.PerrmissionsResponse;
import com.clickatell.models.mc2.users.response.newuser.UserSignupResponse;
import com.clickatell.models.mc2.users.response.profile.UserProfile;
import com.clickatell.models.mc2.users.response.user_cvs.UserCSVResponse;
import com.clickatell.utils.ApplicationProperties;
import com.clickatell.utils.MySQLConnector;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by sbryt on 6/14/2016.
 */
public class UserActions {
    RequestEngineMC2 requestEngine;

    /**
     * Get request for getting user permissions list
     *
     * @param user the {@link User} object
     * @return the user permissions list
     */
    public List<String> getUserPermissionsShort(User user) {
        return requestEngine.getRequest(EndPointsClass.USER_PERMISSION, user.getId()).as(PerrmissionsResponse.class).getPermissions();
    }

    public enum UserStatus {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE"),
        INVITE_PENDING("INVITE_PENDING");

        private String value;

        UserStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * Constructor for initialising requestEngine variable.
     *
     * @param requestEngine the {@link RequestEngineMC2} variable
     */
    public UserActions(RequestEngineMC2 requestEngine) {
        this.requestEngine = requestEngine;
    }

    /**
     * Post request for creating new user
     *
     * @param userSignupRequest the body request
     * @return the {@link UserSignupResponse} object
     */
    public UserSignupResponse createNewUser(UserSignupRequest userSignupRequest) {
        return requestEngine.postRequest(EndPointsClass.AUTH_SIGN_UP, userSignupRequest).as(UserSignupResponse.class);
    }

    /**
     * Method for logging in the system with default user
     */
    public void loginWithDefaultUser() {
        User user = new User();
        loginWithUser(user);
    }

    /**
     * Method for creating new user and login in the system
     *
     * @return the {@link User} object
     */
    public User createUserAndLogin() {
        UserSignupRequest userSignupRequest = new UserSignupRequest();
        UserSignupResponse userSignupResponse = createNewUser(userSignupRequest);
        User user = new User(userSignupRequest);
        user.setId(userSignupResponse.getUserId());
        user.setMainAccount(new Account(userSignupResponse.getAccountId(), userSignupRequest.getAccountName(), true));
        if (loginWithUser(user)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * Method for logging in the system with some user
     *
     * @param user        the user for logging
     * @param accountName the account name
     * @return boolean value true if user logged in the system
     */
    public boolean loginWithUser(User user, String... accountName) {
        LoginActions loginActions = new LoginActions(this.requestEngine);
        return accountName.length > 0 ? loginActions.loginToAccount(user, accountName[0]) : loginActions.loginToAccount(user);
    }

    /**
     * Get method for getting user by id
     *
     * @param userId the user id
     * @return the {@link User} object
     */
    public User getUserById(String userId) {
        Predicate<User> predicate = (p) -> (p.getId().equals(userId));
        return requestEngine.getRequest(EndPointsClass.USERS, UsersClass.class).getUsers()
                .stream()
                .filter(predicate)
                .findFirst().get();
    }

    /**
     * Get method for getting user by email
     *
     * @param email the user email
     * @return the {@link User} object
     */
    public User getUserByEmail(String email) {
        Predicate<User> predicate = (p) -> (p.getEmail().equals(email));
        return requestEngine.getRequest(EndPointsClass.USERS, UsersClass.class).getUsers()
                .stream()
                .filter(predicate)
                .findFirst().get();
    }

    /**
     * Method for getting current account id
     *
     * @param accountName the account name
     * @return the account id
     */
    public String getCurrentAccountId(String accountName) {
        return new MySQLConnector().getAccountIdByName(accountName);
    }

    /**
     * Method for getting data about current account
     *
     * @return the {@link Account} object
     */
    public Account getCurrentAccount() {
        String accountName = getCurrentAccountName();
        String accountId = getCurrentAccountId(accountName);
        return new Account(accountId, accountName);
    }

    /**
     * Methood for getting current account name
     *
     * @return the current account name
     */
    private String getCurrentAccountName() {
        return requestEngine.getRequest(EndPointsClass.USERS, UsersClass.class).getAccountName();
    }

    /**
     * Method for verifying if user exists in the system
     *
     * @param user the {@link User} object
     * @return boolean value true if user exists in the system
     */
    public boolean isUserExists(User user) {
        return getUserByEmail(user.getEmail()) != null;
    }

    /**
     * Method for accepting invitation
     *
     * @param email the email name
     * @return the {@link User} object
     */
    public User acceptInvitation(String email) {
        MySQLConnector mySQLConnector = new MySQLConnector();
        String invitationId = mySQLConnector.getInvitationIdBy(email, getCurrentAccount().getId());
        UserSignupInvitationRequest signupInvitationRequest = new UserSignupInvitationRequest(ApplicationProperties.getInstance().getPropertyByName("user.default.password"));
        requestEngine.postRequest(EndPointsClass.INVITATIONS_SIGN_UP, invitationId, signupInvitationRequest);
        User user = getUserByEmail(email).setPassword(signupInvitationRequest.getPassword());
        if (user.getMainAccount() == null) {
            user.setMainAccount(getCurrentAccount());
        } else {
            user.addAdditionalAccount(getCurrentAccount());
        }
        return user;
    }

    /**
     * Get request for getting all user in current account
     *
     * @return list of {@link User} objects
     */
    public List<User> getAllUsersOfCurrentAccount() {
        UsersClass usersClass = requestEngine.getRequest(EndPointsClass.USERS, UsersClass.class);
        return usersClass.getUsers();
    }

    /**
     * Method for accepting invitation by existing user
     *
     * @param user the {@link User} object
     */
    public void acceptInvitationByExistingUser(User user) {
        MySQLConnector mySQLConnector = new MySQLConnector();
        String invitationId = mySQLConnector.getInvitationIdBy(user.getEmail(), getCurrentAccount().getId());
        UserSignupInvitationRequest signupInvitationRequest = new UserSignupInvitationRequest(user.getPassword());
        requestEngine.postRequest(EndPointsClass.INVITATIONS_SIGN_UP, invitationId, signupInvitationRequest);
        if (user.getMainAccount() == null) {
            user.setMainAccount(getCurrentAccount());
        } else {
            user.addAdditionalAccount(getCurrentAccount());
        }
    }

    /**
     * Post request for creating new user and new account
     *
     * @param userSignupRequest the body request
     * @return the {@link Response} object
     */
    public Response createAccount(UserSignupRequest userSignupRequest) {
        return requestEngine.postRequest(EndPointsClass.AUTH_SIGN_UP, userSignupRequest);
    }

    /**
     * Post request for signing user
     *
     * @param userSignInRequest the body request
     * @return the {@link Response} object
     */
    public Response signInUser(UserSignInRequest userSignInRequest) {
        return requestEngine.postRequest(EndPointsClass.AUTH_ACCOUNT_SIGN_IN, userSignInRequest);
    }

    /**
     * Get request for getting roles for user
     *
     * @param user    the {@link User} object
     * @param details boolean value if true getting user details
     * @return the list of {@link Role} objects
     */
    public List<Role> getRolesOfUser(User user, boolean details) {
        return requestEngine.getRequest(EndPointsClass.USER_ROLES + "?details=" + details, user.getId()).as(AllRolesResponse.class).getRoles();
    }

    /**
     * Post request for adding role to user
     *
     * @param user    the {@link User} object
     * @param newRole the {@link Role} object
     */
    public void addNewRoleToUser(User user, Role newRole) {
        requestEngine.postRequest(EndPointsClass.USER_ROLE, user.getId(), newRole.getId(), new Header("solution", "PLATFORM"));
    }

    /**
     * Delete request for removing role from user
     *
     * @param userId the user id
     * @param roleId the role id
     * @return the {@link Response} object
     */
    public Response removeRoleFromUser(String userId, String roleId) {
        return requestEngine.deleteRequest(EndPointsClass.USER_ROLE, userId, roleId);
    }

    /**
     * Post request for converting user list from CSV file to JSON
     *
     * @param firstRowHeader boolean value true if the first row is header
     * @param fieldSeparator the field separator
     * @param csvFile        the CSV file
     * @param clazz          returned generic class
     * @param <T>            type of returned generic class
     * @return the result of removing permission from role
     * @see UserCSVResponse class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T createUsersFromCSVfile(Boolean firstRowHeader, String fieldSeparator, File csvFile, Class<T> clazz) {
        return requestEngine.postRequestWithMultiPart(EndPointsClass.INVITATIONS_CSV, firstRowHeader, fieldSeparator, csvFile).as(clazz);
    }

    /**
     * Delete request for deleting user
     *
     * @param invitedUser   the invited {@link User}
     * @param returnedClass returned generic class
     * @param <T>           type of returned generic class
     * @return the result of removing permission from role
     * @see MessageResponse class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    public <T> T deleteUser(User invitedUser, Class<T> returnedClass) {
        return requestEngine.deleteRequest(EndPointsClass.USER, invitedUser.getId()).as(returnedClass);
    }

    /**
     * Method for getting current user permissions list
     *
     * @return the permissions list
     */
    public List<String> getCurrentUserPermissionsShort() {
        return getCurrentUserPermissions(false, String.class);
    }

    /**
     * Method for getting current user permissions detailed
     *
     * @return the list of {@link Permission}
     */
    public List<Permission> getCurrentUserPermissionsDetailed() {
        return getCurrentUserPermissions(true, Permission.class);
    }

    /**
     * Get request for getting list of my permissions
     *
     * @param detailed boolean value if true getting user details
     * @param tClass   returned generic class
     * @param <T>      type of returned generic class
     * @return the result of sending message via integration
     * @see Permission class in valid cases
     * @see ErrorMessage class in invalid cases
     */
    private <T> List<T> getCurrentUserPermissions(boolean detailed, Class<T> tClass) {
        Response response = requestEngine.getRequest(EndPointsClass.USERS_PERMISSIONS + "?details=" + detailed);
        List<T> arrayTList = new ArrayList<>();
        arrayTList.addAll(response.getBody().jsonPath().getList("permissions", tClass));
        return arrayTList;

    }

    /**
     * Get request for getting details ot the current user
     *
     * @return the {@link User} object
     */
    public User getCurrentUserProfile() {
        return requestEngine.getRequest(EndPointsClass.USERS_PROFILE).as(User.class);
    }


    public UserProfile getUserProfile(String userId, Boolean roleDetails, Boolean permissionDetails) {
        return requestEngine.getRequest(EndPointsClass.USER_PROFILE
                + "?roleDetails=" + roleDetails
                + "&permissionDetails=" + permissionDetails, userId).as(UserProfile.class);
    }

    public UserProfile getUserProfile(String userId) {
        return getUserProfile(userId, true, true);
    }


    /**
     * Put request for updating current user profile
     *
     * @param updateUserMetadataRequest the request body
     * @return the {@link User} object
     */
    public User updateCurrentUserProfile(UpdateUserMetadataRequest updateUserMetadataRequest) {
        requestEngine.putRequest(EndPointsClass.USERS_PROFILE, updateUserMetadataRequest);
        return getCurrentUserProfile();
    }

    /**
     * Put request for updating users profile
     *
     * @param id                the path parameter
     * @param updateUserRequest the request body
     * @return the {@link User} object
     */
    public User updateUserProfile(String id, UpdateUserRequest updateUserRequest) {
        requestEngine.putRequest(EndPointsClass.USER_PROFILE, id, updateUserRequest);
        return getUserById(id);
    }

    /**
     * Put request for updating users profile
     *
     * @param id                the path parameter
     * @param updateUserRequest the request body
     * @param withError         boolean variable
     * @return the array of {@link ErrorMessage}
     */
    public ErrorMessage[] updateUserProfile(String id, UpdateUserRequest updateUserRequest, boolean withError) {
        Response response = requestEngine.putRequest(EndPointsClass.USER_PROFILE, id, updateUserRequest);
        try {
            return response.as(ErrorMessage[].class);
        } catch (Exception e) {
            return new ErrorMessage[]{response.as(ErrorMessage.class)};
        }
    }

    /**
     * Put request for activating/deactivating user.
     *
     * @param user   the {@link User} object
     * @param active the request body
     * @return the {@link User} object
     */
    public User setUserActivityStatus(User user, UserStatus active) {
        requestEngine.putRequest(EndPointsClass.USER_STATUS, user.getId(), "{\"active\": " + active.equals(UserStatus.ACTIVE) + "}");
        return getUserById(user.getId());
    }

    /**
     * Put request for activating/deactivating user.
     *
     * @param user   the {@link User} object
     * @param active the request body
     * @return the {@link MessageResponse} object
     */
    public MessageResponse setUserActivityStatusAngGetMessage(User user, UserStatus active) {
        return requestEngine.putRequest(EndPointsClass.USER_STATUS, user.getId(), "{\"active\": " + active.equals(UserStatus.ACTIVE) + "}");
    }


}