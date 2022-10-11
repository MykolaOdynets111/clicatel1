package steps.portalsteps.settingssteps;

import apihelper.APITagsHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import portaluielem.ChatTagsWindow;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static datetimeutils.DateTimeHelper.getDD_MM_YYYY_DateFormatter;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.agentsteps.AbstractAgentSteps.faker;
import static steps.portalsteps.AbstractPortalSteps.getPortalTouchPreferencesPage;

public class ChatTagsSteps {

    @Then("^Verify (.*) column is sorted by (.*)$")
    public void verifyColumnIsSorted(String column, String sortedType) {
        List<String> columnValue = getTagsWindow().getColumnValueList(column);
        if (sortedType.equals("asc")) {
            assertThat(columnValue)
                    .as("Values should be sorted by ASC ")
                    .isEqualTo(getSortedList(column, Comparator.naturalOrder()));
        } else if (sortedType.equals("desc")) {
            assertThat(columnValue)
                    .as("Values should be sorted by DESC ")
                    .isEqualTo(getSortedList(column, Comparator.reverseOrder()));
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
        assertThat(name)
                .as(format("Tag %s should be created!", name))
                .isIn(APITagsHelper.getAllTagsTitle());
    }

    @When("^Verify (.*) column data is updated for tag (.*)$")
    public void verifyColumnDataIsUpdatedFor(String column, String tag) {
        String columnValue = getTagsWindow().getCellValue(column, tag);

            assertThat(columnValue)
                    .as(format("%s tag value should be displayed in %s column", tag, column ))
                    .isEqualTo(LocalDateTime.now().format(getDD_MM_YYYY_DateFormatter()));
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
