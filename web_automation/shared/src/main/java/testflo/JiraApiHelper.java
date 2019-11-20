package testflo;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.exception.JsonPathException;
import org.apache.http.params.CoreConnectionPNames;
import org.hamcrest.Matchers;
import testflo.jacksonschemas.AllureScenarioInterface;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testflo.jacksonschemas.testplansubtasks.ExistedTestCase;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JiraApiHelper {

    private static List<String> errors = new ArrayList<>();


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

    public static void changeTestCaseStatus (String tcKey, String transitionId) throws SocketTimeoutException {
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(3000l));
        Response resp =  RestAssured.given()
                        .config(setTimeouts())
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"transition\": {\n" +
                        "        \"id\": \"" + transitionId + "\"\n" +
                        "\t} \n" +
                        "}")
                .post(String.format(JiraEndpoints.MOVE_JIRA_ISSUE, tcKey));
        if (resp.statusCode()!=204){
            errors.add("\n\n Status changing for " + tcKey + " failed: " + resp.getBody().asString());
        }
    }

    public static void updateTestCaseDescription(String tcKey, String description){
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .body("{\"fields\":{\"description\":\"" + description + "\" } }")
                .put(JiraEndpoints.JIRA_ISSUE + "/" + tcKey);
    }

    public static int getNextTransitionId(String tcKey){
        Response resp = RestAssured.given()
                .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                .header("Content-Type", "application/json")
                .get(String.format(JiraEndpoints.MOVE_JIRA_ISSUE, tcKey));
        if (resp.statusCode()!=200){
            waitFor(1000);
            resp = RestAssured.given()
                    .auth().preemptive().basic(JiraUser.USER_EMAIL, JiraUser.USER_PASS)
                    .header("Content-Type", "application/json")
                    .get(String.format(JiraEndpoints.MOVE_JIRA_ISSUE, tcKey));
        }
        int id=51;
        try{
            id = resp.getBody().jsonPath().getInt("transitions.id[0]");
        }catch (JsonPathException e){
            System.out.println("\n\n !! Error while getting transaction Id" + resp.getBody().asString() + "\n\n");
        }

        return id;
    }

    private static RestAssuredConfig setTimeouts(){
        return RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000)
                .setParam(CoreConnectionPNames.SO_TIMEOUT, 5000));
    }

    public static List<String> getErrors(){
        return errors;
    }

    private static void waitFor(int wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
