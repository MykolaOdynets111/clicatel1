package testflo;


import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import testflo.jacksonschemas.AllureScenarioInterface;
import testflo.jacksonschemas.NewTCTStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NewTCBodyGenerator {

    // parent - key of TestPlan
    // customfield_10902 - key of TestTemplate

     public static String newIssueBody = "{\n" +
             "  \"fields\": {\n" +
             "    \"project\": {\n" +
             "      \"key\": \"TPORT\"\n" +
             "    },\n" +
             "    \"parent\": {\"key\": \"%s\"},\n" +
             "    \"summary\": \"%s\",\n" +
             "    \"description\": \"%s\",\n" +
             "    \"issuetype\": {\n" +
             "      \"name\": \"Test Case\"\n" +
             "    },\n" +
             "    \"customfield_10902\": \"%s\",\n" +
             "    \"customfield_10906\": {\n" +
             "            \"stepsVersion\": 1,\n" +
             "            \"stepsRows\": %s,\n" +
             "            \"stepsColumns\": [\n" +
             "                {\n" +
             "                    \"name\": \"Action\",\n" +
             "                    \"size\": 150\n" +
             "                },\n" +
             "                {\n" +
             "                    \"name\": \"Input\",\n" +
             "                    \"size\": 150\n" +
             "                },\n" +
             "                {\n" +
             "                    \"name\": \"Expected result\",\n" +
             "                    \"size\": 150\n" +
             "                }\n" +
             "            ],\n" +
             "            \"stepsStatuses\": []        }\n" +
             "  }\n" +
             "}";

     public static String formBodyForNewTestCase(String testPlanKey, String testTemplateId, AllureScenarioInterface scenario){
         ObjectMapper mapper = new ObjectMapper();

//         Map<String, String> scenarioSteps = scenario.getStepsWithStatuses();
//         List<String> testCaseSteps = new ArrayList<>();
//         for(String stepName : scenarioSteps.keySet()){
//
//             NewTCStep testCaseStep = new NewTCStep().setCells(
//                     Arrays.asList(stepName, "", ""));
//             try {
//                 testCaseSteps.add(mapper.writeValueAsString(testCaseStep));
//             } catch (JsonProcessingException e) {
//                 e.printStackTrace();
//             }
//         }


         List<Map<String, String>> scenarioSteps = scenario.getStepsWithStatuses();
         List<String> testCaseSteps = new ArrayList<>();
         for(int i = 0; i<scenarioSteps.size(); i++){
             Map<String, String> map = scenarioSteps.get(i);
             NewTCTStep testCaseStep = new NewTCTStep().setCells(
                     Arrays.asList(map.get("name"), "", ""));
             try {
                 testCaseSteps.add(i, mapper.writeValueAsString(testCaseStep));
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }
         }

         return String.format(newIssueBody, testPlanKey, scenario.getName(),
                 scenario.getDescription().replace("null", "").trim(),
                 testTemplateId, testCaseSteps.toString());
     }


}
