package testflo;

import drivermanager.ConfigManager;
import testflo.jacksonschemas.AllureScenarioInterface;
import testflo.jacksonschemas.testplansubtasks.ExistedTestCase;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TestFloReporter {

    public static void main(String[] args) {
//        ConfigManager.setIsRemote("true");

        if(ConfigManager.reportToTouchFlo()) {

            //ToDo: Add storing base test plan key on Jenkins side as an Artifact
            String baseTestPlan = readBaseTestPlanKey();
            if (baseTestPlan == null) baseTestPlan = "";

            // 1. Create test plan
            String newTPlanKey = JiraApiHelper.copyTestPlan(baseTestPlan);
            updateTestPlanSummary(newTPlanKey);

            // 2. Get existed cases from Test Plan
            List<ExistedTestCase> existedTestCases = JiraApiHelper.getExistedInTestPlanTestCases(newTPlanKey);
            List<String> existedTestCasesNames = existedTestCases.stream()
                    .map(testCase -> testCase.getFields().getSummary()).collect(Collectors.toList());

            // 3. Get executed case from allure
            List<AllureScenarioInterface> executedTests = AllureReportParser.parseAllureResultsToGetTestCases();

            // 4. Filter the cases that should be created in Test Plan
            List<AllureScenarioInterface> executedTestsToBeCreatedInTestPlan = executedTests
                    .stream()
                    .filter(executedTest -> !(existedTestCasesNames.contains(executedTest.getName())))
                    .collect(Collectors.toList());

            // 5. Update target Test Plan key for the next run
            if (executedTestsToBeCreatedInTestPlan.size() > 0) writeNewBaseTestPlanKey(newTPlanKey);


            // 6. Loop through existed Test Cases in tests plan and update the status
            existedTestCases.forEach(tc -> updateExistedTestCase(tc, executedTests));

            // 7. Create missing test cases and set their status
            executedTestsToBeCreatedInTestPlan.forEach(e -> addMissingScenarios(e, newTPlanKey));

        } else{
            System.out.println("!!! reporting to TestFLO is turned off. \n" +
                    "Please check -DreportToTestFLO parameter !!!!");
        }

    }


    private static void updateTestPlanSummary(String testPlanKey){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String newPlanName = "AUTOMATION " + now.format(formatter) + " " +
                ConfigManager.getEnv().toUpperCase() + ", suite - " + ConfigManager.getSuite();

        JiraApiHelper.updateTestPlanSummary(testPlanKey, newPlanName);
    }

    private static void updateExistedTestCase(ExistedTestCase testCase, List<AllureScenarioInterface> executedTests){
        AllureScenarioInterface executedTest;
        try{
            executedTest = executedTests.stream()
                    .filter(e -> e.getName().equals(testCase.getFields().getSummary()))
                    .findFirst().get();
        }catch(NoSuchElementException e){
            return;
        }

        setTCStatus(testCase.getKey(), executedTest.getStatus(), executedTest.getFailureMessage());
    }

    public static void addMissingScenarios(AllureScenarioInterface scenario, String testPlan){
        Map<String, String> newTC = JiraApiHelper.createNewTestCase(scenario, testPlan);

        setTCStatus(newTC.get("key"), scenario.getStatus(), scenario.getFailureMessage());

    }

    private static void setTCStatus(String tcKey, String tcStatus, String failureMessage){
        if(!tcStatus.equalsIgnoreCase("canceled")) {
            JiraApiHelper.changeTestCaseStatus(tcKey, "11"); // moves ticket "In Progress" status

            if (tcStatus.equalsIgnoreCase("passed")) {
                JiraApiHelper.changeTestCaseStatus(tcKey, "21");
            }

            if (tcStatus.equalsIgnoreCase("broken") |
                    tcStatus.equalsIgnoreCase("failed")) {
                JiraApiHelper.changeTestCaseStatus(tcKey, "31");
                JiraApiHelper.updateTestCaseDescription(tcKey, failureMessage);
            }
        }
    }

    private static String readBaseTestPlanKey(){
            try {
                FileReader fileReader = new FileReader("src/main/java/testflo/testplan.txt");
                BufferedReader rFile =  new BufferedReader(fileReader);
                return rFile.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "TPORT-4030";
        }

    private static void writeNewBaseTestPlanKey(String key){
            File file =new File("src/main/java/testflo/testplan.txt");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(key);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
