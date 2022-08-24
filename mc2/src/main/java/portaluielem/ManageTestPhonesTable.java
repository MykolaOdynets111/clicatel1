package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css ="section[table-meta='tableMeta']")
public class ManageTestPhonesTable extends BasePortalWindow {

    @FindBy(css = "section[table-meta='tableMeta'] tr.ng-scope")
    private List<WebElement> testPhonesRows;

    public ManageTestPhonesRow getPhoneRow(String phone){
        try{
            return testPhonesRows.stream().map( e -> new ManageTestPhonesRow(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getPhoneNumber().equals(phone)).findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Step(value = "Verify test number is present in the table on Manage test phones page")
    public boolean isPhoneNumberAdded(String phoneNumber, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        ManageTestPhonesRow targetRow = getPhoneRow(phoneNumber);
        for(int i =0; i<wait; i++){
            if(targetRow!=null) return true;
            else{
                waitFor(1000);
                targetRow = getPhoneRow(phoneNumber);
            }
        }
        return false;
    }
}
