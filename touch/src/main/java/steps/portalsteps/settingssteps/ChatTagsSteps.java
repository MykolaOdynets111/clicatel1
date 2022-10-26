package steps.portalsteps.settingssteps;

import apihelper.APITagsHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import portaluielem.ChatTagsWindow;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static steps.agentsteps.AbstractAgentSteps.faker;
import static steps.portalsteps.AbstractPortalSteps.getPortalTouchPreferencesPage;

public class ChatTagsSteps {

    @Then("^Verify (.*) column is sorted by (.*)$")
    public void verifyColumnIsSorted(String column, String sortedType) {
        List<String> columnValue = getTagsWindow().getColumnValueList(column);
        if (sortedType.equals("asc")) {

        } else if (sortedType.equals("desc")) {

        }
    }

    @When("^Sort (.*) column by (.*)$")
    public void sortColumnBy(String column, String sortedType) {
        getTagsWindow().clickSortedColumn(column, sortedType);
    }

    @When("^Create a tag (.*)$")
    public void createTag(String name) {
        getTagsWindow()
                .clickAddChatTagButton()
                .setTagName(name + faker.numerify("#####"))
                .clickSaveButton();
    }

    @When("^Set (.*) for (.*) tag status$")
    public void changeTagStatus(String status, String tagName){
        APITagsHelper.setTagsStatus(tagName, status);
    }

    @NotNull
    private static List<String> getSortedList(String column, Comparator<String> comparator) {
        return getTagsWindow().getColumnValueList(column)
                .stream().sorted(comparator).collect(Collectors.toList());
    }

    private static ChatTagsWindow getTagsWindow() {
        return getPortalTouchPreferencesPage().getChatTagsWindow();
    }
}
