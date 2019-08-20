package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum ExistedAccount {

    TESTING_BILLING("aqa_veniam789", "Greenfelder, Wiegand and Feil","aqatest_1566304859863@gmail.com", "p@$$w0rd4te$t", "testing", "Donald Spinka", "Donald", "Spinka", "ff8080816c952f26016caf0b82c60305", "starter"),
    DEMO_BILLING("aqa_officia562", "Marvin Inc", "aqatest_1565877071564@gmail.com", "p@$$w0rd4te$t", "demo", "Cristin Bode", "Cristin", "Bode", "ff8080816caab0fb016caf1758e00010", "starter"),
    QA_BILLING("aqa_molestiae796", "Quigley, Lubowitz and Wiegand", "aqatest_1566305636483@gmail.com", "p@$$w0rd4te$t", "qa", "Jorge Kertzmann", "Jorge", "Kertzmann", "ff8080816caab0fb016caf1758e00010", "starter"),

    TESTING_PAYMENT("aqa_nemo943", "The best bank","aqatest_1565876438838@gmail.com", "p@$$w0rd4te$t", "testing", "Twanda Hettinger", "Twanda", "Hettinger", "ff8080816c952f26016c958268140074", "starter"),
    DEMO_PAYMENT("aqa_officia562", "Marvin Inc", "aqatest_1565877071564@gmail.com", "p@$$w0rd4te$t", "demo", "Cristin Bode", "Cristin", "Bode", "ff8080816c8b5274016c958bf6bf02fe", "starter"),
    QA_PAYMENT("aqa_corporis032", "Schiller, Prohaska and Nikolaus", "aqatest_1565853411462@gmail.com", "p@$$w0rd4te$t", "qa", "Adolfo Rowe", "Adolfo", "Rowe", "ff8080816c912129016c9422f3cb0014", "starter"),


    TESTING_EMAIL("aqa_tempora107", "","automationmc2+1565961000111@gmail.com", "p@$$w0rd4te$t", "testing", "Sudie Lakin", "Sudie", "Lakin", "", "starter"),

    ;

    String accountName;
    String companyName;
    String email;
    String pass;
    String env;
    String ownerName;
    String firstName;
    String lastName;
    String accountID;
    String touchGoPlan;

    ExistedAccount(String accountName, String companyName, String email, String pass, String env, String ownerName, String firstName, String lastName, String accountID, String touchGoPlan) {

        this.accountName = accountName;
        this.companyName = companyName;
        this.email = email;
        this.pass = pass;
        this.env = env;
        this.ownerName = ownerName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountID = accountID;
        this.touchGoPlan = touchGoPlan;

    }

    public static ExistedAccount getExistedAccountForPayments() {
        ExistedAccount[] agentsArray = ExistedAccount.values();
        List<ExistedAccount> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                & e.name().toLowerCase().contains("payment"))
                .findFirst().get();
    }

    public static ExistedAccount getExistedAccountForBilling() {
        ExistedAccount[] agentsArray = ExistedAccount.values();
        List<ExistedAccount> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        & e.name().toLowerCase().contains("billing"))
                .findFirst().get();
    }

    public static ExistedAccount getExistedAccountWithValidMail() {
        ExistedAccount[] agentsArray = ExistedAccount.values();
        List<ExistedAccount> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv()))
                .findFirst().get();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getTouchGoPlan() {
        return touchGoPlan;
    }

    public void setTouchGoPlan(String touchGoPlan) {
        this.touchGoPlan = touchGoPlan;
    }
}
