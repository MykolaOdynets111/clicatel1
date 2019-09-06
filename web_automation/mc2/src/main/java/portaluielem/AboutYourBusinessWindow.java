package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = "div.integration-content.cl-content-area.mod-portal.mod-small-cards.ng-valid.ng-valid-required.ng-valid-maxlength.ng-dirty.ng-valid-parse")
public class AboutYourBusinessWindow extends BasePortalWindow {

    @FindBy(name = "companyName")
    private WebElement companyName;

    @FindBy(name = "companyCity")
    private WebElement companyCity;

    @FindBy(xpath = "//div[contains(@ng-model-key, 'category')]//li//span[contains(@class,'ui-select-choices-row')]")
    private List<WebElement> companyIndastry ;

    @FindBy(xpath = "//span[contains(@ng-model-key, 'country')]//li//span[contains(@class,'ui-select-choices-row')]")
    private List<WebElement> companyCountry ;

    @FindBy(xpath = "//div[contains(@ng-model-key, 'category')]")
    private WebElement selectCompanyIndustry ;

    @FindBy(xpath = "//span[contains(@ng-model-key, 'country')]")
    private WebElement selectCompanyCountry ;

    @FindBy(xpath = "//span[contains(text(), 'Specific Agent Support hours')]")
    private WebElement specificSupportHours ;

    @FindBy(xpath = "//div[contains(@ng-repeat, 'businessHours')]//span[contains(@aria-label, 'Select box activate')]")
    private WebElement arrowSupportHours ;

    @FindBy(xpath = "//span[contains(@class, 'ui-select-choices-row-inner')]")
    private WebElement choicesSupportHours ;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    private WebElement addSupportHoursButton ;

    private String choiseSupportDay = "//span[@class='checkbox-label ng-binding'][text()='%s']/..";

    private String isSelected = "/span[contains(@class, 'uncheck')]";

    private String companyNameXpath ="//input[contains(@name,'companyName')]";

    private String companyCityXpath ="//input[contains(@name,'companyCity')]";

    private String companyCountryFlag = "//span[contains(@class,'flag-icon ng-scope flag-icon') and @style]";

    public String  getCompanyName(){
        return companyName.getAttribute("value");
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
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), companyCountryFlag, 7);
        return selectCompanyCountry.getText();
    }

    public void setCompanyName(String name){
        findElemByXPATH(this.getCurrentDriver(), companyNameXpath).clear();
        findElemByXPATH(this.getCurrentDriver(), companyNameXpath).sendKeys(name);
    }

    public void setCompanyCity(String name){
        findElemByXPATH(this.getCurrentDriver(), companyCityXpath).clear();
        findElemByXPATH(this.getCurrentDriver(), companyCityXpath).sendKeys(name);
    }

    public String selectRandomIndastry(){
        selectCompanyIndustry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyIndastry.size() - 1);
        companyIndastry.get(randomIndustryNumber).click();
        return selectCompanyIndustry.getText();
    }

    public String selectRandomCountry(){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), companyCountryFlag, 7);
        selectCompanyCountry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyCountry.size() - 1);
        companyCountry.get(randomIndustryNumber).click();
        return selectCompanyCountry.getText();
    }

    public String uncheckTodayDay(){
        clickElem(this.getCurrentDriver(), arrowSupportHours,2,"Arrow to open support hours");
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        String nameOfDay = simpleDateformat.format(new Date());
        WebElement dayToUncheck = findElemByXPATH(this.getCurrentDriver(), String.format(choiseSupportDay,nameOfDay));
        clickElem(this.getCurrentDriver(), dayToUncheck,2,"Today day");
        clickElem(this.getCurrentDriver(), addSupportHoursButton,2,"Add button");
        return nameOfDay;
    }

    public boolean isUncheckTodayDay(String nameOfDay){
        clickElem(this.getCurrentDriver(), arrowSupportHours,3,"Arrow to open support hours");
        String isSelected = "/span[contains(@class, 'uncheck')]";
        return isElementExistsInDOMXpath(this.getCurrentDriver(),String.format(choiseSupportDay,nameOfDay)+ isSelected,5);
    }

    public void openSpecificSupportHours(){
        clickElem(this.getCurrentDriver(), specificSupportHours,2,"Specific Support Hours");
    }

}
