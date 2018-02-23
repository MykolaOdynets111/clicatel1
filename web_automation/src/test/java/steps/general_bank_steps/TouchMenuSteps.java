package steps.general_bank_steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import touch_pages.pages.Widget;
import touch_pages.uielements.TouchActionsMenu;

public class TouchMenuSteps {

    Widget widget_for_touch_menu = new Widget();
    TouchActionsMenu touchActionsMenu = widget_for_touch_menu.getTouchActionsMenu();

    @When("^User click Touch button$")
    public  void clickTouchButton() {
        widget_for_touch_menu.clickTouchButton();
    }

    @Then("^\"(.*)\" is shown in touch menu$")
    public void isTouchMenuActionShown(String action) {
        Assert.assertTrue(touchActionsMenu.isTouchActionShown(action),
                "Touch action '"+action+"' is not shown in Touch menu");
    }

    @When("^User select \"(.*)\" from touch menu$")
    public void selectActionFromTouchMenu(String action) {
        touchActionsMenu.selectTouchAction(action);
    }
}
