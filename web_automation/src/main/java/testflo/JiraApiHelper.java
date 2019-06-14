package testflo;

import testflo.jacksonschemas.AllureScenarioInterface;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testflo.jacksonschemas.testplansubtasks.ExistedTestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JiraApiHelper {


    public static String copyTestPlan(String testPlan){
        return RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"testPlanKey\": \"" + testPlan + "\",\n" +
                        "  \"projects\": [\"TPORT\"],\n" +
                        "  \"copyStrategy\": \"BY_TEST_CASES\"\n" +
                        "}")
                .post(JiraEndpoints.COPY_TEST_PLAN)
                .jsonPath().get("testPlans.testPlanKey[0]");
    }

    public static void updateTestPlanSummary(String issueKey, String newSummary){
        RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{ \"fields\": {\"summary\": \"" + newSummary + "\"}}")
                .put(JiraEndpoints.JIRA_ISSUE + "/" + issueKey);
    }

    public static Map<String, String> createNewTestCase(AllureScenarioInterface scenario, String testPlanKey){
        String testTemplateId = scenario.getTestId().replace("https://jira.clickatell.com/browse/", "");
        String body = NewTCBodyGenerator.formBodyForNewTestCase(testPlanKey, testTemplateId, scenario);
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body(body)
                .post(JiraEndpoints.JIRA_ISSUE);
        resp.statusCode();
        return resp.jsonPath().getMap("");
    }

    public static List<ExistedTestCase> getExistedInTestPlanTestCases(String testPlanKey){
        return RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .get(JiraEndpoints.JIRA_ISSUE + "/" + testPlanKey)
                .getBody().jsonPath().getList("fields.subtasks", ExistedTestCase.class);
    }

    public static Map<String, String> createNewTestCaseTemplate(AllureScenarioInterface scenario){
        String body = NewTCTBodyGenerator.formBodyForNewTestCaseTemplate(scenario);
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body(body)
                .post(JiraEndpoints.JIRA_ISSUE);
        int statusCode = resp.statusCode();
        if(statusCode == 400 |
                statusCode == 500){
            Map<String, String> map = new HashMap<>();
            map.put("key", "TPORT-0000");
            map.put("tcName", scenario.getName());
            map.put("body", body);
            return  map;
        } else {
            return resp.jsonPath().getMap("");
        }
    }

    public static void changeTestCaseStatus(String tcKey, String transitionId){
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"transition\": {\n" +
                        "        \"id\": \"" + transitionId + "\"\n" +
                        "\t} \n" +
                        "}")
                .post(String.format(JiraEndpoints.MOVE_JIRA_ISSUE, tcKey));
    }

    public static void updateTestCaseDescription(String tcKey, String description){
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{\"fields\":{\"description\":\"" + description + "\" } }")
                .put(JiraEndpoints.JIRA_ISSUE + "/" + tcKey);
    }
}
