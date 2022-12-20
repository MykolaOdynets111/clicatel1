package driverfactory;

import drivermanager.ConfigManager;


public class UnityURLs {


    // ================== BASE UNITY URLs ========================= //

    private static String BASE_UNITY_URL = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/";
    private static String UNITY_LOGIN_FORM = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/login";


    public static String getUnityLoginForm(){

        return String.format(UNITY_LOGIN_FORM, ConfigManager.getEnv());
    }

}

