package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(xpath = "//div[@class='profile-modal-header modal-header']/parent::div")
public class ProfileWindow extends AbstractUIElement {

    @FindBy(css = "ul.groups-list li.groups-item")
    private List<WebElement> listOfElementsWithRoles;

    @FindBy(css = "div.icon.icon-close")
    private WebElement closeProfileWindowButton;

    private String infoFieldCss = "span.profile-data-row>input[value='%s']";

    public boolean isAgentInfoShown(String info){
        return isElementShownByCSS(this.getCurrentDriver(), (String.format(infoFieldCss, info)), 3);
    }

    public List<String> getListOfRoles(){
        if(listOfElementsWithRoles.size()==0){
            Assert.fail("There are no roles listed in profile window.");
        }
        return listOfElementsWithRoles.stream().map(e -> e.getText().toUpperCase()).collect(Collectors.toList());
    }

    public boolean isCloseProfileButtonShown(){
        return isElementShown(this.getCurrentDriver(), closeProfileWindowButton, 3);
    }

    public void closeProfileWindow(){
        closeProfileWindowButton.click();
    }

    public void closeIfOpened(){
        if (isCloseProfileButtonShown()) closeProfileWindow();
    }
}
