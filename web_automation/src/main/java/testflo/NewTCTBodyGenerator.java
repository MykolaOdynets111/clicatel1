package testflo;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import testflo.jacksonschemas.AllureScenarioInterface;
import testflo.jacksonschemas.NewTCTStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NewTCTBodyGenerator {

    // parent - key of TestPlan

     public static String newIssueBody = "{\n" +
             "  \"fields\": {\n" +
             "    \"project\": {\n" +
             "      \"key\": \"TPORT\"\n" +
             "    },\n" +
             "    \"summary\": \"%s\",\n" +
             "    \"description\": \"%s\",\n" +
             "    \"issuetype\": {\n" +
             "      \"name\": \"Test Case Template\"\n" +
             "    },\"components\": [{\n" +
             "  \"name\": \"Automated Test Cases\"\n" +
             "}],\n" +
             "    \"customfield_10906\": {\n" +
             "            \"stepsRows\": %s" +
             "    }\n" +
             "  }\n" +
             "}";

     public static String formBodyForNewTestCaseTemplate(AllureScenarioInterface scenario){
         ObjectMapper mapper = new ObjectMapper();

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

         String body = String.format(newIssueBody, scenario.getName().replace("\"", "\\\"" ),
                 scenario.getDescription()
                         .replace("null", "")
                         .replace("\"", "\\\"" )
                         .replace("\n", "\\n")
                         .trim(),
                 testCaseSteps.toString());
         return body;
     }


}
