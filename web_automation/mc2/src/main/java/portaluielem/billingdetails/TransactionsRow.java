package portaluielem.billingdetails;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TransactionsRow extends AbstractWidget {

    @FindBy(css = "td[cl-mobile-title='Transaction Name']")
    private WebElement transactionName;

    @FindBy(css = "td[cl-mobile-title='Amount']")
    private WebElement transactionAmount;

    @FindBy(css = "td[cl-mobile-title='Transaction Date']")
    private WebElement transactionDate;

    protected TransactionsRow(WebElement element) {
        super(element);
    }

    public TransactionsRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getTransactionName(){
        return getTextFromElem(this.getCurrentDriver(), transactionName, 5, "Transaction Name");
    }

    public String getTransactionAmount(){
        return getTextFromElem(this.getCurrentDriver(), transactionAmount, 5, "Transaction Amount");
    }

    public String getTransactionDate(){
        return getTextFromElem(this.getCurrentDriver(), transactionDate, 5, "Transaction Date");
    }

}