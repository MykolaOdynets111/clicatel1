package portalpages.uielements;


import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class ChatConsoleInboxRow extends Widget implements WebActions {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(xpath = "//div[@class='cl-actions-dropdown dropdown btn-group']/button")
    private WebElement threeDots;

    @FindBy(xpath = "//li[@class='cl-actions-dropdown__menu-item']//span[text()='Assign manually']")
    private WebElement assing;

    @FindBy(xpath = "//div[@class='Select-placeholder']")
    private WebElement select;

    @FindBy(xpath = "//div[@class='Select-menu']/div")
    private List<WebElement> listOfAgents;

    @FindBy(xpath = "//div[@class='modal-footer']//button[text()='Assign']")
    private WebElement assignButton;

    private String chatConsoleInboxRowNameXpath = "//div[@class='cl-table-user-data__description']//div[2]/../div[1]";

    public ChatConsoleInboxRow(WebElement element) {
        super(element);
    }

    public void clickThreeDots(){
       clickElemAgent(threeDots,3,"admin", "Three dots button");
    }

    public void clickAssignManually(){
        clickElemAgent(assing,3,"admin", "Assign manually button");
    }

    public void clickAgent(){
        clickElemAgent(listOfAgents.get(0),3,"admin", "Agent in the list");
    }

    public void clickAssignButton(){
        clickElemAgent(assignButton,3,"admin", "Assign button");
    }

    public String getChatConsoleInboxRowName(){
        return baseWebElem.findElement(By.xpath(chatConsoleInboxRowNameXpath)).getText();
    }


}
