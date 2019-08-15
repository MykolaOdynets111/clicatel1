package portaluielem.billingdetails;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.BasePortalWindow;

import java.util.List;

@FindBy(css ="section>table")
public class TransactionsTable extends BasePortalWindow {


    @FindBy(css = "tr.cl-table-row--uis")
    private List<WebElement> transactionRows;


    public TransactionsRow getTargetTransactionRow(String name, String amount, String date){
        try{
            return transactionRows.stream().map( e -> new TransactionsRow(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getTransactionName().contains(name) &
                                 e.getTransactionAmount().equals(amount) &
                                 e.getTransactionDate().equals(date))
                    .findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Step(value = "Verify the transaction present on the Billing & payments > Transactions page")
    public boolean isTransactionPresent(String name, String amount, String date, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        TransactionsRow targetRow = getTargetTransactionRow(name, amount, date);
        for(int i =0; i<wait; i++){
            if(targetRow!=null) return true;
            else{
                waitFor(5000);
                this.getCurrentDriver().navigate().refresh();
                waitWhileProcessing(this.getCurrentDriver(), 2, 5);
                waitForElementsToBeVisible(this.getCurrentDriver(), transactionRows, 5);
                targetRow = getTargetTransactionRow(name, amount, date);
            }
        }
        return false;
    }

}
