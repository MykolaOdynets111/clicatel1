package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import com.sun.jna.WString;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

@FindBy(css = ".ReactModal__Content")
public class HSMForm extends AbstractUIElement {

    @FindBy(name = "waPhone")
    private WebElement waPhoneField;

    @FindBy(xpath = ".//label[contains(text(),'Template')]/parent::div//div[contains(@class,'cl-select__indicators')]")
    private WebElement templateDropDown;

    @FindBy(css = ".cl-select__option")
    private List<WebElement> hsmDropDownOPtions;

    @FindBy(name = "parameters.0")
    private WebElement varibleInsertinWhatsapp;

    @FindBy(css ="[type='submit']")
    private WebElement sendButton;

    @FindBy(css =".cl-option-with-icon svg")
    private WebElement appleImageIcon;

    public HSMForm setWAPhoneNumber(String variable) {
        inputText(this.getCurrentDriver(), waPhoneField, 5, "Send to field", variable);
        return this;
    }
    public HSMForm selectTemplate(String templateName){
        clickElem(this.getCurrentDriver(), templateDropDown, 1, "Template Dropdown");
        hsmDropDownOPtions.stream().filter(e -> e.getText().equalsIgnoreCase(templateName))
                .findFirst().orElseThrow(() ->
                        new AssertionError(templateName + " Template is not found")).click();
        return this;
    }

    public boolean isValidChannelImg(String channelPictureName) {
        File image = new File(System.getProperty("user.dir") + "/src/test/resources/adaptericons/" + channelPictureName +".jpg");
        return isWebElementEqualsImage(this.getCurrentDriver(), appleImageIcon, image);
    }

    public HSMForm insertVariableForWhatsapp(String variable) {
        inputText(this.getCurrentDriver(), varibleInsertinWhatsapp, 5, "Varible Numbver Insert", variable);
        return this;
    }

    public void clickSendButton(){
        clickElem(this.getCurrentDriver(), sendButton, 10, "Send button");
    }

    public String getContactNum() {
      String fetchedContactNum =   getAttributeFromElem(this.getCurrentDriver(), waPhoneField, 3, "wa phone number", "value");
      return fetchedContactNum;
    }

}