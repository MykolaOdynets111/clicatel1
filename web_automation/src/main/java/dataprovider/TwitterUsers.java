package dataprovider;

public enum TwitterUsers {

    FIRST_USER("Tom", "Smith", "generalbankaqa@gmail.com", "p@$$w0rd4te$t", " @tomaqa0");

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String twitterUserName;

    TwitterUsers(String userName, String userSurname, String userEmail, String userPass, String userID) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.twitterUserName = userID;
    }

    private static TwitterUsers LOGGED_IN_USER = null;

    public static TwitterUsers getLoggedInUser() {
        return LOGGED_IN_USER;
    }

    public static void setLoggedInUser(TwitterUsers loggedInUser) {
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
        return this.twitterUserName;
    }
}
