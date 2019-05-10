package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    private String companyNameXpath ="//input[contains(@name,'companyName')]";

    private String companyCityXpath ="//input[contains(@name,'companyCity')]";

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
        return selectCompanyCountry.getText();
    }

    public void setCompanyName(String name){
        findElemByXPATHAgent(companyNameXpath).clear();
        findElemByXPATHAgent(companyNameXpath).sendKeys(name);
    }

    public void setCompanyCity(String name){
        findElemByXPATHAgent(companyCityXpath).clear();
        findElemByXPATHAgent(companyCityXpath).sendKeys(name);
    }

    public String selectRandomIndastry(){
        selectCompanyIndustry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyIndastry.size() - 1);
        companyIndastry.get(randomIndustryNumber).click();
        return selectCompanyIndustry.getText();
    }

    public String selectRandomCountry(){
        selectCompanyCountry.click();
        int randomIndustryNumber = ThreadLocalRandom.current().nextInt(0, companyCountry.size() - 1);
        companyCountry.get(randomIndustryNumber).click();
        return selectCompanyCountry.getText();
    }
}
