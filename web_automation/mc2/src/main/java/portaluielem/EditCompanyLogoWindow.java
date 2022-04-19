package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".ReactModal__Content")
public class EditCompanyLogoWindow extends BasePortalWindow {

    @FindBy(xpath = ".//button[text()='Save Image']")
    private WebElement saveImageButton;


    public void clickSaveImageButton(){
        clickElem(this.getCurrentDriver(), saveImageButton, 1,"Upload button");
    }


}