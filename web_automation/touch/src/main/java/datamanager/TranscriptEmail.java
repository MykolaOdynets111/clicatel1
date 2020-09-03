package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;

public enum TranscriptEmail {

    QA_TRANSCRIPT("standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "qa"),
    DEMO_TRANSCRIPT("aqamc2771@gmail.com", "p@$$w0rd4te$t", "demo"),
    DEV_TRANSCRIPT("updateplan@gmail.com", "p@$$w0rd4te$t", "dev"),
    INTEGRATION_TRANSCRIPT("touchdemotenant@gmail.com", "12345qwer", "integration"),
    TESTING_TRANSCRIPT("touchdemotenant@gmail.com", "12345qwer", "testing")
    ;

    String email;
    String emailPass;
    String env;

    TranscriptEmail(String email, String emailPass, String env) {
        this.email = email;
        this.emailPass = emailPass;
        this.env = env;
    }


    public String getEnv() {
        return this.env;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailPass() {
        return emailPass;
    }

    public static TranscriptEmail getMailByEnv(){
        return Arrays.stream(TranscriptEmail.values()).filter(e -> e.getEnv().equals(ConfigManager.getEnv()))
                .findFirst().get();
    }

}
