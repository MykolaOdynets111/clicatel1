package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.CreateDepartmentForm;
import portaluielem.DepartmentCard;

import java.util.List;

public class DepartmentsManagementPage extends PortalAbstractPage {

    @FindBy(css = ".departments-grouping-reused__card--add-department")
    private WebElement addNewDepartmentButton;

    @FindBy(xpath = "//span[contains(text(), 'Departments Management')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[@class = 'departments-grouping-reused__card']")
    private List<WebElement> departmentCards;

    @FindBy(xpath = "//div[@id = 'swal2-content']")
    private WebElement duplicationAlert;

    //@FindBy(css = ".cl-r-confirmation button.cl-r-button--primary")
    @FindBy(css=".cl-button.cl-button--primary")
    private WebElement confirmDeletingButton;

    private CreateDepartmentForm createDepartmentForm;

    public DepartmentsManagementPage() {
        super();
    }

    public DepartmentsManagementPage(String agent) {
        super(agent);
    }

    public DepartmentsManagementPage(WebDriver driver) {
        super(driver);
    }

    public DepartmentCard findCardByNameAndDescription(String cardName, String cardDescription) {
        return departmentCards.stream().map(a -> new DepartmentCard(a).setCurrentDriver(this.getCurrentDriver()))
                .filter(e -> e.getName().trim().equals(cardName) & e.getDescription().trim().equals(cardDescription))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot Find the card with '" + cardName + "'  name and '" + cardDescription + "' description"));
    }

    public boolean isCardPresent(String cardName, int time) {
        boolean isCardPresent = false;

        for (int i = time; i > 0; i--) {
            try {
                isCardPresent = departmentCards.stream().anyMatch(e -> e.getText().contains(cardName));
            } catch (StaleElementReferenceException e) {
            }
            if (isCardPresent) {
                return true;
            }
            waitFor(1000);
        }
        return false;
    }

    public boolean isCardDisappeared(String cardName, int time) {
        boolean isCardPresent = true;
        try {
            for (int i = time; i > 0; i--) {
                isCardPresent = departmentCards.stream().anyMatch(e -> e.getText().contains(cardName));
                if (!isCardPresent) {
                    return true;
                }
                waitFor(1000);
            }
        } catch (StaleElementReferenceException e) {
            return true;
        }
        return false;
    }

    @Step(value = "Click 'Create New Department' button")
    public CreateDepartmentForm clickAddNewDepartmentButton() {
        clickElem(this.getCurrentDriver(), addNewDepartmentButton, 10, "Add New Department button");
        createDepartmentForm.setCurrentDriver(this.getCurrentDriver());
        return createDepartmentForm;
    }

    @Step(value = "Verify Departments page is opened")
    public boolean isDepartmentsPageOpened() {
        return isElementShown(this.getCurrentDriver(), pageTitle, 20);
    }

    public CreateDepartmentForm openDepartmentManageForm(String cardName, String cardDescription){
        findCardByNameAndDescription(cardName, cardDescription).clickManageDepartmentButton();
        createDepartmentForm.setCurrentDriver(this.getCurrentDriver());
        return createDepartmentForm;
    }

    public String getDuplicationAlertText(){
        return getTextFromElem(this.getCurrentDriver(), duplicationAlert, 3, "Duplication Alert");
    }

    public void confirmDeletingDepartment() {
        clickElem(this.getCurrentDriver(), confirmDeletingButton, 3, "Confirm deleting department button");
    }

}
