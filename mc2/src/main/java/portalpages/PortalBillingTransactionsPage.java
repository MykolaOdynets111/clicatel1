package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.billingdetails.TransactionsTable;



public class PortalBillingTransactionsPage extends PortalAbstractPage {

    @FindBy(css = "h3.empty-notification")
    private WebElement emptyTransationsTable;

    @FindBy(css = "input[ng-model='dateTo']")
    private WebElement toDatePicker;

    private TransactionsTable transactionsTable;

    // == Constructors == //

    public PortalBillingTransactionsPage() {
        super();
    }
    public PortalBillingTransactionsPage(String agent) {
        super(agent);
    }
    public PortalBillingTransactionsPage(WebDriver driver) {
        super(driver);
    }

   public TransactionsTable getTransactionsTable(){
       transactionsTable.setCurrentDriver(this.getCurrentDriver());
       return transactionsTable;
   }

   @Step(value = "Verify Transaction table is not empty")
   public boolean isTransactionRecordsAppear(int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForElementToBeVisible(this.getCurrentDriver(), toDatePicker, 3);
        boolean result = false;
        for(int i = 0; i<wait; i ++){
           if(!isElementShown(this.getCurrentDriver(), emptyTransationsTable, 1)){
               result = true;
               break;
           }else{
               waitFor(10000);
               this.getCurrentDriver().navigate().refresh();
               waitWhileProcessing(2, 5);
               waitForElementToBeVisible(this.getCurrentDriver(), toDatePicker, 3);
           }
        }
        return result;
   }
}
