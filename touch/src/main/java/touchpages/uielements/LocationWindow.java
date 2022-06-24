package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.xml.stream.Location;
import java.util.List;

@FindBy(css=".cl-share-location")
public class LocationWindow extends AbstractUIElement {

    @FindBy(css="input[placeholder='Search location']")
    private WebElement searchField;

    @FindBy(xpath="//div[@class='pac-item']")
    private List<WebElement> locations;

    @FindBy(css=".cl-location-search__send")
    private WebElement sendLocationsButton;

    @FindBy(css=".cl-button.cl-button--reset-only")
    private WebElement cancelLocationButton;


    public LocationWindow selectLocation(String locationName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(),locations,3);
        locations.stream().filter(e-> e.getText().contains(locationName)).findFirst().orElseThrow(() -> new AssertionError(
                "No Location was found from: " + locationName )).click();
        return this;
    }

    public LocationWindow searchLocation(String locationName){
        waitFor(1000);
        inputText(this.getCurrentDriver(), searchField, 2, "Location Search Field", locationName);
        return this;
    }
    public void clickSendLocationsButton(){
        clickElem(this.getCurrentDriver(), sendLocationsButton, 3,"Send Locations Button");
    }

    public void clickCancelLocationButton(){
        clickElem(this.getCurrentDriver(), cancelLocationButton,3,"Location cancel button");
    }

    public boolean checkSearchFieldisEmpty() {
       return isElementHasAnyText(this.getCurrentDriver(), searchField, 4);
    }
}
