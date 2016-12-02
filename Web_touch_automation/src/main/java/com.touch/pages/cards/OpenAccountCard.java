package com.touch.pages.cards;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Created by kmakohoniuk on 11/23/2016.
 */
public class OpenAccountCard extends PageObject {
    @FindBy(xpath="//div/span[text()='Name']/../../input")
    private WebElementFacade nameInput;
    @FindBy(xpath="//div/span[text()='Surname']/../../input")
    private WebElementFacade surnameInput;
    @FindBy(xpath="//div/span[text()='Email']/../../input")
    private WebElementFacade emailInput;
    @FindBy(xpath="//div/span[text()='Contact number']/../../input")
    private WebElementFacade contactNumberInput;
   @FindBy(xpath="//button/span[text()='Submit']")
    private WebElementFacade submitButton;


    public void setNameInput(String value) {
        nameInput.type(value);
    }

    public void setSurnameInput(String value) {
        surnameInput.type(value);
    }

    public void setEmailInput(String value) {
        emailInput.type(value);
    }

    public void setContactNumberInput(String value) {
        contactNumberInput.type(value);
    }

    public void clickOnSubmitButton() {
        submitButton.click();
        new WaitForPageToLoad();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
