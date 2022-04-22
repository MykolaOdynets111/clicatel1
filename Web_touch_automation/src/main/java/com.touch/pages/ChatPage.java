package com.touch.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Created by kmakohoniuk on 3/14/2017.
 */
public abstract class ChatPage extends PageObject {
    protected final String allCardXpath = "//div[contains(@id,'card')]";
    protected final String messageFromXpath = "//*[@class='ctl-chat-message-container message-from']//div[@class='ctl-message-content-block-text-subject']";
    protected final String messageToXpath = "//*[@class='ctl-chat-message-container message-to']//div[@class='ctl-message-content-block-text-subject']";
    protected JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    @FindBy(xpath = "//div[@class='ctl-chat-container ctl-visible']//textarea")
    protected static WebElementFacade messageInput;
    @FindBy(css = ".scroller")
    protected static WebElementFacade scroller;

    public ChatPage(WebDriver driver) {
        super(driver);
    }
    public void goDownToLastCard() {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        WebElementFacade element = all.get(all.size() - 1);
        scrollDownUntilVisible(element, 20, 50);
    }

    public void goDownToTheBottom() {
        WebElementFacade scroller = find(By.cssSelector(".scroller"));
        int height = scroller.getSize().getHeight();
        Integer y = -1 * (height - 300);
        jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + y + "px) translateZ(0px)\"");
    }
    protected void searchElementAndScrollUPDownToIt(WebElementFacade element, int attempts, int step) {
        scrollUpUntilVisible(element, attempts, step);
        scrollDownUntilVisible(element, attempts * 2, step);
    }
    protected void searchElementAndScrollDownUpToIt(WebElementFacade element, int attempts, int step) {
        scrollDownUntilVisible(element, attempts, step);
        scrollUpUntilVisible(element, attempts * 2, step);
    }
    protected void scrollChatHistoryUp() {
        Integer yPosition = Integer.valueOf(getCurrentScrollPositionY()) + 50;
        jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + yPosition + "px) translateZ(0px)\"");
    }

    protected void scrollUpUntilVisible(WebElementFacade element, int attempts) {
        Integer y = Integer.valueOf(getCurrentScrollPositionY());

        while (!element.isVisible() && attempts > 0 && Integer.valueOf(getCurrentScrollPositionY()) < 0) {
            y += 100;
            jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + y + "px) translateZ(0px)\"");
            attempts--;
        }
    }

    protected void scrollUpUntilVisible(WebElementFacade element, int attempts, int step) {
        Integer y = Integer.valueOf(getCurrentScrollPositionY());

        while (!element.isVisible() && attempts > 0 && Integer.valueOf(getCurrentScrollPositionY()) < 0) {
            y += step;
            jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + y + "px) translateZ(0px)\"");
            attempts--;
        }
    }

    protected void scrollDownUntilVisible(WebElementFacade element, int attempts) {
        Integer y = Integer.valueOf(getCurrentScrollPositionY());
        WebElementFacade scroller = find(By.cssSelector(".scroller"));
        int height = scroller.getSize().getHeight();
        while (!element.isVisible() && attempts > 0 && Integer.valueOf(getCurrentScrollPositionY()) < height - 150) {
            y -= 200;
            jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + y + "px) translateZ(0px)\"");
            attempts--;
        }
    }

    protected void scrollDownUntilVisible(WebElementFacade element, int attempts, int step) {
        Integer y = Integer.valueOf(getCurrentScrollPositionY());
        WebElementFacade scroller = find(By.cssSelector(".scroller"));
        int height = scroller.getSize().getHeight();
        while (!element.isVisible() && attempts > 0 && Integer.valueOf(getCurrentScrollPositionY()) > -1*(height - 300)) {
            y -= step;
            jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + y + "px) translateZ(0px)\"");
            attempts--;
        }
    }

    protected void setScrollToPosition(String position) {
        jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + position + "px) translateZ(0px)\"");
    }

    protected void scrollChatHistoryDown() {
        Integer yPosition = Integer.valueOf(getCurrentScrollPositionY()) - 50;
        jse.executeScript("document.querySelector('.scroller').style.transform = \"translate(0px, " + yPosition + "px) translateZ(0px)\"");
    }
    protected String getCurrentScrollPositionY() {
        String style = scroller.getAttribute("style");
        String[] sp1 = style.split("translate\\(.*px,");
        String positionY = sp1[1].split("px\\)")[0].trim();
        return positionY;
    }

}
