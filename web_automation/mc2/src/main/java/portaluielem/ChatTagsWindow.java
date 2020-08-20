package portaluielem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".cl-chat-tags-page")
public class ChatTagsWindow extends BasePortalWindow{

    @FindBy(css = ".cl-add-button")
    private WebElement addChatTagButton;

    @FindBy(css =".cl-r-form-control--input-primary")
    private WebElement nameInput;

    @FindBy(css =".cl-tag-form__save-button")
    private WebElement saveButton;

    @FindBy(css = ".cl-collapsible-table__row")
    private List<WebElement> tagRows;

    public ChatTagsWindow clickAddChatTagButton() {
        clickElem(this.getCurrentDriver(), addChatTagButton, 2, "Add Chat Tag Button");
        return this;
    }

    public ChatTagsWindow setTagName(String tagName){
        inputText(this.getCurrentDriver(), nameInput, 2, "Name field", tagName);
        return this;
    }

    public ChatTagsWindow clickSaveButton(){
        clickElem(this.getCurrentDriver(), saveButton, 2, "Save Button");
        return this;
    }

    private WebElement getRowByName(String tagName){
        return tagRows.stream().filter(e -> e.getText().contains(tagName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" +tagName +"' tag."));
    }

    public ChatTagsWindow clickEditTagButton(String tagName){
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        row.findElement(By.cssSelector(".cl-tag-form__pencil-icon")).click();
        return this;
    }


}
