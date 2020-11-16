package touchpages.uielements.supervisor;


import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SupervisorDeskLiveRow extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = ".cl-r-chat-item-user-name")
    private WebElement userName;

    @FindBy(css = "[selenium-id='chat-item-icons-holder'] svg")
    private WebElement chanelIcon;

    public SupervisorDeskLiveRow(WebElement element) {
        super(element);
    }

    public SupervisorDeskLiveRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getUserName(){
        return userName.getText();//getTextFromElem(this.getCurrentDriver(), userName, 5, "User name");
    }

    public void clickOnUserName(){
        clickElem(this.getCurrentDriver(), userName, 5, "User Name");
    }

    public String getIconName(){
        return chanelIcon.getAttribute("name").trim();
    }
}
