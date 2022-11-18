package abstractclasses;

import driverfactory.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public abstract class UnityAbstractPage implements WebActions, ActionsHelper, JSHelper {

    private String loadingSpinner = "//*[text()='Connecting...']";

    protected WebDriver currentDriver;

    public UnityAbstractPage() {
        currentDriver = DriverFactory.getTouchDriverInstance();
        HtmlElementLoader.populatePageObject(this, currentDriver);
    }

    public UnityAbstractPage(WebDriver driver) {
        this.currentDriver = driver;
        HtmlElementLoader.populatePageObject(this, this.currentDriver);
    }

    public boolean waitForLoadingInLeftMenuToDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        try{
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToAppear);
            }catch (TimeoutException e){ }
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), loadingSpinner, waitForSpinnerToDisappear);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }

}
