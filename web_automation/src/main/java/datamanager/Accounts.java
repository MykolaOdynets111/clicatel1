package datamanager;

import drivermanager.ConfigManager;

public class Accounts {

    public static String getCorrectAccountName(String tenantOrgName) {
        String accountName;
        if (tenantOrgName.equalsIgnoreCase("general bank demo")) {
            if (ConfigManager.getEnv().equals("dev")) {
                accountName = "generalbank";
            } else {
                accountName = "generalbankdemo";
            }
            return accountName;
        } else{
           return MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName).getAccountName();
        }
    }
}
