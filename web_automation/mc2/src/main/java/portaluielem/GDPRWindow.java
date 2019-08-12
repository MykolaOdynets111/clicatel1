package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



@FindBy(xpath = "//div[@class = 'modal-dialog modal-sm'][descendant::h3]")
public class GDPRWindow extends BasePortalWindow {

    @FindBy(css = "a[alt = 'GDPR Privacy Policy']")
    private WebElement GDPRpolicyLink;

    @FindBy(css = "a[alt = 'GDPR Compliance']")
    private WebElement GDPRComplianceLink;

    @Step(value = "Click GDPR Privacy Policy link")
    public GDPRWindow clickGDPRPolicyLink(){
        clickElem(this.getCurrentDriver(), GDPRpolicyLink, 5, "'GDPR Privacy Policy' link");
        return this;
    }

    @Step(value = "Click GDPR Compliance")
    public GDPRWindow clickGDPRComplianceLink(){
        clickElem(this.getCurrentDriver(), GDPRComplianceLink, 5, "'GDPR Compliance' link");
        return this;
    }

    @Step(value = "Verify GDPR Privacy Policy link redirected to the correct page")
    public boolean verifyCorrectnessGDPRLink(String expectedLink){
        String portalWindow = this.getCurrentDriver().getWindowHandle();
        switchDriver(this.getCurrentDriver(), portalWindow);

        boolean result = this.currentDriver.getCurrentUrl().equals(expectedLink);
        closeTab(this.getCurrentDriver(), portalWindow);

        return result;
    }

}
