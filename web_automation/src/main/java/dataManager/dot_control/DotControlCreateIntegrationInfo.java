package dataManager.dot_control;

import com.github.javafaker.Faker;

import java.util.Random;

public class DotControlCreateIntegrationInfo {

    private String name;
    private boolean enabled;
    private String apiToken;
    private String callBackURL;

    private Random r = new Random( System.currentTimeMillis() );


    public DotControlCreateIntegrationInfo(boolean enabled, String callBackURL) {
        this.name = generateRandomName();
        this.enabled = enabled;
        this.apiToken = generateRandomApiKey();
        this.callBackURL = callBackURL;
    }

    private String generateRandomName(){
        return "at_integration_" + ((1 + r.nextInt(2)) * 1000000 + r.nextInt(1000000));
    }

    private String generateRandomApiKey(){
        return "testing_" + ((1 + r.nextInt(2)) * 1000000 + r.nextInt(1000000));
    }
}

