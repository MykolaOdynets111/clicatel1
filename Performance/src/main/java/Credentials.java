public enum Credentials {

    FIRST_AGENT("touchdemotenant@gmail.com", "12345qwer", "Touch_Demo AQA"),
    SECOND_AGENT("generabanksecondagent@gmail.com","p@$$w0rd4te$t", "Second Agent");


    String email;
    String userPass;
    String name;

    Credentials(String email, String userPass, String name) {
        this.email = email;
        this.userPass = userPass;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getName() {
        return name;
    }

}
