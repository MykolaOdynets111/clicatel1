package driverfactory;

import drivermanager.ConfigManager;


public class UnityURLs {


    // ================== API BASE URLs ========================= //

    private static String BASE_CHATHUB_API_URL = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/";
    private static String CHATHUB_LOGIN_FORM = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/login";


    public static String getUnityLoginForm(){

        return String.format(CHATHUB_LOGIN_FORM, ConfigManager.getEnv());
    }

}

