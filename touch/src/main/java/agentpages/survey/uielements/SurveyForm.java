package agentpages.survey.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FindBy(xpath = "//div[@class='page-wrapper']//div[contains(@class, 'cl-fetch-states')]")
public class SurveyForm extends AbstractUIElement {

    @FindBy(xpath = "//div[@class='setting-group']//div[contains(@class, 'setting-group__secondary-control--toggle')]//label")
    private List<WebElement> toggleButtons;

    /*@FindBy(xpath = "//div[@class='setting-group__collapse-wrapper']//div[@class='survey-form-row survey-type-container']//label")
    private List<WebElement> collapseChannelHeaders;*/

    @FindBy(css = ".cl-preview-box .survey-form-title")
    private WebElement surveyPreviewTitle;

    @FindBy(css = ".preview-message.preview-message--agent-message")
    private List<WebElement> previewMessage;

    @FindBy(xpath = ".//button[text()='Save configuration']")
    private WebElement saveSurveyButton;

    @FindBy(xpath = "//div[@class='setting-group__collapse-wrapper']//div[@class='survey-form-row survey-type-container']//label[contains(text(), 'CSAT')]")
    private List<WebElement> csatRadioButton;

    @FindBy(xpath = "//div[@class='setting-group__collapse-wrapper']//div[@class='survey-form-row survey-type-container']//label[contains(text(), 'NPS')]")
    private List<WebElement> npsRadioButton;

    @FindBy(xpath = "//div[@aria-hidden='false']//div[@class='cl-select__indicators css-1wy0on6']")
    private WebElement ratingNumbersDropdown;

    @FindBy(xpath = ".//div[contains(@id, 'react-select')]")
    private List<WebElement> ratingNumbersVariation;

    @FindBy(xpath = "//div[@aria-hidden='false']//button[text()='Save configuration']")
    private WebElement saveButtonElement;

    private String collapseChannelHeaders = "//label[contains(@for, '%s')]//ancestor::div[@class='ReactCollapse--collapse']";
    private String expandButtonElement = "//label[contains(@for, '%s')]//following::button";

    public SurveyForm clickToggleButton(String channelId) {
        waitFor(2500);
        if (npsRadioButton.stream().filter(nps -> nps.getAttribute("for").contains(channelId)).findFirst().get().isEnabled()) {
            System.out.println("Toggle button is already clicked");
        } else {
            WebElement toggleButton = toggleButtons.stream().filter(toggle -> toggle.getAttribute("for").contains(channelId)).findFirst().get();
            moveToElemAndClick(this.getCurrentDriver(), toggleButton);
        }
        return this;
    }

    public SurveyForm clickExpandButton(String channelId) {
        /*WebElement element = collapseChannelHeaders.stream().filter(nps -> nps.getAttribute("for").contains(channelId)).findFirst().get();
        if (element.findElement(By.xpath("//ancestor::div[@class='ReactCollapse--collapse']")).getAttribute("aria-hidden").trim().contains("false")) {
            System.out.println("Channel is already expanded");
        } else {
            WebElement expandButton = toggleButtons.stream().filter(nps -> nps.getAttribute("for").contains(channelId)).findFirst()
                    .get().findElement(By.xpath("//following::button"));
            moveToElemAndClick(this.getCurrentDriver(), expandButton);
        }*/
        WebElement element = findElemByXPATH(this.getCurrentDriver(), String.format(collapseChannelHeaders, channelId));
        if (element.getAttribute("aria-hidden").trim().contains("false")) {
            System.out.println("Channel is already expanded");
        } else {
            WebElement expandButton = findElemByXPATH(this.getCurrentDriver(), String.format(expandButtonElement, channelId));
            moveToElemAndClick(this.getCurrentDriver(), expandButton);
        }
        return this;
    }

    public SurveyForm clickCSATRadioButton(String channelId) {
        waitFor(2500);
        WebElement csatRadio = csatRadioButton.stream().filter(e -> e.getAttribute("for").contains(channelId)).findFirst().get();
        executeJSclick(this.getCurrentDriver(), csatRadio);
        return this;
    }

    public SurveyForm clickNPSRadioButton(String channelId) {
        waitFor(3500);
        WebElement npsRadio = npsRadioButton.stream().filter(e -> e.getAttribute("for").contains(channelId)).findFirst().get();
        executeJSclick(this.getCurrentDriver(), npsRadio);
        return this;
    }

    public String getSurveyPreviewTitle() {
        return getTextFromElem(this.getCurrentDriver(), surveyPreviewTitle, 3, "Survey preview title");
    }

    public List<String> getAllMessagesInSurveyPreview(String channelId) {
        List<String> messages = new ArrayList<>();
        for (WebElement elem : previewMessage) {
            messages.add(elem.getText());
        }
        return messages;
    }

    public void clickSaveButton(String channelId) {
        if (isElementEnabled(this.getCurrentDriver(), saveButtonElement, 2) == true) {
            clickElem(this.getCurrentDriver(), saveButtonElement, 2, "Save Survey Button");
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
        clickElem(this.getCurrentDriver(), ratingNumbersDropdown, 20, "Rating number dropdown");

        waitFor(5000);
        ratingNumbersVariation.stream().filter(e -> e.getText().trim().equals(number)).findFirst()
                .orElseThrow(() -> new AssertionError(number + " number was not found in dropdown.")).click();
    }

}