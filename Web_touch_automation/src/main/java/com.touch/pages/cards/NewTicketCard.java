package com.touch.pages.cards;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import java.util.List;

/**
 * Created by kmakohoniuk on 11/23/2016.
 */
public class NewTicketCard extends PageObject {
    private final String allCardXpath = "//div[contains(@id,'card')]";
    @FindBy(xpath="//div/span[text()='Subject']/../../input")
    private WebElementFacade subjectInput;
    @FindBy(xpath="//div/span[text()='Description']/../../textarea")
    private WebElementFacade descriptionInput;
    @FindBy(xpath="//div/span[text()='Email']/../../input")
    private WebElementFacade emailInput;
    @FindBy(xpath="//div/span[text()='Contact number']/../../input")
    private WebElementFacade contactNumberInput;
    @FindBy(xpath="//div/span[text()='High']/../div")
    private WebElementFacade highrPriorityRB;
    @FindBy(xpath="//div/span[text()='Medium']/../div")
    private WebElementFacade mediumPriorityRB;
    @FindBy(xpath="//div/span[text()='Low']/../div")
    private WebElementFacade lowPriorityRB;
    @FindBy(xpath="//button/span[text()='Create']")
    private WebElementFacade createButton;
    @FindBy(xpath="//button/span[text()='Cancel']")
    private WebElementFacade cancelButton;

    public String getTitleFromLastCard() {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(0).waitUntilVisible();
        return titles.get(0).getText();
    }

    public void setSubjectInput(String value) {
        subjectInput.type(value);
    }

    public void setDescriptionInput(String value) {
        descriptionInput.type(value);
    }

    public void setEmailInput(String value) {
        emailInput.type(value);
    }

    public void setContactNumberInput(String value) {
        contactNumberInput.type(value);
    }

    public void clickOnHighPriorityRB() {
        highrPriorityRB.click();
    }

    public void clickOnMediumPriorityRB() {
        mediumPriorityRB.click();
    }

    public void clickOnLowPriorityRB() {
        lowPriorityRB.click();
    }

    public void clickOnCreateButton() {
        createButton.click();
        new WaitForPageToLoad();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnCancelButton() {
        cancelButton.click();
        new WaitForPageToLoad();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
