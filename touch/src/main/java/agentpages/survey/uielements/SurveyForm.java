package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyForm extends AbstractUIElement {

    @FindBy(css = ".cl-toggle")
    private List<WebElement> formElement;

    @FindBy(css = ".cl-preview-box .survey-form-title")
    private WebElement surveyPreviewTitle;

    @FindBy(css = ".preview-message.preview-message--agent-message")
    private List<WebElement> previewMessage;

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveSurveyButton;

    @FindBy(xpath = ".//div[@aria-hidden='false']//label[contains(text(), 'CSAT')]//span")
    private WebElement csatRadioButton;

    @FindBy(xpath = ".//div[@aria-hidden='false']//label[contains(text(), 'NPS')]//span")
    private WebElement npsRadioButton;
    private SurveysInner surveysInner;

    @FindBy(css = "div.cl-select__indicators.css-1wy0on6")
    private WebElement ratingNumbersDropdown;

    @FindBy(xpath = ".//div[contains(@id, 'react-select')]")
    private List<WebElement> ratingNumbersVariation;

    public SurveyForm clickToggleButton(String channelId) {
        waitFor(2500);
        try {
            if (isElementShown(this.getCurrentDriver(), this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group__header')]//label[contains(text(), 'CSAT')]")), 5)) {
                System.out.println("Toggle button is already clicked");
            }
        }
        catch (Exception e) {
            WebElement toggleButton = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//div"));
            moveToElemAndClick(this.getCurrentDriver(), toggleButton);
        }
        return this;
    }

    public SurveyForm clickExpandButton(String channelId) {
        try {
            if (isElementShown(this.getCurrentDriver(), this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group__header')]//label[contains(text(), 'CSAT')]")), 5)) {
                System.out.println("Channel is already expanded");
            }
        }
        catch (Exception e) {
            WebElement expandButton = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group__header')]"));
            moveToElemAndClick(this.getCurrentDriver(), expandButton);
        }
        return this;
    }

    public SurveyForm clickCSATRadioButton(String channelId) {
        waitFor(2500);
        WebElement csatRadio = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group')]//label[contains(text(), 'CSAT')]"));
        executeJSclick(this.getCurrentDriver(), csatRadio);
        return this;
    }

    public SurveyForm clickNPSRadioButton(String channelId) {
        waitFor(3500);
        WebElement npsRadio = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group')]//label[contains(text(), 'NPS')]"));
        executeJSclick(this.getCurrentDriver(), npsRadio);
        return this;
    }

    public SurveysInner getSurveysInner() {
        surveysInner.setCurrentDriver(this.getCurrentDriver());
        return surveysInner;
    }

    public String getSurveyPreviewTitle() {
        return getTextFromElem(this.getCurrentDriver(), surveyPreviewTitle, 3, "Survey preview title");
    }

    public List<String> getAllMessagesInSurveyPreview(String channelId) {
        List<WebElement> previewMessage = this.getCurrentDriver().findElements(By.xpath("//label[contains(@for, '" + channelId + "')]//ancestor::div[contains(@class,'setting-group__collapse')]//div[contains(@class,'preview-message preview-message--agent-message')]"));
        return previewMessage.stream()
                .map(message -> getTextFromElem(this.getCurrentDriver(), message, 3, "Survey preview message"))
                .collect(Collectors.toList());
    }

    public void clickSaveButton(String channelId) {
        WebElement saveSurveyButton = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '"+ channelId +"')]//ancestor::div[contains(@class,'setting-group__collapse-wrapper')]//button[text()='Save configuration']"));
        if (isElementEnabled(this.getCurrentDriver(), saveSurveyButton, 2) == true) {
            clickElem(this.getCurrentDriver(), saveSurveyButton, 2, "Save Survey Button");
        } else {
            System.out.println("Value is already saved");
        }
    }

    public List<String> getVariationOfRatingCSATScale() {
        List<String> ratingNums;
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown");
        waitForElementsToBeVisible(this.getCurrentDriver(), ratingNumbersVariation, 2);
        ratingNums = ratingNumbersVariation.stream().map(e -> e.getText()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 2, "Rating number dropdown");
        return ratingNums;
    }

    public void selectDropdownOption(String number, String channelId) {
        WebElement ratingNumbersDropdown = this.getCurrentDriver().findElement(By.xpath("//label[contains(@for, '"+ channelId +"')]//ancestor::div[contains(@class,'setting-group__collapse-wrapper')]//div[contains(@class,'cl-select__indicators css-1wy0on6')]"));
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 20, "Rating number dropdown");

        waitFor(5000);
        getCurrentDriver().findElements(By.xpath(".//div[contains(@id, 'react-select')]")).stream().filter(e -> e.getText().trim().equals(number)).findFirst()
                .orElseThrow(() -> new AssertionError(number + " number was not found in dropdown.")).click();
    }

}