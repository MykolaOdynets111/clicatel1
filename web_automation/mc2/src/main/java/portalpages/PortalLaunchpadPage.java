package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portaluielem.*;

import java.util.List;

public class PortalLaunchpadPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[text()='Remove']")
    private WebElement confirmRemovingTestPhone;

    @FindBy(xpath = "//button[contains(text(), ' Get started with Touch')]")
    private WebElement getStartedWithTouchButton;

    @FindAll({
            @FindBy(xpath = "//ng-include/div[contains(@class, 'ng-scope')]/div[contains(@class, 'col-sm-6 col-md-6 col-lg-6')]"),
            @FindBy(xpath = "//ng-include/div[contains(@class, 'ng-scope')]/div[not(contains(@class, 'col-sm-6 col-md-6 col-lg-6'))][child::p]"),
            @FindBy(xpath = "//div[@class='touch-card ng-scope']")
    })
    private List<WebElement> getStartedCards;

    private ModalWindow modalWindow;

    private GetLongNumberWindow longNumberWindow;

    // == Constructors == //

    public PortalLaunchpadPage(WebDriver driver) {
        super(driver);
    }
    public PortalLaunchpadPage(String agent) {
        super(agent);
    }
    public PortalLaunchpadPage() {
        super();
    }

    public GetLongNumberWindow getLongNumberWindow(){
        longNumberWindow.setCurrentDriver(this.getCurrentDriver());
        return longNumberWindow;
    }


    public ModalWindow getModalWindow(){
        modalWindow.setCurrentDriver(this.getCurrentDriver());
        return modalWindow;
    }

    @Step(value = "Verify if 'Get started with Touch' button shown")
    public boolean isGetStartedWithTouchButtonShown(){ return isElementShown(this.getCurrentDriver(), getStartedWithTouchButton, 2);}

    public void clickGetStartedWithTouchButton(){ getStartedWithTouchButton.click();}

    public GetStartedSection getTargetSection(String targetCard){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForElementsToBeVisible(this.getCurrentDriver(), getStartedCards, 10);
        try {
            return getStartedCards.stream().map(e -> new GetStartedSection(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(card -> card.getCardTitle().equals(targetCard))
                    .findFirst().orElseGet(null);
        }
        catch(NullPointerException e){
            return null;
        }
    }

    @Step(value = "Verify '{sectionTitle}' launchpad section presence")
    public boolean isSectionPresent(String sectionTitle, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        GetStartedSection card = getTargetSection(sectionTitle);
        for(int i = 0; i < wait; i++){
            if(card!=null) return true;
            else{
                waitFor(1000);
                card = getTargetSection(sectionTitle);
            }
        }
        return false;
    }

    @Step(value = "Get number of sections on Launchpad")
    public int getNumberOfSections(){
        return getStartedCards.size();
    }

    @Step(value = "Confirm test phone deleting")
    public void confirmTestPhoneDeleting(){
        clickElem(this.getCurrentDriver(), confirmRemovingTestPhone, 4, "Confirm test phone deleting");
        waitWhileProcessing(1, 4);
    }
}
