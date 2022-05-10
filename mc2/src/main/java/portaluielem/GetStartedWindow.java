package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;


@FindBy(css = "div.create-integration-container")
public class GetStartedWindow extends BasePortalWindow {

    @FindBy(css = "span.svg-close.cl-clickable.push-right")
    private WebElement closeLandingPage;

    @FindBy(css = "ul.integration-steps span.integration-step-link")
    private List<WebElement> integrationSteps;

    @FindBy(css = "ul.integration-steps li.ng-scope.active")
    private WebElement onFocusMenuButton;

    @FindBy(css = "div.welcome-desc-title")
    private WebElement sectionHeader;

    @FindBy(css = "button.button.button-secondary.ng-scope")
    private WebElement previousButton;

    @FindBy(css = "button.button.button-primary.ng-scope")
    private WebElement nextButton;

    public void clickCloseGetStartedWindow(){
        clickElem(this.getCurrentDriver(), closeLandingPage, 5,"Close landing popup");
    }

    @Step(value = "Get integration steps labels from Get Started window")
    public List<String> getIntegrationStepsLbls(){
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < integrationSteps.size(); i ++){
            labels.add(i, integrationSteps.get(i).getText().trim());
        }
        return labels;
    }

    @Step(value = "Click on '{menuNavButton}' navigation button")
    public void clickNavButton(String menuNavButton){
        waitForElementsToBeVisible(this.getCurrentDriver(), integrationSteps, 3);
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        try {
            clickNavButtonPrivate(menuNavButton);
        }catch (ElementClickInterceptedException e){
            waitFor(500);
            clickNavButtonPrivate(menuNavButton);
        }
    }

    private void clickNavButtonPrivate(String menuNavButton){
        WebElement button = integrationSteps.stream().filter(e -> e.getText().trim().equals(menuNavButton))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot click '"+menuNavButton+"' nav button in Get Started section"));
        clickElem(this.getCurrentDriver(), button, 3, menuNavButton + " nav button");
    }

    @Step(value = "Verify '{menuNavButton}' navigation button is on focus")
    public boolean isNavButtonOnFocus(String menuNavButton){
        return onFocusMenuButton.getText().trim().equals(menuNavButton);
    }

    @Step(value = "Verify correctness of the section title")
    public boolean isSectionTitleCorrect(String sectionTitle){
        return sectionHeader.getText().trim().equals(sectionTitle);
    }

    @Step(value = "Click 'Previous' button")
    public void clickPreviousButton(){
        clickElem(this.getCurrentDriver(), previousButton, 2, "'Previous' button");
    }

    @Step(value = "Click 'Next' button")
    public void clickNextButton(){
        clickElem(this.getCurrentDriver(), nextButton, 2, "'Next' button");
    }
}
