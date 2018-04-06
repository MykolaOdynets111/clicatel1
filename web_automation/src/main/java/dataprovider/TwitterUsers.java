package dataprovider;

public enum TwitterUsers {

    FIRST_USER("Tom", "Smith", "generalbankaqa@gmail.com", "p@$$w0rd4te$t", "@tomaqa0", "p@$$w0rd4te$t");

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String screenName;
    String userMailBoxPassword;

   TwitterUsers(String userName, String userSurname, String userEmail, String userPass, String screenName, String userMailBoxPassword) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.screenName = screenName;
        this.userMailBoxPassword = userMailBoxPassword;
    }

    private static TwitterUsers LOGGED_IN_USER = null;

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
}
