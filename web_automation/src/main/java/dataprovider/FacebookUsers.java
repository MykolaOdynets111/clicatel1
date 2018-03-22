package dataprovider;

public enum FacebookUsers {

    FIRST_USER("Tom", "Smith", "generalbankaqa@gmail.com", "p@$$w0rd4te$t", "100024956568638");

    String userName;
    String userSurname;
    String userEmail;
    String userPass;
    String userID;

    FacebookUsers(String userName, String userSurname, String userEmail, String userPass, String userID) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userID = userID;
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

    }
