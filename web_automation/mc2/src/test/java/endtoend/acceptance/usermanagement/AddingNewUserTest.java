package endtoend.acceptance.usermanagement;

import datamanager.ExistedAccount;
import datamanager.model.NewUserModel;
import endtoend.basetests.BaseTest;
import interfaces.VerificationHelper;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import portalpages.*;


@Listeners({TestAllureListener.class})
@Test(testName = "User Management :: Add new user", groups = {"existedaccount", "usermanagement"})
@TmsLink("TECH-5846")
public class AddingNewUserTest extends BaseTest implements VerificationHelper {

    private PortalMainPage mainPage;
    private PortalManageTestPhonesPage testPhonesPage;
    private PortalConfigureSMSPage configureSMSPage;
    private NewUserModel userModel;
    private ExistedAccount account;
    private PortalUserManagementPage userManagementPage;

    @BeforeClass
    private void generateUserData(){
        userModel = new NewUserModel();
        account = ExistedAccount.getExistedAccountForPayments();
        userManagementPage = new PortalUserManagementPage();
    }


    @Description("User Management :: Add new user")
    @Epic("User Management")
    @Feature("Add new user")
    public void addingNewUserTest(){
        mainPage = PortalLoginPage.openPortalLoginPage().login(account.getEmail(), account.getPass());
        mainPage.getLeftMenu().navigateINLeftMenuWithSubmenu("Settings", "User management");

        verifyProvidingNewUserDetails();
    }

    @Step(value = "Provide new user details")
    private void verifyProvidingNewUserDetails(){
        userManagementPage.clickPageActionButton("Add user");
        userManagementPage.getAddNewUserWindow().provideNewUserDetailsInfo(userModel)
                                                .clickNextButton();
    }



}
