package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testcontainers.shaded.org.bouncycastle.tsp.TSPUtil;
import portaluielem.CreateDepartmentForm;
import portaluielem.DepartmentCard;

import java.util.List;

public class DepartmentsManagementPage extends PortalAbstractPage {

    @FindBy(id = "departments-iframe")
    private WebElement iframeId;

    @FindBy(css = ".departments-grouping__card.departments-grouping__card--add-department")
    private WebElement addNewDepartmentButton;

    @FindBy(xpath = "//a[contains(text(), 'Departments Management')]")
    private WebElement pageTitle;


    @FindBy(xpath = "//div[@class = 'departments-grouping__card']")
    private List<WebElement> departmentCards;


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

    public boolean isCardPresent(String cardName, int time){
        boolean isCardPresent = false;
        for (int i = time; i > 0; i--){
            isCardPresent = departmentCards.stream().anyMatch(e -> e.getText().contains(cardName));
            if (isCardPresent){
                return true;
            }
            waitFor(1000);
        }
        return false;
    }

    public DepartmentsManagementPage switchToFrame(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        return this;
    }

    @Step(value = "Click 'Create New Department' button")
    public CreateDepartmentForm clickAddNewDepartmentButton() {
        switchToFrame();
        clickElem(this.getCurrentDriver(), addNewDepartmentButton, 10, "Add New Department button");
        createDepartmentForm.setCurrentDriver(this.getCurrentDriver());
        return createDepartmentForm;
    }

    @Step(value = "Verify Departments page is opened")
    public boolean isDepartmentsPageOpened() {
        return isElementShown(this.getCurrentDriver(), pageTitle, 5);
    }

}
