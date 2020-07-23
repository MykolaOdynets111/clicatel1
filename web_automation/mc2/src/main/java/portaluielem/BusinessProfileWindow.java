package portaluielem;

import com.github.javafaker.Faker;
import drivermanager.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = ".business-profile")
public class BusinessProfileWindow extends BasePortalWindow {

    private Faker faker = new Faker();

    @FindBy(name = "tenantOrgName")
    private WebElement businessName;

    @FindBy(name = "city")
    private WebElement companyCity;

    @FindBy(xpath = "//label[text()='Industry:']/parent::div//div[contains(@id,'react-select')]")
    private List<WebElement> companyIndastry ;

    @FindBy(xpath = "//label[text()='Country:']/parent::div//div[contains(@id,'react-select')]")
    private List<WebElement> companyCountry ;

    @FindBy(xpath = ".//label[text()='Industry:']/parent::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']")
    private WebElement selectCompanyIndustry ;

    @FindBy(xpath = ".//label[text()='Country:']/parent::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']")
    private WebElement selectCompanyCountry ;

    @FindBy(xpath = ".//*[contains(text(), 'Specific agent support hours')]")
    private WebElement specificSupportHours ;

    @FindBy(css = ".support-hours-select-wrapper .cl-r-select__indicators")
    private WebElement arrowSupportHours ;

    @FindBy(xpath = ".//span[contains(@class, 'ui-select-choices-row-inner')]")
    private WebElement choicesSupportHours ;

    @FindBy(xpath = ".//button[contains(text(), 'Add')]")
    private WebElement addSupportHoursButton ;

    @FindBy(css = "[for='file-input-avatar']")
    private WebElement uploadButton ;

    @FindBy(css = "[for='file-input-avatar'] input")
    private WebElement inputFile;

    @FindBy(css = "ul.color-picker-scroll button.button-primary")
    private WebElement acceptButton ;

    private String choiseSupportDay = "//span[@class='cl-r-checkbox__label'][text()='%s']/..";

    public void clickUploadButton(){
        clickElem(this.getCurrentDriver(), uploadButton, 1,"Upload button");
    }

    public void clickacceptButton(){
        waitForElementToBeVisible(this.getCurrentDriver(), acceptButton, 5);
        acceptButton.click();
    }

    public void uploadPhoto(String photoPath){
        waitForElementToBeVisible(this.getCurrentDriver(), uploadButton, 8);
        if(ConfigManager.isRemote()){
            ((RemoteWebDriver) this.getCurrentDriver()).setFileDetector(new LocalFileDetector());
        }
        inputFile.sendKeys(new File(photoPath).getAbsolutePath());
    }

    public String  getCompanyName(){
        return businessName.getAttribute("value");
    }

    public String  getCompanyCity(){
        return companyCity.getAttribute("value");
    }

    public String  getCompanyIndustry(){
        return selectCompanyIndustry.getText();
    }

    public String  getCompanyCountry(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        return selectCompanyCountry.getText();
    }

    public void setBusinessName(String name){
        inputText(this.getCurrentDriver(), businessName, 2, "Business Name", name);
    }

    public void setCompanyCity(String name){
        inputText(this.getCurrentDriver(), companyCity, 2, "City", name);
    }

    public String selectRandomIndastry(){
        selectCompanyIndustry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyIndastry.size() - 1);
        companyIndastry.get(randomIndustryNumber).click();
        return selectCompanyIndustry.getText();
    }

    public String selectRandomCountry(){
        scrollAndClickElem(this.getCurrentDriver(), selectCompanyCountry, 1, "Company Dropdown");
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyCountry.size() - 1);
        companyCountry.get(randomIndustryNumber).click();
        return selectCompanyCountry.getText();
    }

    public String uncheckTodayDay(){
        clickElem(this.getCurrentDriver(), arrowSupportHours,2,"Arrow to open support hours");
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.US);
        String nameOfDay = simpleDateformat.format(new Date());
        WebElement dayToUncheck = findElemByXPATH(this.getCurrentDriver(), String.format(choiseSupportDay,nameOfDay));
        clickElem(this.getCurrentDriver(), dayToUncheck,2,"Today day");
        clickElem(this.getCurrentDriver(), arrowSupportHours,2,"Arrow to open support hours");
        return nameOfDay;
    }

    public boolean isUncheckTodayDay(String nameOfDay){
        clickElem(this.getCurrentDriver(), arrowSupportHours,3,"Arrow to open support hours");
        String isSelected = "/span[contains(@class, 'uncheck')]";
        return isElementExistsInDOMXpath(this.getCurrentDriver(),String.format(choiseSupportDay,nameOfDay)+ isSelected,5);
    }

    public void openSpecificSupportHours(){
        scrollAndClickElem(this.getCurrentDriver(), specificSupportHours,2,"Specific Support Hours");
    }
}
