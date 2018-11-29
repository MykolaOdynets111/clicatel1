package runner.plainTestNgTests;

import driverManager.ConfigManager;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import testNGtest_helper.AQAHealthTest;
import testNGtest_helper.PlainTestNGConfigs;

import java.util.HashMap;
import java.util.Map;

public class AutomationWidgetHealthCheck {

    private ThreadLocal<TestNG> testNGThreadLocal = new ThreadLocal<>();
    private Map<String, Boolean> resultsMap =  new HashMap<>();

    private void setTestNGThreadLocal(){
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.addListener(tla);
        testng.setDefaultSuiteName(PlainTestNGConfigs.getTestSuiteName());
        testNGThreadLocal.set(testng);
        getTestNGRunner().setOutputDirectory(PlainTestNGConfigs.getTestOutPutDir());

    }

    private TestNG getTestNGRunner(){
        if(testNGThreadLocal.get()==null){
            setTestNGThreadLocal();
        }
        return testNGThreadLocal.get();
    }

    /**
     * Run test on redirection to the agent (without sending request, only filling out user info card)
     * Full and short description of test may be found in AQAHealthTest enum
     * @param env - target env where to run test (e.g. demo, qa, prod, etc.)
     * @param tenantID - target tenantId
     * @return Map with full test description as a key and its run result as a value (true means test passed).
     */
    public Map<String, Boolean> runTestOnWidgetConnectionAndBotResponse(String env, String tenantID){
        String testDescription = AQAHealthTest.getAQATestDescriptionByShortName("bot connection");
        setUpConfigs(env, tenantID);

        getTestNGRunner().setTestClasses(new Class[] { WidgetAndBotHealthCheckTest.class });
        getTestNGRunner().setDefaultTestName(testDescription);
        getTestNGRunner().run();
        boolean result=true;
        if (getTestNGRunner().hasFailure()|getTestNGRunner().hasSkip()) result=false;
        resultsMap.put(testDescription, result);
        return resultsMap;

    }

    /**
     * Run test on redirection to the agent (without sending request, only filling out user info card)
     * Full and short description of test may be found in AQAHealthTest enum
     * @param env - target env where to run test (e.g. demo, qa, prod, etc.)
     * @param tenantID - target tenantId
     * @return Map with full test description as a key and its run result as a value (true means test passed).
     */
    public Map<String, Boolean> runTestOnRedirectionToAgentAndUserInfoCard(String env, String tenantID){
        String testDescription = AQAHealthTest.getAQATestDescriptionByShortName("redirection to agent");
        setUpConfigs(env, tenantID);


        getTestNGRunner().setTestClasses(new Class[] { WidgetRedirectionToTheAgentTest.class });
        getTestNGRunner().setDefaultTestName(testDescription);
        getTestNGRunner().run();

        boolean result=true;
        if (getTestNGRunner().hasFailure()|getTestNGRunner().hasSkip()) result=false;
        resultsMap.put(testDescription, result);
        return resultsMap;    }

    /**
     * Run all existed heals check tests
     * @param env - target env where to run test (e.g. demo, qa, prod, etc.)
     * @param tenantID - target tenantId
     * @return Map with full test description as a key and its run result as a value (true means test passed).
     */
    public Map<String, Boolean> runAllHealthCheck(String env, String tenantID){
        runTestOnWidgetConnectionAndBotResponse(env, tenantID);
        runTestOnRedirectionToAgentAndUserInfoCard(env, tenantID);
        return resultsMap;
    }



    private void setUpConfigs(String env, String tenantID){
        ConfigManager.setEnv(env);
        ConfigManager.setTenantId(tenantID);
        ConfigManager.setBrowserType("headless_chrome");
    }
}
