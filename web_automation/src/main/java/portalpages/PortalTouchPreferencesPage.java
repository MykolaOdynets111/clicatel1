package portalpages;

import drivermanager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.AutoResponder;

import java.util.List;

public class PortalTouchPreferencesPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[text()='Save changes ']")
    private WebElement saveChangesButton ;

    @FindBy(css = "div[cl-tabs='tabs'] ol.list-unstyled.list-inline>li")
    private List<WebElement> navItems;

    @FindBy(css = "div.integration-content cl-content-area mod-portal mod-small-cards automated-messages ng-scope>ng-scope")
    private List<WebElement> autoResponder;


    public AutoResponder getAutoResponse(){
        return new AutoResponder (
//                autoResponder.stream().filter(e -> new AutoResponder())//ilter(e -> ((String) e.get(2)).equals("faq"))
//                        .findFirst().get()

                autoResponder.stream().findFirst().get()//ilter(e -> ((String) e.get(2)).equals("faq"))

        );
    }

    public void clickNavItem(String navName){
        moveToElemAndClick(DriverFactory.getAgentDriverInstance(),
                navItems.stream().filter(e -> e.getText().equalsIgnoreCase(navName)).findFirst().get());
    }

    public void clickSaveButton(String agent){
        clickElemAgent(saveChangesButton, 2, agent, "Save changes ");
    }


//    @FindBy(xpath = "//div[contains(@class,'integration-type cl-card')]")
//    private List<WebElement> integrationCards;
//
//    private CreateIntegrationWindow createIntegrationWindow;
//
//    public CreateIntegrationWindow getCreateIntegrationWindow() {
//        return createIntegrationWindow;
//    }
//
//    private IntegrationRow getTargetIntegrationRow(String integrationName){
//        return integrationRows.stream().map(IntegrationRow::new).collect(Collectors.toList())
//                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
//                .findFirst().get();
//    }
//
//    private IntegrationCard getTargetIntegrationCard(String integrationName){
//        return integrationCards.stream().map(IntegrationCard::new).collect(Collectors.toList())
//                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
//                .findFirst().get();
//    }
//
//    public void clickToggleFor(String integrationName){
//        try {
//            getTargetIntegrationRow(integrationName).clickToggle();
//        } catch(java.util.NoSuchElementException e){
//            Assert.assertTrue(false, "Toggle for managing '"+integrationName+"' integration is not shown on the page");
//        }
//    }
//
//    public String getIntegrationRowStatus(String integrationName){
//        return getTargetIntegrationRow(integrationName).getStatus();
//    }
//
//    public String getIntegrationCardStatus(String integrationName){
//        return getTargetIntegrationCard(integrationName).getStatus();
//    }
//
//    public void clickActionButtonForIntegration(String integrationName){
//        getTargetIntegrationCard(integrationName).clickActionButton();
//    }
}
