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
    public void clickGDPRPolicyLink(){
        clickElem(this.getCurrentDriver(), GDPRpolicyLink, 5, "'GDPR Privacy Policy' link");
    }

    @Step(value = "Click GDPR Compliance")
    public void clickGDPRComplianceLink(){
        clickElem(this.getCurrentDriver(), GDPRComplianceLink, 5, "'GDPR Compliance' link");
    }

    @Step(value = "Verify GDPR Privacy Policy link redirected to the correct page")
    public boolean verifyGDPRPolicyLinkWorks(){
        String currentWindow = this.getCurrentDriver().getWindowHandle();
        if(this.getCurrentDriver().getWindowHandles().size()>1) {
            for (String winHandle : this.getCurrentDriver().getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    this.getCurrentDriver().switchTo().window(winHandle);
                }
            }
        }
        return false;
    }

}
