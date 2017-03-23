package com.touch.pages.cards;

import com.touch.pages.ChatPage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

/**
 * Created by kmakohoniuk on 3/14/2017.
 */
public class ContactDetailsCard extends ChatPage {
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElementFacade firstNameInput;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElementFacade lastNameInput;
    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElementFacade emailInput;
    @FindBy(xpath = "//input[@placeholder='Contact Number']")
    private WebElementFacade contactNumberInput;
    @FindBy(xpath = "//button/span[text()='Submit']")
    private WebElementFacade submitButton;

    public ContactDetailsCard(WebDriver driver) {
        super(driver);
    }

    public void setFirstNameInput(String value) {
        searchElementAndScrollDownUpToIt(firstNameInput,20,20);
        firstNameInput.type(value);
    }

    public void setLastNameInput(String value) {
        searchElementAndScrollDownUpToIt(lastNameInput,20,20);
        lastNameInput.type(value);
    }

    public void setEmailInput(String value) {
        searchElementAndScrollDownUpToIt(emailInput,20,20);
        emailInput.type(value);
    }

    public void setContactNumberInput(String value) {
        searchElementAndScrollDownUpToIt(contactNumberInput,20,20);
        contactNumberInput.type(value);
    }

    public void clickOnSubmitButton() {
        searchElementAndScrollDownUpToIt(submitButton,20,20);
        submitButton.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
