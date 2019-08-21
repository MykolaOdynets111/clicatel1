package endtoend.acceptance.touch;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;
import portalpages.PortalTouchIntegrationsPage;
import portalpages.PortalUserProfilePage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@Listeners({TestAllureListener.class})
@Test(testName = "Touch :: Get started with touch", groups = {"newaccount", "touch"})
@TmsLink("TECH-6202")
public class GetStartedWithTouchTest extends APICreatedAccountTest  {

    private PortalMainPage mainPage;
    private SoftAssert soft;
    private String companyName;
    private PortalTouchIntegrationsPage touchIntegrations;

    @BeforeClass
    private void generateTestPhone(){
        soft = new SoftAssert();
    }


    @Description("Touch :: Get started with touch")
    @Epic("Touch")
    @Feature("Get started with touch")
    public void addTouchTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());

        verifyOpeningGetTouchWindow();
        verifyConfiguringTouch();
        verifyTouchRoles();
    }

    @Step(value = "Verify opening 'Get started with Touch' window")
    private void verifyOpeningGetTouchWindow() {
        mainPage.getLaunchpad().getTargetSection("Clickatell Touch")
                .clickButton("Get started with Touch", 3);
        Assert.assertTrue(mainPage.isConfigureTouchWindowOpened(),
                "\"Get started with Touch Go\" window is not opened");
    }

    @Step(value = "Verify adding touch")
    private void verifyConfiguringTouch() {
        soft = new SoftAssert();
        touchIntegrations = new PortalTouchIntegrationsPage();
        mainPage.getConfigureTouchWindow()
                .createNewTenant(readCompanyName(), email.get());


        soft.assertTrue(touchIntegrations.isPageOpened(), "Touch integration page is not loaded");

        soft.assertEquals(touchIntegrations.getPageHeader().getTouchGoPlanName().toLowerCase(), "touch go starter",
                "Incorrect Touch Go plan is shown");
        soft.assertEquals(touchIntegrations.getPageHeader().getAgentSeatsInfo(), "1x AGENT SEAT",
                "Incorrect Agent seats info is shown");
        soft.assertEquals(touchIntegrations.getPageHeader().getTextFromBuyingAgentsButton(), "Upgrade",
                "'Upgrade' button is not shown");

        soft.assertEquals(touchIntegrations.getIntegrationRowStatus("Web Chat").toLowerCase(), "active",
                "'Touch Web chat' integration status in integrations table is not as expected");
        soft.assertEquals(touchIntegrations.getIntegrationCardStatus("Web Chat").toLowerCase(), "active",
                "''Touch Web chat'' integration status in integrations card is not as expected");


        soft.assertAll();
    }

    @Step(value = "Verify Touch Roles are assigned under 'Assigned roles' section")
    private void verifyTouchRoles(){
        List<String> expectedRoles = Arrays.asList("admin (TOUCH)", "TOUCH_ADMIN (TOUCH)",
                "TOUCH_TENANT_ADMIN (TOUCH)", "TOUCH_AGENT (TOUCH)", "TOUCH_SUPERVISOR_AGENT (TOUCH)");
        touchIntegrations.getPageHeader().openProfileSettings();

        Assert.assertTrue(new PortalUserProfilePage().isAllExpectedRolesPresent(expectedRoles),
                "Not all touch roles are present on User Profile page.");
    }

    private String readCompanyName(){
        try {
            FileInputStream in = new FileInputStream("src/test/resources/newapiaccount.properties");
            Properties props = new Properties();
            props.load(in);
            companyName = props.getProperty("companyName");
        } catch(IOException e){
            e.printStackTrace();
        }
        return companyName;
    }
}
