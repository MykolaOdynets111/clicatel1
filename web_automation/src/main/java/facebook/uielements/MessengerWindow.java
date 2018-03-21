package facebook.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//a[@data-hover='tooltip'][@data-tooltip-position='above'][not(contains(@class, 'close button'))]//ancestor::div[@role='complementary']")
public class MessengerWindow extends AbstractUIElement {

    private String inputFieldXPATHLocator = "//div[@role='combobox']";

    public void waitUntilLoaded() {
        waitForElementToBeVisible(this);
    }

    public void enterMessage(String message){
        findElemByXPATH(inputFieldXPATHLocator).sendKeys(message);
        pressEnterForWebElem(findElemByXPATH(inputFieldXPATHLocator));
    }

}
