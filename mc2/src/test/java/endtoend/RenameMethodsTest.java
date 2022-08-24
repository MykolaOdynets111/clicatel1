package endtoend;

import com.github.javafaker.Faker;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portalpages.PortalSignUpPage;

import java.util.HashMap;

@Listeners({TestAllureListener.class})
@Test(testName = "Registration")
@TmsLink("TECH-12068")
public class RenameMethodsTest extends BaseTest {

    private HashMap<String, String> signUpInfo = new HashMap<>();
    private PortalLoginPage loginPage;
    private PortalMainPage mainPage;
    private SoftAssert soft;
    protected String mTestCaseName = "";
    Faker faker;
    String testMethodName = "Method name";

    @BeforeTest
    private void prepareSignUpInfo(){
        System.setProperty("env", "qa");
        faker = new Faker();
        signUpInfo.put("firstName", faker.name().firstName());
        signUpInfo.put("lastName", faker.name().lastName());
        signUpInfo.put("name", signUpInfo.get("firstName") + " " + signUpInfo.get("lastName"));
        signUpInfo.put("accountName", "aqa_" + faker.lorem().word() + faker.number().digits(3));
        signUpInfo.put("email", "automationmc2+" + System.currentTimeMillis() +"@gmail.com");
        signUpInfo.put("pass", "p@$$w0rd4te$t");

        soft = new SoftAssert();

    }

//    @AfterMethod
//    private void updateTestnAME(ITestResult result, Method method){
//        Field methodName = null;
//        try {
//            methodName = BaseTestMethod.class.getDeclaredField("m_methodName");
//
//        methodName.setAccessible(true);
//        methodName.set(result.getMethod(), testMethodName);
//        method.setAccessible(true);
//        methodName.set(result.getMethod(), testMethodName);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

//    @Description("Registration :: Sign up")
    @Feature("feature")
    @Epic("epic")
    @Story("Story")
    @Test
    public void newSignUp(){
        PortalSignUpPage.openPortalSignUpPage()
                .provideSignUpDetails(signUpInfo.get("name"), signUpInfo.get("accountName"),
                                        signUpInfo.get("email"), signUpInfo.get("pass"));
    }

//    @AfterMethod
//    private void updatdeTestnAME(Method m){
//        setName("Registration :: Sign up");
//    }

//    @AfterMethod(alwaysRun = true)
//    private void updateTestName(ITestResult result){
//        SoftAssert softAssert = new SoftAssert();
//        ((TestResult) result).setAttribute("m_methodName", "New test method name");
//        String[] data = {"m_methodName", "New test method name"};
//        softAssert.assertTrue(false, "!!Result get name before" + result.getName() + "\n !!");
//        result.setParameters(data);
//        softAssert.assertTrue(false, "Result get name after" + result.getName() + "\n !!");
//        softAssert.assertAll();
//
//    }

    @AfterTest(alwaysRun = true)
    private void deactivateTestAccount1(ITestContext test){
//        test.setAttribute("m_methodName", "New test method name");
//        test.get.println("\n\n test.getName(): " + test.getName());
//        try {
//            ApiHelperPlatform.closeAccount(signUpInfo.get("accountName"),
//                    signUpInfo.get("email"), signUpInfo.get("pass"));
//        }catch (AssertionError e){
//            // Nothing to do. Account was not activated.
//        }

    }

//    @Override
//    public String getTestName() {
//        return this.mTestCaseName;
//    }

//    @AfterMethod(alwaysRun = true)
//    public void setResultTestName(ITestResult result) {
//        result.setParameters(new String[] {"Registration account"});
//    }



}
