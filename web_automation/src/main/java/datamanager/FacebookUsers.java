package datamanager;

import apihelper.ApiHelper;
import drivermanager.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public enum FacebookUsers {

    TOM_SMITH("Tom", "Smith", "generalbankaqa@gmail.com", "100024956568638", "password22"),
//    FIRST_USER("Aqa", "TestPages", "generabanksecondagent@gmail.com", "", "p@$$w0rd4te$t"),//acc not available
    FIRST_USER("Yahor", "Click", "yahor.click@gmail.com", "", "Passw0rd@"),

    USER_FOR_INTEGRATION("Aqa", "TestPages", "generabanksecondagent@gmail.com", "", "p@$$w0rd4te$t"),
//    ACCOUNT_WITH_QA_GENBANK_PAGE("Generalbank", "Demo", "generalbankdemo@gmail.com","T0uch!d3m0", "")
    ACCOUNT_WITH_DEV_GENBANK_PAGE("Tom", "Black", "tenantagentmode@gmail.com", "", "p@$$w0rd4te$t"),

    ;

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String userID;

    FacebookUsers(String userName, String userSurname, String userEmail, String userID, String userPass) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        if(userPass.equals("")){
            if(ConfigManager.getFBUserPass()==null){
                this.userPass = "newpassw0rd";
            } else{
                this.userPass = ConfigManager.getFBUserPass();
            }
        }else {
            this.userPass = userPass;
        }
        this.userID = userID;
    }

    public static final Map<String, Long> userIds = new HashMap<String, Long>() {{
        put(TOM_SMITH.userEmail + "_fbmsg", 1912835872122481l);
    }};


    private static FacebookUsers LOGGED_IN_USER = null;

    public static FacebookUsers getLoggedInUser() {
        return LOGGED_IN_USER;
    }

    public static void setLoggedInUser(FacebookUsers loggedInUser) {
        LOGGED_IN_USER = loggedInUser;
    }

    public String getFBUserPass() {
        return this.userPass;
    }

    public String getFBUserName() {
        return this.userName;
    }

    public String getFBUserSurname() {
        return this.userSurname;
    }

    public String getFBUserEmail() {
        return this.userEmail;
    }

    public String getFBUserID() {
        return this.userID;
    }

    public static String getLoggedInUserName(){
//        String clientId = FacebookUsers.getLoggedInUser().getFBUserID();
//        return  ApiHelper.getCustomer360PersonalInfo(Tenants.getTenantUnderTestOrgName(),
//            clientId, "FACEBOOK").getFullName();
        return LOGGED_IN_USER.getFBUserName() + " " + LOGGED_IN_USER.getFBUserSurname();
    }

}
