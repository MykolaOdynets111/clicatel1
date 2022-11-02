package portaluielem;

import drivermanager.ConfigManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = ".cl-business-profile")
public class BusinessProfileWindow extends BasePortalWindow {

    @FindBy(name = "orgName")
    private WebElement businessName;

    @FindBy(name = "city")
    private WebElement companyCity;

    @FindBy(xpath = "//label[text()='Industry:']/parent::div//div[contains(@id,'react-select')]")
    private List<WebElement> companyIndustry;

    @FindBy(xpath = "//label[text()='Country:']/parent::div//div[contains(@id,'react-select')]")
    private List<WebElement> companyCountry;

    @FindBy(xpath = ".//label[text()='Industry:']/parent::div//div[@class='cl-select-container cl-select-container--primary css-b62m3t-container']")
    private WebElement selectCompanyIndustry;

    @FindBy(xpath = ".//label[text()='Country:']/parent::div//div[@class='cl-select-container cl-select-container--primary css-b62m3t-container']")
    private WebElement selectCompanyCountry;

    @FindBy(css = "[for='file-input-avatar']")
    private WebElement uploadButton;

    @FindBy(xpath = ".//button[contains(@class, 'image-button')]")
    private WebElement editImage;

    @FindBy(css = "[for='file-input-avatar'] input")
    private WebElement inputFile;

    @FindBy(xpath = ".//img[@alt = 'avatar preview']")
    private WebElement logoPreview;

    private WebElement getAgentSupportHoursOption(String option) {
        return findElementByXpath(this.getCurrentDriver(), String.format("//*[contains(text(), '%s')]", option), 10);
    }

    public void clickUploadButton() {
        clickElem(this.getCurrentDriver(), uploadButton, 1, "Upload button");
    }

    public void clickEditImageButton() {
        clickElem(this.getCurrentDriver(), editImage, 1, "'Edit Image' button");
    }

    public boolean isLogoIsVisible() {
        return isElementShown(this.getCurrentDriver(), logoPreview, 2);
    }

    public void uploadPhoto(String photoPath) {
        waitForElementToBeVisible(this.getCurrentDriver(), uploadButton, 8);
        if (ConfigManager.isRemote()) {
            ((RemoteWebDriver) this.getCurrentDriver()).setFileDetector(new LocalFileDetector());
        }
        inputFile.sendKeys(new File(photoPath).getAbsolutePath());
    }

    public String getCompanyName() {
        return businessName.getAttribute("value");
    }

    public String getCompanyCity() {
        return companyCity.getAttribute("value");
    }

    public String getCompanyIndustry() {
        return selectCompanyIndustry.getText();
    }

    public String getCompanyCountry() {
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        return selectCompanyCountry.getText();
    }

    public BusinessProfileWindow setBusinessName(String name) {
        inputText(this.getCurrentDriver(), businessName, 2, "Business Name", name);
        return this;
    }

    public BusinessProfileWindow setCompanyCity(String name) {
        inputText(this.getCurrentDriver(), companyCity, 2, "City", name);
        return this;
    }

    public String selectRandomIndustry() {
        selectCompanyIndustry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyIndustry.size() - 1);
        companyIndustry.get(randomIndustryNumber).click();
        return selectCompanyIndustry.getText();
    }

    public String selectRandomCountry() {
        scrollAndClickElem(this.getCurrentDriver(), selectCompanyCountry, 1, "Company Dropdown");
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyCountry.size() - 1);
        companyCountry.get(randomIndustryNumber).click();
        return selectCompanyCountry.getText();
    }

    public void selectAgentSupportHoursOption(String option) {
        WebElement supportHoursOption = getAgentSupportHoursOption(option);
        scrollAndClickElem(this.getCurrentDriver(), supportHoursOption, 7, option);
    }
}
