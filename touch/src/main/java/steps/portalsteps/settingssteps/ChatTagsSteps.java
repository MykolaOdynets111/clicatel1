package steps.portalsteps.settingssteps;

import apihelper.APIHelperTags;
import datamanager.ComparatorProvider;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import portaluielem.ChatTagsWindow;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static datetimeutils.DateTimeHelper.getDD_MM_YYYY_DateFormatter;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.agentsteps.AbstractAgentSteps.faker;
import static steps.portalsteps.AbstractPortalSteps.getPortalTouchPreferencesPage;

public class ChatTagsSteps {

    @Then("^Verify (.*) column is sorted by ASC$")
    public void verifyColumnIsSortedAsc(String column) throws InterruptedException {
        List<String> columnValue = getTagsWindow().getColumnValueList(column);
        columnValue = columnValue.stream().filter(s -> !s.contains("\u2013")).collect(Collectors.toList());
        List<String> sorted;
        if (column.equals("Created on") || column.equals("Last Used")) {
            sorted = columnValue.stream()
                    .sorted(ComparatorProvider.DATE_COMPARATOR)
                    .collect(Collectors.toList());
        } else {
            sorted = getSortedList(column, Comparator.naturalOrder());
        }

        assertThat(columnValue)
                .as("Values should be sorted by ASC ")
                .isEqualTo(sorted);
    }

    @Then("^Verify (.*) column is sorted by DESC$")
    public static void verifyColumnIsSortedDesc(String column) {
        List<String> columnValue = getTagsWindow().getColumnValueList(column);
        columnValue = columnValue.stream().filter(s -> !s.contains("\u2013")).collect(Collectors.toList());
        List<String> sorted;
        if (column.equals("Created on")|| column.equals("Last Used")) {
            sorted = columnValue.stream()
                    .sorted(ComparatorProvider.DATE_COMPARATOR)
                    .collect(Collectors.toList());
            Collections.reverse(sorted);
        } else {
            sorted = getSortedList(column, Comparator.reverseOrder());
        }

        assertThat(columnValue)
                .as("Values should be sorted by DESC ")
                .isEqualTo(sorted);
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
                .isIn(APIHelperTags.getAllTagsTitle());
    }

    @When("^Verify (.*) column data is updated for tag (.*)$")
    public void verifyColumnDataIsUpdatedFor(String column, String tag) {
        assertThat(getCellValueForTag(column, tag))
                .as(format("%s tag value should be displayed in %s column", tag, column))
                .isEqualTo(LocalDateTime.now().format(getDD_MM_YYYY_DateFormatter()));
    }

    @When("^Rename tag (.*) to (.*) and verify column (.*) value is unchanged$")
    public void verifyValueUnchangedAfterEditingTheTag(String tag, String newValue, String column) {
        String valueBefore = getCellValueForTag(column, tag);
        renameTag(tag, newValue);

        assertThat(valueBefore)
                .as(format("%s tag value should be unchanged after editing in %s column", tag, column))
                .isEqualTo(getCellValueForTag(column, newValue));
    }

    @When("^Rename (.*) tag to (.*)$")
    public static void renameTag(String tag, String newTagName) {
        getTagsWindow()
                .clickEditTagButton(tag)
                .setTagName(newTagName)
                .clickSaveButton();

        assertThat(newTagName).as(format("%s tag was renamed", tag))
                .isIn(APIHelperTags.getAllTagsTitle());
        assertThat(tag).as(format("%s tag was renamed", newTagName))
                .isNotIn(APIHelperTags.getAllTagsTitle());
    }

    @When("^Set (.*) for (.*) tag status$")
    public void changeTagStatus(String status, String tagName) {
        APIHelperTags.setTagsStatus(tagName, status);
    }

    @NotNull
    private static List<String> getSortedList(String column, Comparator<String> comparator) {
        return getTagsWindow().getColumnValueList(column)
                .stream().sorted(comparator).collect(Collectors.toList());
    }

    private static String getCellValueForTag(String column, String tag) {
        return getTagsWindow().getCellValue(column, tag);
    }

    private static ChatTagsWindow getTagsWindow() {
        return getPortalTouchPreferencesPage().getChatTagsWindow();
    }
}
