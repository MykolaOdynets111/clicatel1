package endtoend.acceptance;

import endtoend.basetests.APICreatedAccountTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLaunchpadPage;
import portalpages.PortalLoginPage;
import portalpages.PortalMainPage;

@Listeners({TestAllureListener.class})
@Test(testName = "Long number :: Verify that user cannot setup long number without billing details", groups = {"newaccount", "longnumber"})
@TmsLink("TECH-5822")
public class LongNumberTest extends APICreatedAccountTest {

    private PortalMainPage mainPage;
    private PortalLaunchpadPage launchpadPage;

    @Description("Long number :: Verify that user cannot setup long number without billing details")
    @Epic("Long number")
    @Feature("Verify that user cannot setup long number without billing details")
    public void checkLaunchpadSectionsNewAccount(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(email.get(), pass.get());
        mainPage.getLaunchpad().getTargetSection("SMS Add Ons")
                .clickButton("Get a long number", 3);
        mainPage.waitWhileProcessing(2, 3);
        launchpadPage = mainPage.getLaunchpad();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(launchpadPage.getModalWindow().getModalTitle(), "Billing Not Setup",
                "Incorrect modal window title \n");
        soft.assertEquals(launchpadPage.getModalWindow().getModalMessage(),
                "You can only purchase Long numbers after your Billing Details are setup",
                "Incorrect modal window message \n");
        soft.assertEquals(launchpadPage.getModalWindow().getModalPrimaryButton(), "Setup Billing",
                "Incorrect modal window button text \n");
        soft.assertAll();
    }


}
