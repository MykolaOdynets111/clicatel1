package driverfactory;

import drivermanager.ConfigManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UnityURLs {

    private static String BASE_UNITY_URL = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/";
    private static String UNITY_LOGIN_FORM = "https://%s-mc2-app-foxtrot.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/login";
    private static String unityURl = "https://demo-platform.clickatelllabs.com";
    public static String JWT_ACCOUNT = unityURl + "/auth/accounts/sign-in";
    public static String AUTH_ACCOUNTS = unityURl + "/auth/accounts";

    public static String getUnityLoginForm() {
        return String.format(UNITY_LOGIN_FORM, ConfigManager.getEnv());
    }
}