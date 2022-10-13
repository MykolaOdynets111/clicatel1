package portaluielem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

@FindBy(css = ".cl-chat-tags-page")
public class ChatTagsWindow extends BasePortalWindow {

    @FindBy(css = "[class='cl-button cl-button--secondary cl-add-button']")
    private WebElement addChatTagButton;

    @FindBy(css = "[type='text']")
    private WebElement nameInput;

    @FindBy(css = "[class='cl-button cl-button--reset-only cl-tag-form__save-button']")
    private WebElement saveButton;

    @FindBy(css = "g[id='Delete / Outline']")
    private WebElement deleteButton;

    @FindBy(css = ".cl-collapsible-table__row")
    private List<WebElement> tagRows;

    @FindBy(xpath = "//div[@class='cl-tag-form'][1]")
    private WebElement getTagName;

    @FindBy(xpath = "//table[contains(@class, 'collapsible-table')]")
    private WebElement tagsTable;

    private List<WebElement> getTableHeaders() {
        return tagsTable.findElements(xpath("./thead//button//span"));
    }

    private WebElement getHeaderByName(String column) {
        return getTableHeaders().stream().filter(c -> c.getText().equals(column))
                .findFirst().orElseThrow(() -> new NoSuchElementException("There no column with title " + column));
    }

    private WebElement getSortButtonForColumn(String column, String sortingType) {
        return getHeaderByName(column).findElement(xpath(String
                .format("./..//div[contains(@class, 'cl-arrow') and contains(@data-testid, '%s')]", sortingType)));
    }

    private List<List<WebElement>> getAllElementsOfTheTable() {
        return tagsTable.findElements(xpath("./tbody/tr"))
                .stream().map(l -> l.findElements(xpath("./td[contains(@class, 'cell')]")))
                .collect(Collectors.toList());
    }

    public List<String> getColumnValueList(String column) {
        int headerNumber = getTableHeaders().stream().map(WebElement::getText)
                .collect(Collectors.toList())
                .indexOf(column);
        return getAllElementsOfTheTable().stream()
                .map(l -> l.get(headerNumber).getText())
                .collect(Collectors.toList());
    }

    public String getCellValue(String column, String tag) {
        int tagNumber = getColumnValueList("Tag Name").indexOf(tag);

        return getColumnValueList(column).get(tagNumber);
    }

    public void clickSortedColumn(String column, String sortingType) {
        while (!getSortButtonForColumn(column, sortingType).getAttribute("class").contains("active")) {
            getSortButtonForColumn(column, sortingType).click();
        }
    }

    public ChatTagsWindow clickAddChatTagButton() {
        clickElem(this.getCurrentDriver(), addChatTagButton, 5, "Add Chat Tag Button");
        return this;
    }

    public ChatTagsWindow setTagName(String tagName) {
        inputText(this.getCurrentDriver(), nameInput, 5, "Name field", tagName);
        return this;
    }

    public void clickSaveButton() {
        clickElem(this.getCurrentDriver(), saveButton, 2, "Save Button");
    }

    public void clickDeleteButton() {
        clickElem(this.getCurrentDriver(), deleteButton, 2, "Delete Button");
    }

    public String getTagName() {
        return getTextFromElem(this.getCurrentDriver(), getTagName, 2, "Tag Name");
    }

    private WebElement getRowByName(String tagName) {
        waitForElementsToBeVisible(this.getCurrentDriver(), tagRows, 5);
        return tagRows.stream().filter(e -> e.getText().contains(tagName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" + tagName + "' tag."));
    }

    public ChatTagsWindow clickEditTagButton(String tagName) {
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        row.findElement(cssSelector(".cl-tag-form__pencil-icon")).click();
        return this;
    }

    public void enableDisableTag(String tagName) {
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        row.findElement(By.cssSelector(".cl-r-toggle-btn")).click();
    }
}
