package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PortalUserProfilePage extends PortalAbstractPage {


    @FindBy(css = "button[ng-click='save()']")
    private WebElement saveChangesButton;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(xpath = "//div[@assigned-groups='user.roles']//div[@class='cl-collapse--heading-cell']")
    private List<WebElement> userRoles;

    // == Constructors == //

    public PortalUserProfilePage() {
        super();
    }
    public PortalUserProfilePage(String agent) {
        super(agent);
    }
    public PortalUserProfilePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getUserRoles(){
        return userRoles.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    @Step(value = "Verify all expected Touch roles are present on User Profile page")
    public boolean isAllExpectedRolesPresent(List<String> expectedRoles){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        List<String> actualRoles = getUserRoles();
        AtomicBoolean result = new AtomicBoolean(true);


        expectedRoles.forEach(e -> {
            if (!actualRoles.contains(e)) result.set(false);
        });
        return result.get();
    }

}
