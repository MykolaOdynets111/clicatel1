package portaluielem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".cl-chat-tags-page")
public class ChatTagsWindow extends BasePortalWindow{

    @FindBy(css = "[class='cl-button cl-button--secondary cl-add-button']")
    private WebElement addChatTagButton;

    @FindBy(css = "[type='text']")
    private WebElement nameInput;

    @FindBy(css ="[class='cl-button cl-button--reset-only cl-tag-form__save-button']")
    private WebElement saveButton;

    @FindBy(css= "g[id='Delete / Outline']")
    private WebElement deleteButton;

    @FindBy(css = ".cl-collapsible-table__row")
    private List<WebElement> tagRows;

    @FindBy(css = ".cl-tag-form__pencil-icon")
    private WebElement clickPencilIcon;

    @FindBy(xpath = "//div[@class='cl-tag-form'][1]")
    private WebElement getTagName;


    public ChatTagsWindow clickAddChatTagButton() {
        clickElem(this.getCurrentDriver(), addChatTagButton, 5, "Add Chat Tag Button");
        return this;
    }

    public ChatTagsWindow setTagName(String tagName){
        inputText(this.getCurrentDriver(), nameInput, 5, "Name field", tagName);
        return this;
    }

    public ChatTagsWindow clickSaveButton(){
        clickElem(this.getCurrentDriver(), saveButton, 2, "Save Button");
        return this;
    }
    public ChatTagsWindow clickDeleteButton(){
        clickElem(this.getCurrentDriver(), deleteButton, 2, "Delete Button");
        return this;
    }
    public String getTagName(){
        return getTextFromElem(this.getCurrentDriver(), getTagName, 2, "Tag Name");
    }


    private WebElement getRowByName(String tagName){
        waitForElementsToBeVisible(this.getCurrentDriver(), tagRows, 5);
        return tagRows.stream().filter(e -> e.getText().contains(tagName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" +tagName +"' tag."));
    }

    public ChatTagsWindow clickEditTagButton(String tagName){
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        moveToElemAndClick(this.getCurrentDriver(), clickPencilIcon);
        return this;
    }

    public ChatTagsWindow enableDisableTag(String tagName){
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        row.findElement(By.cssSelector(".cl-r-toggle-btn")).click();
        return this;
    }

}
