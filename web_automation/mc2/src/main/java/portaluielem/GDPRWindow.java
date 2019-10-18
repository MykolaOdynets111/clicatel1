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

    @Step(value = "Get GDPR page link")
    public String getGDPRLink(){
        String portalWindow = this.getCurrentDriver().getWindowHandle();
        switchDriver(this.getCurrentDriver(), portalWindow);

        String url = this.currentDriver.getCurrentUrl();
        closeTab(this.getCurrentDriver(), portalWindow);

        return url;
    }

}
