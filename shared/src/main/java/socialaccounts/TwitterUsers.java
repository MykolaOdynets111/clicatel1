package socialaccounts;


public enum TwitterUsers {

//    FIRST_USER("Click", "qa2", "click_qa2", "Qwerty123=", "@click_qa2", "", "1120976215819608064", "");
    FIRST_USER("Tom", "Smith", "generalbankaqa@gmail.com", "p@$$w0rd4te$t", "@tomaqa0", "p@$$w0rd4te$t", "979311039996157952", "", "+380 50 5083236"), //this user was unsuccessfully de-linked on qa env
    SECOND_USER("AQA", "Client", "yuri.shostakovskyi+1@gmail.com", "qwertyuiop1234567890", "@yuritets", "", "1098567655341150210", "", ""),
    THIRD_USER("Tom", "AQA", "standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "@aqa_tom", "", "1167458338445152256", "", "+380974070813"),

    TOUCHGO_USER("Tom", "", "", "", "@tomaqa0", "p@$$w0rd4te$t", "979311039996157952", "", ""),

    QA_PAGE_OWNER("Automation", "Owner", "startertgplan@gmail.com", "p@$$w0rd4te$t", "@AutomationOwner", "p@$$w0rd4te$t", "979311039996157952", "", "+380974070813");

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String screenName;
    String userMailBoxPassword;
    String dmUserId;
    String tweetUserId;
    String userPhone;

   TwitterUsers(String userName, String userSurname, String userEmail, String userPass, String screenName, String userMailBoxPassword, String dmUserId, String tweetUserId, String userPhone) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.screenName = screenName;
        this.userMailBoxPassword = userMailBoxPassword;
        this.dmUserId = dmUserId;
        this.tweetUserId = tweetUserId;
        this.userPhone = userPhone;
    }

    private static TwitterUsers LOGGED_IN_USER = null;

    public static String getLoggedInUserName() {
        return LOGGED_IN_USER.getTwitterUserName() + " " + LOGGED_IN_USER.getTwitterUserSurname();
    }
    public static TwitterUsers getLoggedInUser() {
        return LOGGED_IN_USER;
    }

    public static void setLoggedInUser(TwitterUsers loggedInUser) {
        LOGGED_IN_USER = loggedInUser;
    }

    public String getTwitterUserPass() {
        return this.userPass;
    }

    public String getTwitterUserName() {
        return this.userName;
    }

    public String getTwitterUserSurname() {
        return this.userSurname;
    }

    public String getTwitterUserEmail() {
        return this.userEmail;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public String getDmUserId() {
        return dmUserId;
    }

    public String getTweetUserId() {
        return tweetUserId;
    }

    public String getUserPhone() {return userPhone;}
}
