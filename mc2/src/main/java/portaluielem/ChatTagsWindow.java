package portaluielem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = ".cl-chat-tags-page")
public class ChatTagsWindow extends BasePortalWindow{

    @FindBy(css = ".cl-add-button")
    private WebElement addChatTagButton;

  /*  @FindBy(css =".cl-r-form-control--input-primary")
    private WebElement nameInput;
    */
  @FindBy(css =".cl-form-control--input-primary")
  private WebElement nameInput;

    @FindBy(css =".cl-tag-form__save-button")
    private WebElement saveButton;

    @FindBy(css ="#Delete\\ \\/\\ Outline")
    private WebElement deleteButton;

    @FindBy(css = ".cl-collapsible-table__row")
    private List<WebElement> tagRows;

    @FindBy(css = ".cl-collapsible-table__empty-message")
    private WebElement emptyChatTags;

    @FindBy(css = "tr.cl-collapsible-table__row:first-of-type")
    private WebElement firstChatTagFromAll;

    @FindBy(css = "tr.cl-collapsible-table__row:first-of-type")
    private WebElement firstChatTagFromExisting;



    @FindBy(css = "[data-testid='tab-navigation-panel-business-profile']")
    private WebElement businessProfile;

    @FindBy(css = "[data-testid='tab-navigation-panel-chat-tags']")
    private WebElement chatTags;

    @FindBy(css = "[data-testid='tab-navigation-panel-auto-responders']")
    private WebElement autoResponders;

    @FindBy(css = "[data-testid='tab-navigation-panel-preferences']")
    private WebElement preferences;

    @FindBy(css = "[selenium-id='tab-navigation-panel-surveys']")
    private WebElement surveysNavigation;



    public ChatTagsWindow clickAddChatTagButton() {
        clickElem(this.getCurrentDriver(), addChatTagButton, 2, "Add Chat Tag Button");
        return this;
    }

    public ChatTagsWindow setTagName(String tagName){
        clickElem(this.getCurrentDriver(), nameInput, 3,"Add chat tags text field");
        inputText(this.getCurrentDriver(), nameInput, 2, "Name field", tagName);
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

    private WebElement getRowByName(String tagName){
        return tagRows.stream().filter(e -> e.getText().contains(tagName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find '" +tagName +"' tag."));
    }

    public ChatTagsWindow clickEditTagButton(String tagName){
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        System.out.println("The value for tagname before clicking Edit button is "+tagName);
        row.findElement(By.cssSelector(".cl-tag-form__pencil-icon")).click();
        System.out.println("Enough Waiting");
     //   row.findElement(By.xpath("//*[@id=\"Edit\"]/ancestor::button/parent::div[text()='tagName']")).click();

        return this;
    }

    public ChatTagsWindow enableDisableTag(String tagName){
        WebElement row = getRowByName(tagName);
        moveToElement(this.getCurrentDriver(), row);
        row.findElement(By.cssSelector(".cl-r-toggle-btn")).click();
        return this;
    }



/*
    public String verifyChatTagsExistance(String a){

         a= "Add Chat Tags to your tenant";
  //     inputText(this.getCurrentDriver(), nameInput, 2, "Name field", tagName);
       //    getTextFromElem(this.getCurrentDriver(), emptyChatTags, 2, "Add Chat Tags to your tenant"));

     */
/*  String x= this.getCurrentDriver().findElement(By.cssSelector.cl-collapsible-table__empty-message).getText();
       System.out.println("Value is  ::  "+x);*//*


        this.getCurrentDriver().findElement(By.emptyChatTags).getText();

        return x;
    }
*/


/*

    public void verifyChatTagsExistance() {
       //  waitForElementToBeVisible(this.getCurrentDriver(), emptyChatTags, 7);


      //  String x= this.getCurrentDriver().findElement(By.cssSelector.cl-collapsible-table__empty-message).getText();

        Assert.assertEquals(emptyChatTags.getText(),"Add Chat Tags to your tenant","Warning message");

    }
*/


    public boolean verifyNoChatTagsExistance(){
        return isElementShown(this.getCurrentDriver(), emptyChatTags, 3);
    }

    public String verifyChatTagsExistance(String tagname){
        if(verifyNoChatTagsExistance())
        {
            System.out.println("There are no chat tags , we need to create one first");
            clickAddChatTagButton().setTagName(tagname).clickSaveButton();

        }
        else{
            tagname = getTextFromElem(this.getCurrentDriver(),firstChatTagFromAll,1, "first Chat Tag From All the tags present");
            System.out.println("Value for the first chat tag name is :: "+tagname);

        }
        System.out.println("The value of chat tag before the update is "+tagname);

        return tagname;

    }

    public String copyFirstChatTag(){

        return getTextFromElem(this.getCurrentDriver(),firstChatTagFromAll,1, "first Chat Tag From All the tags present");

    }




    public boolean isBusinessProfileTabShown() {
        return isElementShown(this.getCurrentDriver(), businessProfile, 5);
    }

    public boolean isChatTagsTabShown() {
        return isElementShown(this.getCurrentDriver(), chatTags, 5);
    }

    public boolean isAutoRespondersTabShown() {
        return isElementShown(this.getCurrentDriver(), autoResponders, 5);
    }

    public boolean isPreferencesTabShown() {
        return isElementShown(this.getCurrentDriver(), preferences, 5);
    }

    public boolean isSurveysTabShown() {
        return isElementShown(this.getCurrentDriver(), surveysNavigation, 5);
    }





}
