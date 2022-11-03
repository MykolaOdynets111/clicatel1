package unitypages;

import abstractclasses.UnityAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UnityLandingPage extends UnityAbstractPage {

    @FindBy(xpath = "//div[contains(text(),'My Workspace')]")
    private WebElement myWorkspaceMenuButton;

    @FindBy(xpath = "//div[contains(text(),'Products & Services')]")
    private WebElement productsAndServicesButton;

    public UnityLandingPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnMyWorkspace() {
        clickElem(this.getCurrentDriver(), myWorkspaceMenuButton, 3, "My Workspace Button");
    }

    public void clickOnProductsAndServices() {
        clickElem(this.getCurrentDriver(), productsAndServicesButton, 3, "Products & Services Button");
    }
}
