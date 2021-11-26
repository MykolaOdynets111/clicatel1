public class URL {

    private static String env = "stage";
    public static String tenantId = "ff8080815fc3fa12015fcaa09d25000b";

    public static String agentLoginPage = "https://" + env + "-portal.clickatelllabs.com/#/login";
    public static String widgetUrl = "https://" + env + "-touch-web.clickatelllabs.com/?tenantId=" + tenantId + "";
    public static String agentDesk = "https://" + env + "-chatdesk.clickatelllabs.com/";
    public static String supervisorDesk = "https://" + env + "-chatdesk.clickatelllabs.com/supervisor";

    public static String webSocketAuth = "https://" + env + "-chatdesk-platform-app-bravo.int-eks-" + env + ".shared-" + env + ".eu-west-1.aws.clickatell.com/internal/static/auth-tool";
    public static String webSocketAgentDesk = "https://" + env + "-chatdesk-portal-bravo.int-eks-" + env + ".shared-" + env + ".eu-west-1.aws.clickatell.com";

}
