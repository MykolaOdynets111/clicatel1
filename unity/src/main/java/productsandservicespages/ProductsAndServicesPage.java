package productsandservicespages;

import abstractclasses.UnityAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsAndServicesPage extends UnityAbstractPage {

    @FindBy(xpath = "//div[contains(text(),'Explore')]")
    private WebElement exploreBtn;

    @FindBy(xpath = "//div[contains(text(),'Channels')]")
    private WebElement channelsBtn;

    @FindBy(xpath = "//div[contains(text(),'Products')]")
    private WebElement productsBtn;

    @FindBy(xpath = "//div[contains(text(),'Integrations')]")
    private WebElement integrationsBtn;

    public ProductsAndServicesPage(WebDriver driver) {

        super(driver);
    }

    public void clickOnExplore() {
        clickElem(this.getCurrentDriver(), exploreBtn, 3, "Explore Button");
    }

    public void clickOnChannels() {
        clickElem(this.getCurrentDriver(), channelsBtn, 3, "Channels Button");
    }

    public void clickOnProducts() {
        clickElem(this.getCurrentDriver(), productsBtn, 3, "Products Button");
    }

    public void clickOnIntegrations() {
        clickElem(this.getCurrentDriver(), integrationsBtn, 3, "Integrations Button");
    }
}
