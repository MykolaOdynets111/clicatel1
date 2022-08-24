package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;


public class PortalSMSMessagingIntegration extends PortalAbstractPage {

    @FindBy(css = "span[phone]")
    private List<WebElement> testPhones;


    // == Constructors == //

    public PortalSMSMessagingIntegration() {
        super();
    }
    public PortalSMSMessagingIntegration(String agent) {
        super(agent);
    }
    public PortalSMSMessagingIntegration(WebDriver driver) {
        super(driver);
    }

    @Step(value = "Get text phones from SMS integration page")
    public List<String> getTestPhonesFromIntegration(){
        waitForElementsToBeVisible(this.getCurrentDriver(), testPhones, 4);
        if(testPhones.size()==0) Assert.fail("Test Phones list is empty");
        return testPhones.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }


}
