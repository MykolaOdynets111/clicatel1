package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ManageTestPhonesRow extends AbstractWidget {

    @FindBy(css = "td[cl-mobile-title='Number']")
    private WebElement phoneNumber;

    private WebElement baseWebElem = this.getWrappedElement();

    protected ManageTestPhonesRow(WebElement element) {
        super(element);
    }

    public ManageTestPhonesRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getPhoneNumber(){
        return getTextFromElem(this.getCurrentDriver(), phoneNumber, 5, "Phone number");
    }

}
