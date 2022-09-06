package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".ReactModal__Content")
public class EditCompanyLogoWindow extends BasePortalWindow {

    @FindBy(xpath = ".//button[text()='Save Image']")
    private WebElement saveImageButton;

    @FindBy(xpath = ".//button[text()='Remove Image']")
    private WebElement removeImageButton;

    public void clickSaveImageButton() {
        scrollAndClickElem(this.getCurrentDriver(), saveImageButton, 1, "Upload button");
    }

    public void clickRemoveImageButton() {
        scrollAndClickElem(this.getCurrentDriver(), removeImageButton, 1, "'Remove Image' button");
    }
}
