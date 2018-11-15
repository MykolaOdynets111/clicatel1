package runner.plainTestNgTests;

import driverManager.ConfigManager;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import testNGtest_helper.AQAHeathTest;
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


    public Map<String, Boolean> runTestOnWidgetConnectionAndBotResponse(String env, String tenantID){
        String testDescription = AQAHeathTest.getAQATestDescriptionByShortName("bot connection");
        setUpConfigs(env, tenantID);

        getTestNGRunner().setTestClasses(new Class[] { WidgetAndBotHealthCheckTest.class });
        getTestNGRunner().setDefaultTestName(testDescription);
        getTestNGRunner().run();
        boolean result=true;
        if (getTestNGRunner().hasFailure()|getTestNGRunner().hasSkip()) result=false;
        resultsMap.put(testDescription, result);
        return resultsMap;

    }

    public Map<String, Boolean> runTestOnRedirectionToAgentAndUserInfoCard(String env, String tenantID){
        String testDescription = AQAHeathTest.getAQATestDescriptionByShortName("redirection to agent");
        setUpConfigs(env, tenantID);


        getTestNGRunner().setTestClasses(new Class[] { WidgetRedirectionToTheAgentTest.class });
        getTestNGRunner().setDefaultTestName(testDescription);
        getTestNGRunner().run();

        boolean result=true;
        if (getTestNGRunner().hasFailure()|getTestNGRunner().hasSkip()) result=false;
        resultsMap.put(testDescription, result);
        return resultsMap;    }


    private void setUpConfigs(String env, String tenantID){
        ConfigManager.setEnv(env);
        ConfigManager.setTenantId(tenantID);
        ConfigManager.setBrowserType("headless_chrome");
    }
}
