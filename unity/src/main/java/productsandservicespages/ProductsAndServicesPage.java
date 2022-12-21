package productsandservicespages;

import abstractclasses.AgentAbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsAndServicesPage extends AgentAbstractPage {

    @FindBy(xpath = "//div[contains(text(),'Explore')]")
    private WebElement exploreBtn;

    @FindBy(xpath = "//div[contains(text(),'Channels')]")
    private WebElement channelsBtn;

    @FindBy(xpath = "//div[contains(text(),'Products')]")
    private WebElement productsBtn;

    @FindBy(xpath = "//div[contains(text(),'Integrations')]")
    private WebElement integrationsBtn;

    public ProductsAndServicesPage() {
        super();
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
