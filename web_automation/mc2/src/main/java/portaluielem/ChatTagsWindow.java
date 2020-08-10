package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-chat-tags-page")
public class ChatTagsWindow extends BasePortalWindow{

    @FindBy(css = ".cl-add-button")
    private WebElement addChatTagButton;

    @FindBy(css =".cl-r-form-control--input-primary")
    private WebElement nameInput;

    @FindBy(css =".cl-tag-form__save-button")
    private WebElement saveButton;

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




}
