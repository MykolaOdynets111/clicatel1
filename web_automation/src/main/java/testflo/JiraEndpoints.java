package testflo;

public class JiraEndpoints {

    private static final String JIRA_CONTEXT_URL = "https://jira.clickatell.com/rest/";


    // ======================  TestFLO endpoints ==================== //

    public static final String COPY_TEST_PLAN = JIRA_CONTEXT_URL + "tms/1.0/testplan/copy";

    public static final String SET_TESTCASE_STEP_STATUS = JIRA_CONTEXT_URL + "tms/1.0/steps/status";


    // ====================  General JIRA endpoints ================= //

    // Default wildcard is JIRA Issue key like TPORT-4013

    public static String GET_JIRA_ISSUE_AVAILABLE_TRANSITIONS = JIRA_CONTEXT_URL + "api/2/issue/%s/transitions";

    public static final String JIRA_ISSUE = JIRA_CONTEXT_URL + "api/2/issue";

    public static String MOVE_JIRA_ISSUE = JIRA_CONTEXT_URL + "api/2/issue/%s/transitions";
}
