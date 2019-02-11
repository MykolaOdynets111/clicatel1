package dataManager;

public enum TwitterUsers {

    FIRST_USER("Tom", "Smith", "generalbankaqa@gmail.com", "p@$$w0rd4te$t", "@tomaqa0", "p@$$w0rd4te$t", "979311039996157952", "");

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String screenName;
    String userMailBoxPassword;
    String dmUserId;
    String tweetUserId;

   TwitterUsers(String userName, String userSurname, String userEmail, String userPass, String screenName, String userMailBoxPassword, String dmUserId, String tweetUserId) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.screenName = screenName;
        this.userMailBoxPassword = userMailBoxPassword;
        this.dmUserId = dmUserId;
        this.tweetUserId = tweetUserId;
    }

    private static TwitterUsers LOGGED_IN_USER = null;

    public static String getLoggedInUserName() {
        return LOGGED_IN_USER.userName+" "+LOGGED_IN_USER.userSurname;
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
}
