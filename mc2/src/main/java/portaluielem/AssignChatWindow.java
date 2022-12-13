package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
public class AssignChatWindow extends BasePortalWindow {

    @FindBy(xpath = ".//label[contains(text(), 'Agents:')]/following-sibling::div//div[contains(@class, 'cl-select__indicators')]")
    private WebElement openAgentDropdownButton;

    @FindBy(xpath = ".//label[contains(text(), 'Departments:')]/following-sibling::div//div[contains(@class, 'cl-select__indicators')]")
    private WebElement openDepartmentsDropdownButton;

    @FindBy(xpath = "//div[contains(@class, 'cl-select__option')]")
    private WebElement availableAgentOrDepartment;

    @FindBy(xpath = "//div[contains(@class, 'cl-select__option')]")
    private List<WebElement> availableList;

    @FindBy(css = "button[type='submit']")
    private WebElement assignChatButton;

    @FindBy(css = "svg[name = 'close']")
    private WebElement closeAssignChatWindow;


    private void openDropDown(String dropDownType) {
        if(dropDownType.equalsIgnoreCase("Agent")) {
            clickElem(this.getCurrentDriver(), openAgentDropdownButton, 5, "Agent drop down");
        }
        else if (dropDownType.equalsIgnoreCase("Department")) {
            clickElem(this.getCurrentDriver(), openDepartmentsDropdownButton, 5, "Department drop down");
        }
    }

    public void selectDropDown(String dropdownType, String elementName) {
        for (int i = 0; i < 3; i++) {
            if (isElementRemoved(this.getCurrentDriver(), availableAgentOrDepartment, 2))
                openDropDown(dropdownType);
            waitForFirstElementToBeVisible(this.getCurrentDriver(), availableList, 5);
            if (availableList.size() > 0) {
                WebElement agent = availableList.stream()
                        .filter(e -> e.getText().toLowerCase().equals(elementName.toLowerCase()))
                        .findFirst().get();
                executeJSclick(this.getCurrentDriver(), agent);
                return;
            } else waitFor(500);
        }
        new AssertionError("Cannot find " + elementName);
    }

    public void clickAssignChatButton() {
        clickElem(this.getCurrentDriver(), assignChatButton, 3, "'Assign chat' button");
    }

    public boolean isAssignWindowShown(){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 2);
    }

    public void clickOnCloseAssignWindow() {
        clickElem(this.getCurrentDriver(), closeAssignChatWindow, 3, "Assign chat model close icon" );
    }
}
