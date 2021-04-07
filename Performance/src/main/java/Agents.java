public enum Agents {

    AGENT_1("performance1@aqa.com", "welcome1!@"),
    AGENT_2("performance2@aqa.com", "welcome1!@"),
    AGENT_3("performance3@aqa.com", "welcome1!@"),
    AGENT_4("performance4@aqa.com", "welcome1!@"),
    AGENT_5("performance5@aqa.com", "welcome1!@"),
    AGENT_6("performance6@aqa.com", "welcome1!@"),
    AGENT_7("performance7@aqa.com", "welcome1!@"),
//    AGENT_8("performance8@aqa.com", "welcome1!@"),
//    AGENT_9("performance9@aqa.com", "welcome1!@"),
//    AGENT_10("performance10@aqa.com", "welcome1!@"),
//    AGENT_11("performance11@aqa.com", "welcome1!@"),
//    AGENT_12("performance12@aqa.com", "welcome1!@"),
//    AGENT_13("performance13@aqa.com", "welcome1!@"),
//    AGENT_14("performance14@aqa.com", "welcome1!@"),
//    AGENT_15("performance15@aqa.com", "welcome1!@"),
//    AGENT_16("performance16@aqa.com", "welcome1!@"),
//    AGENT_17("performance17@aqa.com", "welcome1!@"),
//    AGENT_18("performance18@aqa.com", "welcome1!@"),
//    AGENT_19("performance19@aqa.com", "welcome1!@"),
    AGENT_20("performance20@aqa.com", "welcome1!@");


    String email;
    String userPass;
    String name;

    Agents(String email, String userPass) {
        this.email = email;
        this.userPass = userPass;
    }

    public String getEmail() {
        return email;
    }

    public String getUserPass() {
        return userPass;
    }

}
