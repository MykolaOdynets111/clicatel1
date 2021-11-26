import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AgentLoginPage implements Waits{

    private WebDriver driver;

    @FindBy(css = "[id='tenants']")
    private WebElement tenantsDropdown;

    @FindBy(css = "[id='agents']")
    private WebElement agentsDropdown;

    @FindBy(css = "[id='auth-button']")
    private WebElement authenticateButton;

    public AgentLoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private Select dropdownSelect;

    public AgentLoginPage selectTenant(String tenantName){
        waitForElementToBeClickable(driver, tenantsDropdown, 6);
        dropdownSelect = new Select(tenantsDropdown);
        waitForOptionsIsDownloaded(dropdownSelect);
        dropdownSelect.selectByVisibleText(tenantName);
        return this;
    }

    private List<WebElement> waitForOptionsIsDownloaded(Select dropdownSelect){
        for(int i=0; i<10; i++) {
            if (dropdownSelect.getOptions().size() > 1){
                break;
            }
            wait(1000);
        }
        return dropdownSelect.getOptions();
    }

    public AgentLoginPage selectAgent(String agent){
        waitForElementToBeClickable(driver, agentsDropdown, 3);
        agentsDropdown.click();
        dropdownSelect = new Select(agentsDropdown);
        List<WebElement> options = waitForOptionsIsDownloaded(dropdownSelect);
//        String agentName = options.stream().filter(a -> a.getText().equalsIgnoreCase(agent)).findFirst().get().getText();
        dropdownSelect.selectByVisibleText(agent);
        return this;
    }

    public void clickAuthenticateButton(){
        waitForElementToBeClickable(driver, authenticateButton, 3);
        authenticateButton.click();
    }

    public void takeScreenshot(Agents agent){
        System.out.println(agent.name() + " take screenshot " + agent.getEmail());
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        String pathForSave = System.getProperty("user.dir") +"\\src\\test\\screenshots\\" + agent.name() +".png";
        File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(pathForSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
