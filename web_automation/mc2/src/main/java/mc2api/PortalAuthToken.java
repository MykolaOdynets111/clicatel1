package mc2api;

import datamanager.Accounts;
import datamanager.Agents;
import datamanager.MC2Account;
import drivermanager.ConfigManager;


public class PortalAuthToken {

    private static volatile ThreadLocal<String> PORTAL_USER_ACCESS_TOKEN = new ThreadLocal<>();
    private static volatile ThreadLocal<String> PORTAL_SECOND_USER_ACCESS_TOKEN = new ThreadLocal<>();

    public static String getAccessTokenForPortalUser(String tenantOrgName, String agent) {
        if(agent.toLowerCase().contains("second")){
            return getAccessTokenForSecondAgent(tenantOrgName);
        }else{
            return getAccessTokenForMainAgent(tenantOrgName);
        }
    }

    private static String getAccessTokenForMainAgent(String tenantOrgName){
        if(PORTAL_USER_ACCESS_TOKEN.get()==null) {
            Agents user = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName.toLowerCase(), "main");
            String accountName = Accounts.getCorrectAccountName(tenantOrgName);

            String token = PortalAuth.getMC2AuthToken(accountName, user.getAgentEmail(), user.getAgentPass());

            PORTAL_USER_ACCESS_TOKEN.set(token);
            return PORTAL_USER_ACCESS_TOKEN.get();
        }else{
            return PORTAL_USER_ACCESS_TOKEN.get();
        }
    }

    private static String getAccessTokenForSecondAgent(String tenantOrgName){
        if(PORTAL_SECOND_USER_ACCESS_TOKEN.get()==null) {
            Agents user = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName.toLowerCase(), "second agent");
            String accountName = Accounts.getCorrectAccountName(tenantOrgName);

            String token = PortalAuth.getMC2AuthToken(accountName, user.getAgentEmail(), user.getAgentPass());

            PORTAL_SECOND_USER_ACCESS_TOKEN.set(token);
            return PORTAL_SECOND_USER_ACCESS_TOKEN.get();
        }else{
            return PORTAL_SECOND_USER_ACCESS_TOKEN.get();
        }
    }

    public static String getAccessTokenForPortalUserByAccount(String accountName) {
        if (PORTAL_USER_ACCESS_TOKEN.get()==null) {
            MC2Account admin = MC2Account.getAccountDetailsByAccountName(ConfigManager.getEnv(), accountName);

            String token = PortalAuth.getMC2AuthToken(accountName, admin.getEmail(), admin.getPass());

            PORTAL_USER_ACCESS_TOKEN.set(token);
            return PORTAL_USER_ACCESS_TOKEN.get();
        } else {
            return PORTAL_USER_ACCESS_TOKEN.get();
        }
    }


    public static String getAccessTokenForPortalUser(String accountName, String email, String pass) {
        if (PORTAL_USER_ACCESS_TOKEN.get()==null) {
            String token = PortalAuth.getMC2AuthToken(accountName, email, pass);
            PORTAL_USER_ACCESS_TOKEN.set(token);
            return PORTAL_USER_ACCESS_TOKEN.get();
        } else {
            return PORTAL_USER_ACCESS_TOKEN.get();
        }
    }
    public static void clearAccessTokenForPortalUser(){
        PORTAL_USER_ACCESS_TOKEN.remove();
        PORTAL_SECOND_USER_ACCESS_TOKEN.remove();
    }


}
