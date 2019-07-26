package testflo;

import drivermanager.ConfigManager;

public class JiraUser {

    public static final String USER_EMAIL = ConfigManager.getJiraUser();

    public static final String USER_PASS = ConfigManager.getJiraPass();


}
