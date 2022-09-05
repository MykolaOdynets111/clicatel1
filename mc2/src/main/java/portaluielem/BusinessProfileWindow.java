package portaluielem;

import drivermanager.ConfigManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    @FindBy(css = ".support-hours-select-wrapper .cl-r-select__indicators")
    private WebElement arrowSupportHours;

    @FindBy(xpath = ".//span[contains(@class, 'ui-select-choices-row-inner')]")
    private WebElement choicesSupportHours;

    @FindBy(xpath = ".//button[contains(text(), 'Add')]")
    private WebElement addSupportHoursButton;

    @FindBy(css = "[for='file-input-avatar']")
    private WebElement uploadButton;

    @FindBy(xpath = ".//button[contains(@class, 'image-button')]")
    private WebElement editImage;

    @FindBy(css = "[for='file-input-avatar'] input")
    private WebElement inputFile;

    @FindBy(css = "ul.color-picker-scroll button.button-primary")
    private WebElement acceptButton;

    @FindBy(xpath = ".//img[@alt = 'avatar preview']")
    private WebElement logoPreview;

    private WebElement getAgentSupportHoursOption(String option) {
        return findElementByXpath(this.getCurrentDriver(), String.format("//*[contains(text(), '%s')]", option), 10);
    }

    private final String choiseSupportDay = "//span[@class='cl-r-checkbox__label'][text()='%s']/..";
    private final String uncheckedTDay = ".//input[@value='%s' and not(@checked)]";


    public void clickUploadButton() {
        clickElem(this.getCurrentDriver(), uploadButton, 1, "Upload button");
    }

    public void clickEditImageButton() {
        clickElem(this.getCurrentDriver(), editImage, 1, "'Edit Image' button");
    }

    public void clickAcceptButton() {
        waitForElementToBeVisible(this.getCurrentDriver(), acceptButton, 5);
        acceptButton.click();
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

    public void setBusinessName(String name) {
        inputText(this.getCurrentDriver(), businessName, 2, "Business Name", name);
    }

    public void setCompanyCity(String name) {
        inputText(this.getCurrentDriver(), companyCity, 2, "City", name);
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

    public String uncheckTodayDay() {
        clickElem(this.getCurrentDriver(), arrowSupportHours, 2, "Arrow to open support hours");
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.US);
        String nameOfDay = simpleDateformat.format(new Date());
        WebElement dayToUncheck = findElemByXPATH(this.getCurrentDriver(), String.format(choiseSupportDay, nameOfDay));
        clickElem(this.getCurrentDriver(), dayToUncheck, 2, "Today day");
        clickElem(this.getCurrentDriver(), arrowSupportHours, 2, "Arrow to open support hours");
        return nameOfDay;
    }

    public boolean isUncheckTodayDay(String nameOfDay) {
        clickElem(this.getCurrentDriver(), arrowSupportHours, 3, "Arrow to open support hours");
        return isElementExistsInDOMXpath(this.getCurrentDriver(), String.format(uncheckedTDay, nameOfDay), 5);
    }

    public void selectAgentSupportHoursOption(String option) {
        WebElement supportHoursOption = getAgentSupportHoursOption(option);
        scrollAndClickElem(this.getCurrentDriver(), supportHoursOption, 7, option);
    }
}
