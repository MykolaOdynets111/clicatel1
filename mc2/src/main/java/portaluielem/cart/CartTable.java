package portaluielem.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.BasePortalWindow;

import java.util.List;

@FindBy(css ="table.table-integration")
public class CartTable extends BasePortalWindow {


    @FindBy(css = "tr.cl-table-row--uis")
    private List<WebElement> cartRows;


    public CartRow getTargetCartRow(String itemType, String total){
        try{
            return cartRows.stream().map( e -> new CartRow(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getCartItemType().equals(itemType)&
                            e.getCartItemTotal().equals(total)).findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    public CartRow getTargetCartRow(String itemName){
        try{
            return cartRows.stream().map( e -> new CartRow(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getCartItemName().equals(itemName)).findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Step(value = "Verify the item present on the Cart page")
    public boolean isItemAddedToTheCart(String itemType, String total, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        CartRow targetRow = getTargetCartRow(itemType, total);
        for(int i =0; i<wait; i++){
            if(targetRow!=null) return true;
            else{
                waitFor(1000);
                targetRow = getTargetCartRow(itemType, total);
            }
        }
        return false;
    }

    @Step(value = "Verify the item present on the Cart page")
    public boolean isItemAddedToTheCart(String itemName, int wait){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        CartRow targetRow = getTargetCartRow(itemName);
        for(int i =0; i<wait; i++){
            if(targetRow!=null) return true;
            else{
                waitFor(1000);
                targetRow = getTargetCartRow(itemName);
            }
        }
        return false;
    }

}
