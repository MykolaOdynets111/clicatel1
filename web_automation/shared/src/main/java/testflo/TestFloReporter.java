package testflo;

import drivermanager.ConfigManager;
import testflo.jacksonschemas.AllureScenarioInterface;
import testflo.jacksonschemas.testplansubtasks.ExistedTestCase;

import java.io.*;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TestFloReporter {

    public static void main(String[] args) {
////        For debug
//    System.setProperty("isAllure2Report", "true");
//    System.setProperty("reportToTestFLO", "true");
//    System.setProperty("isRerun", "true");
//    System.setProperty("tplanKey", "TPORT-37612");
//    System.setProperty("jirauser", "");
//    System.setProperty("jirapass", "");


        if(ConfigManager.reportToTouchFlo()) {

            // Get target TPLAN key
            String targetTestPlan = ConfigManager.getTplanKey().toUpperCase();
            if (targetTestPlan.isEmpty()) throw new AssertionError("Test Plan key was not provided");

            // 1. Create test plan copy if needed
            String newTPlanKey = "";
            if(ConfigManager.createNewTPlan()) {
                newTPlanKey = JiraApiHelper.copyTestPlan(targetTestPlan);
                updateTestPlanSummary(newTPlanKey);
            } else newTPlanKey = targetTestPlan;

            // 2. Get existed cases from Test Plan
            List<ExistedTestCase> existedTestCases = JiraApiHelper.getExistedInTestPlanTestCases(newTPlanKey);
            List<String> existedTestCasesNames = existedTestCases.stream()
                    .map(testCase -> testCase.getFields().getSummary()).collect(Collectors.toList());

            // 3. Get executed case from allure
            List<AllureScenarioInterface> executedTests = AllureReportParser
                    .parseAllureResultsToGetTestCases(ConfigManager.isAllure2Report());

            // 4. Filter the cases that should be created in Test Plan
            List<AllureScenarioInterface> executedTestsToBeCreatedInTestPlan = executedTests
                    .stream()
                    .filter(executedTest -> !(existedTestCasesNames.contains(executedTest.getName())))
                    .collect(Collectors.toList());

            // 5. Update target Test Plan key for the next run
//            if (executedTestsToBeCreatedInTestPlan.size() > 0) writeNewBaseTestPlanKey(newTPlanKey);


            // 6. Loop through existed Test Cases in tests plan and update the status
            existedTestCases.forEach(tc -> updateExistedTestCase(tc, executedTests));

            // 7. Create missing test cases and set their status
            String testPlan = newTPlanKey;
            executedTestsToBeCreatedInTestPlan.forEach(e -> addMissingScenarios(e, testPlan));

            // 8. Add logs about failed actions

            System.out.println("\n!!! reporting errors \n" +
                    JiraApiHelper.getErrors().toString());

        } else{
            System.out.println("!!! reporting to TestFLO is turned off. \n" +
                    "Please check -DreportToTestFLO parameter !!!!");
        }

    }

//    public static void main(String[] args) {
//
////            Сreating TCT
//            System.setProperty("remote", "true");
//            System.setProperty("isAllure2Report", "true");
//            List<String> createdTCT = new ArrayList<>();
//
//            // 1. Get executed case from allure
//            List<AllureScenarioInterface> executedTests = AllureReportParser.parseAllureResultsToGetTestCases();
//
//            // 2. Loop through existed Test Cases in tests plan and update the status
//
//            for (AllureScenarioInterface scenario : executedTests) {
//                Map<String, String> aa = JiraApiHelper.createNewTestCaseTemplate(scenario);
//                createdTCT.add(aa.get("key"));
//            }
//    }


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
                    .filter(e -> e!=null)
                    .filter(e -> e.getName().equals(testCase.getFields().getSummary()))
                    .findFirst().get();
        }catch(NoSuchElementException e){
            return;
        }
        String testFloTestStatus = testCase.getFields().getStatus().getName().toLowerCase();
        if(!executedTest.getStatus().contains(testFloTestStatus)) {
            try {
                setTCStatus(testCase.getKey(), testFloTestStatus, executedTest.getStatus(), executedTest.getFailureMessage());
            }catch (SocketTimeoutException e){
                return;
            }
        }
    }

    public static void addMissingScenarios(AllureScenarioInterface scenario, String testPlan){
        System.out.println("Made For ff debug:");
        System.out.println("Scenario name: " + scenario.getName());
        System.out.println("Scenario status: " + scenario.getStatus());
        System.out.println("Scenario description: " + scenario.getDescription());
        System.out.println("Scenario failure message: " + scenario.getFailureMessage());
        if (!scenario.getFailureMessage().trim().equalsIgnoreCase("Test skipped with unknown reason")) {
            System.out.println("Scenario name inside: " + scenario.getName());
            System.out.println("Scenario status inside: " + scenario.getStatus());
            Map<String, String> newTC = JiraApiHelper.createNewTestCase(scenario, testPlan);
            try {
                setTCStatus(newTC.get("key"), "open", scenario.getStatus(), scenario.getFailureMessage());
            } catch (SocketTimeoutException e) {
                return;
            }
        }
    }

    private static void setTCStatus(String tcKey, String currentStatus, String executionStatus, String failureMessage)
    throws SocketTimeoutException{
        if(executionStatus.equalsIgnoreCase("canceled") |
                executionStatus.equalsIgnoreCase("skipped")){
            return;
        }
        moveTicketToInProgress(tcKey, currentStatus);
        setStatusForTestCase(tcKey, executionStatus, failureMessage);
    }

    private static void moveTicketToInProgress (String tcKey, String currentStatus) throws SocketTimeoutException{
        if(!currentStatus.equalsIgnoreCase("open")) retestTestCase(tcKey, currentStatus);
        else JiraApiHelper.changeTestCaseStatus(tcKey, "11"); // moves ticket "In Progress" status
    }

    private static void retestTestCase(String tcKey, String currentStatus) throws SocketTimeoutException {
        if(ConfigManager.rerunTestPlan()) {
            if (currentStatus.equalsIgnoreCase("pass")) {
                JiraApiHelper.changeTestCaseStatus(tcKey, "61"); // moves passed ticket "Re-test" status
            } else {
                JiraApiHelper.changeTestCaseStatus(tcKey, "51"); // moves failed ticket "Re-test" status
            }
            waitFor(4500); // To eliminate JIRA API hang out
            JiraApiHelper.changeTestCaseStatus(tcKey, "81"); // moves ticket "Test" status
            if(JiraApiHelper.getNextTransitionId(tcKey)==81){
                waitFor(1000);
                JiraApiHelper.changeTestCaseStatus(tcKey, "81");
            }
        }
    }

    private static void setStatusForTestCase(String tcKey, String tcStatus, String failureMessage) throws SocketTimeoutException{
        if (tcStatus.equalsIgnoreCase("passed")) {
            JiraApiHelper.changeTestCaseStatus(tcKey, "21");
        } else {
            JiraApiHelper.changeTestCaseStatus(tcKey, "31");
            JiraApiHelper.updateTestCaseDescription(tcKey, failureMessage);
        }
    }


    private static void waitFor(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waifForStatusToBeTest(String testCaseKey, int seconds){
        for (int i = 0; i<seconds*5; i++){
            int nextTransition = JiraApiHelper.getNextTransitionId(testCaseKey);
            if(nextTransition==81) break;
            else waitFor(200);
        }
    }
}
