package facebook.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(xpath = "//a[@data-hover='tooltip'][@data-tooltip-position='above'][not(contains(@class, 'close button'))]//ancestor::div[@role='complementary']")
public class MessengerWindow extends AbstractUIElement {

    private String inputFieldXPATHLocator = "//div[@role='combobox']";
    private String toUserMesaage = "//span[text()='%s']//following::div[contains(@class, 'direction')]//span/span";

    @FindBy(xpath = ".//div[@role='heading']/following-sibling::ul")
    private WebElement headerActionButonsContainer;

    @FindBy(xpath = ".//div[contains(@class, 'uiPopover')]")
    private WebElement gearIcon;

    @FindBy(xpath = "//ul[@role='menu']")
    private WebElement popupMenu;

    @FindBy(xpath = "//span[text()='Delete Conversation...']")
    private WebElement deleteConversationButton;

    @FindBy(xpath = "//a[text()='Delete Conversation']")
    private WebElement confirmDeletingConverstionButton;

    public void waitUntilLoaded() {
        waitForElementToBeVisible(this);
    }

    public void enterMessage(String message){
        findElemByXPATH(inputFieldXPATHLocator).sendKeys(message);
        pressEnterForWebElem(findElemByXPATH(inputFieldXPATHLocator));
    }

    public void deleteConversation(){
        moveToElement(DriverFactory.getInstance(),headerActionButonsContainer);
        gearIcon.click();
        waitForElementToBeVisible(popupMenu, 9);
        deleteConversationButton.click();
        waitForElementToBeVisible(confirmDeletingConverstionButton, 9);
        confirmDeletingConverstionButton.click();
    }

    public List<String> getToUserResponse(String userMessage) {
        waitForElementsToBeVisibleByXpath(String.format(toUserMesaage, userMessage), 30);
        List<WebElement> webElem = findElemsByXPATH(String.format(toUserMesaage, userMessage));
        return webElem.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

}
