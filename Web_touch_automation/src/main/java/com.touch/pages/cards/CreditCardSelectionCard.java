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
public class CreditCardSelectionCard extends PageObject {
    private final String allCardXpath = "//div[contains(@id,'card')]";

    @FindBy(xpath="//button/span[text()='Block']")
    private WebElementFacade blockButton;

    public void selectCreditCardWithNumbeInList(int number) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(number).click();
    }


    public void clickOnBlockButton() {
        blockButton.click();
        new WaitForPageToLoad();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
