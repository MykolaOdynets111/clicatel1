package endtoend.acceptance.testphones;

import endtoend.basetests.APICreatedAccountTest;
import interfaces.VerificationHelper;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import portalpages.*;

//DRAFT (Test Case clarification needed)

@Listeners({TestAllureListener.class})
@Test(testName = "Test Phone :: Add test phone from Launchpad", groups = {"newaccount", "testphone"}, dependsOnMethods = "addingTestPhoneTest")
@TmsLink("TECH-5745")
public class TestPhoneDeletingTest extends APICreatedAccountTest implements VerificationHelper {

    private PortalMainPage mainPage;
    private PortalManageTestPhonesPage testPhonesPage;
    private PortalConfigureSMSPage configureSMSPage;
    private String testPhone;

    @BeforeClass
    private void generateTestPhone(){
        testPhone = generateUSCellPhoneNumber();
    }



    @Description("Test Phone :: Add test phone from Launchpad")
    @Epic("Test Phone")
    @Feature("Add test phone from Launchpad")
    public void addingTestPhoneTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        deleteTestPhoneFromLaunchpad();
    }

    @Step(value = "Delete phone from launchpad")
    private void deleteTestPhoneFromLaunchpad(){
        mainPage.getLaunchpad()
                .getTargetSection("Manage test phones")
                .clickDeletePhoneButton();

        mainPage.getLaunchpad().confirmTestPhoneDeleting();
    }
}
