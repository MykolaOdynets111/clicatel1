package com.touch.pages.cards;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Created by kmakohoniuk on 11/23/2016.
 */
public class CalendarCardElement extends PageObject {

    public void clickOnDay(String dayNumber) {
        find(By.xpath("//*[@id='calendar']//td[text()='"+dayNumber+"']")).click();
        new WaitForPageToLoad();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
