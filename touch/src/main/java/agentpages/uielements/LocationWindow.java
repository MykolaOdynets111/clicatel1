package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css=".cl-share-location")
public class LocationWindow extends AbstractUIElement {

    @FindBy(css="input[placeholder='Search location']")
    private WebElement searchField;

    @FindBy(xpath= "//div[@class='cl-address__name']")
    private WebElement searchedAddress;

    @FindBy(xpath="//div[@class='pac-item']")
    private List<WebElement> locations;

    @FindBy(css=".cl-location-search__send")
    private WebElement sendLocationsButton;

    @FindBy(css=".cl-button.cl-button--reset-only")
    private WebElement cancelLocationButton;

    @FindBy(css="button")
    private List<WebElement> buttonElements;

    public LocationWindow selectLocation(String locationName){
        clickElem(this.getCurrentDriver(),searchField,3,"Search Location");
        waitForFirstElementToBeVisible(this.getCurrentDriver(),locations,3);
        locations.stream().filter(e-> e.getText().contains(locationName)).findFirst().orElseThrow(() -> new AssertionError(
                "No Location was found from: " + locationName )).click();
        return this;
    }

    public boolean fetchLocationDropdownSize(int expectedSize){
        return locations.size() == expectedSize;
    }

    public String getTextFromSearch(){
        waitForFirstElementToBeVisible(this.getCurrentDriver(),locations,3);
        String location = getTextFromElem(this.getCurrentDriver(), searchedAddress, 2,"Location Search Field");
        return location;
    }
    public LocationWindow searchLocation(String locationName){
        waitFor(1500);
        inputText(this.getCurrentDriver(), searchField, 2, "Location Search Field", locationName);
        return this;
    }

    public int isSendLocationsButtonInvisible(String elementText){
        return buttonElements.stream().filter(e -> e.getText().contains(elementText)).collect(Collectors.toList()).size();
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