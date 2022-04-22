package socialaccounts;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FacebookUsers {

    // ======== Working LIVE Facebook accounts  ========= //

    TOM_SMITH("Tom", "Smith", "generalbankaqa@gmail.com", "1912835872122481", "password22", ""),
    FIRST_USER("Yahor", "Click", "yahor.click@gmail.com", "", "6740582W@", ""),

    USER_FOR_INTEGRATION("Aqa", "TestPages", "generabanksecondagent@gmail.com", "", "p@$$w0rd4te$t", ""),
//    ACCOUNT_WITH_QA_GENBANK_PAGE("Generalbank", "Demo", "generalbankdemo@gmail.com","T0uch!d3m0", "")
    ACCOUNT_WITH_DEV_GENBANK_PAGE("Tom", "Black", "tenantagentmode@gmail.com", "", "p@$$w0rd4te$t", ""),

    /** ===== Working TEST Facebook accounts created per environment ===== *
     NOTE: test pages can see only TEST pages and users. It's impossible to send message to live FB page*/

    // admins of the created test pages
//    DEV_GB_ADMIN("","","william_xqdblbu_sharpeescu@tfbnw.net","Qwerty123=",""),
//    INTEGRATION_GB_ADMIN("", "", "edqomdsulr_1563535393@tfbnw.net", "Qwerty123=", ""),
//    DEMO_GB_ADMIN("","","samantha_shfhuzu_seligsteinescu@tfbnw.net","Qwerty123=",""),
//    QA_GB_ADMIN("","","general_fpfrybo_qa@tfbnw.net","Qwerty123=",""),
//    TESTING_GB_ADMIN("","","jackson_iazeibm_chengstein@tfbnw.net","Qwerty123=",""),
//
//    DEV_AUTOMATION_ADMIN("","","bob_wghhhhq_greenesen@tfbnw.net","Qwerty123=",""),
//    INTEGRATION_AUTOMATION_ADMIN("", "", "william_slgguem_moiduberg@tfbnw.net", "Qwerty123=", ""),
//    DEMO_AUTOMATION_ADMIN("","","jackson_vovrvxg_putnamberg@tfbnw.net","Qwerty123=",""),
//    QA_AUTOMATION_ADMIN("","","jackson_ocmqjqq_sharpesky@tfbnw.net","Qwerty123=",""),
//    TESTING_AUTOMATION_ADMIN("","","ullrich_shvqdbd_zamoresky@tfbnw.net","Qwerty123=",""),

    DEV_TEST_USER1("Elizabeth","Sidhustein","elizabeth_oydovzf_sidhustein@tfbnw.net","Qwerty123=","2288205414630822", "dev"),
    INTEGRATION_TEST_USER1("Karen", "Brownescu", "karen_kppmjfc_brownescu@tfbnw.net", "Qwerty123=", "2349160228506502", "integration"),
    DEMO_TEST_USER1("Will","Yangson","will_oavozvz_yangson@tfbnw.net","Qwerty123=","2293724664075345", "demo"),
    QA_TEST_USER1("Joshua", "Sidhuwitz", "joshua_gitkhzl_sidhuwitz@tfbnw.net", "Qwerty123=", "3016265601747293", "qa"),
    TESTING_TEST_USER1("Helen","Fallersen","helen_grwqpit_fallersen@tfbnw.net","Qwerty123=","", "testing"),

    // additional test users
//    DEV_TEST_USER2("Donna", "Sadanstein","donna_nvmipwq_sadanstein@tfbnw.net", "Qwerty123=", "2469407299784371", "dev"),
//    INTEGRATION_TEST_USER2("Joshua","Thurnwitz", "joshua_eeocrzz_thurnwitz@tfbnw.net", "Qwerty123=", "2559593924085583", "integration"),
//    DEMO_TEST_USER2("Matthew","Vijayvergiyaberg","matthew_tkexbwd_vijayvergiyaberg@tfbnw.net","Qwerty123=","2390818367619976", "demo"),
//    QA_TEST_USER2("Jennifer","Romansky","jennifer_zzthmkp_romansky@tfbnw.net","Qwerty123=","2340551252658542", "qa"),
//    TESTING_TEST_USER2("Susan","Lisky","susan_erygprx_lisky@tfbnw.net","Qwerty123=","", "testing"),

    ;

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String userIDMsg;
    String userEnv;

    FacebookUsers(String userName, String userSurname, String userEmail, String userPass, String userIDMsg, String userEnv) {
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
        this.userIDMsg = userIDMsg;
        this.userEnv = userEnv;
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

    public Long getFBUserIDMsg() {
        return Long.valueOf(this.userIDMsg);
    }

    public String getFBUSerEnv(){
        return this.userEnv;
    }

    public static String getLoggedInUserName(){
        return LOGGED_IN_USER.getFBUserName() + " " + LOGGED_IN_USER.getFBUserSurname();
    }

    public static FacebookUsers getFBTestUserFromCurrentEnv() {
        FacebookUsers[] fbUsersArray = FacebookUsers.values();
        List<FacebookUsers> fbUsersList = Arrays.asList(fbUsersArray);
        return fbUsersList.stream()
                .filter(e -> e.getFBUSerEnv().equalsIgnoreCase(ConfigManager.getEnv()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot get facebook user for on " + ConfigManager.getEnv() + " env"));
    }

}
