package com.touch.pages;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.util.ArrayList;
import java.util.List;

public class ChatRoomPage extends PageObject {
    private final String allCardXpath = "//div[contains(@id,'card')]";
    private final String messageFromXpath = "//*[@class='ctl-chat-message-container message-from']//div[@class='ctl-message-content-block-text-subject']";
    private final String messageToXpath = "//*[@class='ctl-chat-message-container message-to']//div[@class='ctl-message-content-block-text-subject']";

    @FindBy(xpath = "//div[@class='ctl-chat-container ctl-visible']//textarea")
    public static WebElementFacade messageInput;

    public ChatRoomPage(WebDriver driver) {
        super(driver);
    }

    public void setMessageToChatAndClickEnter(String value) {
        messageInput.typeAndEnter(value);
        new WaitForPageToLoad().setDefaultTimeout(30000);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnButtonWithText(String text) {
        WebElementFacade button = find(By.xpath("//button/span[text()=\"" + text + "\"]"));
        button.waitUntilClickable();
        button.click();
        new WaitForPageToLoad().setDefaultTimeout(30000);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setLoginAndPasswordInCard(String login, String password) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> inputs = findAll(By.xpath("//div/input[ancestor::*[@id='" + id + "']]"));
        Assert.assertEquals("Wrong CardModel with other amount of input fields", inputs.size(), 2);
        inputs.get(0).type(login);
        inputs.get(1).type(password);
        new WaitForPageToLoad();

    }

    public String getTitleFromLastCard() {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(0).waitUntilVisible();
        return titles.get(0).getText();
    }

    public String getTitleFromPreviousCard() {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 2).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(0).waitUntilVisible();
        return titles.get(0).getText();
    }
    public String getTitleFromCardWithNumberFromEnd(int number) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - number).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(0).waitUntilVisible();
        return titles.get(0).getText();
    }

    public List<String> getListOfInformationRowsFromLastCard() {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        for (int i = 1; i < titles.size(); i++) {
            list.add(titles.get(i).getText());
        }
        return list;
    }

    public List<String> getListOfInformationRowsFromCardWithNumberFromEnd(int number) {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - number).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        for (int i = 1; i < titles.size(); i++) {
            list.add(titles.get(i).getText());
        }
        return list;
    }
    public List<String> getListOfInformationRowsFromPreviousCard() {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 2).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        for (int i = 1; i < titles.size(); i++) {
            list.add(titles.get(i).getText());
        }
        return list;
    }
    public List<String> getListOfButtonsNameFromLastCard() {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> buttons = findAll(By.xpath("//button/span[ancestor::*[@id='" + id + "']]"));
        for (WebElementFacade button : buttons) {
            list.add(button.getText());
        }
        return list;
    }

    public List<String> getListOfButtonsNameFromPreviousCard() {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 2).getAttribute("id");
        List<WebElementFacade> buttons = findAll(By.xpath("//button/span[ancestor::*[@id='" + id + "']]"));
        for (WebElementFacade button : buttons) {
            list.add(button.getText());
        }
        return list;
    }
    public List<String> getListOfButtonsNameFromCardWithNumberFromEnd(int number) {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - number).getAttribute("id");
        List<WebElementFacade> buttons = findAll(By.xpath("//button/span[ancestor::*[@id='" + id + "']]"));
        for (WebElementFacade button : buttons) {
            list.add(button.getText());
        }
        return list;
    }
    public String getYourLastRequest() {
        List<WebElementFacade> all = findAll(By.xpath(messageFromXpath));
        return all.get(all.size() - 1).getText();
    }

    public String getLastResponceMessage() {
        List<WebElementFacade> all = findAll(By.xpath(messageToXpath));
        return all.get(all.size() - 1).getText();
    }

    public String getPreviousResponceMessage() {
        List<WebElementFacade> all = findAll(By.xpath(messageToXpath));
        return all.get(all.size() - 2).getText();
    }

    public boolean isAccountInformationCardAppeared() {
        return findAll(By.xpath("//div/span[text()='Account Information']")).size() > 0;
    }

    public void selectRateInLastCard(String rate) {
        if (0 < Integer.valueOf(rate) && Integer.valueOf(rate) < 6) {
            List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
            String id = all.get(all.size() - 1).getAttribute("id");
            WebElementFacade rateButtons = find(By.xpath("//*[@id='" + id + "']//div[" + rate + "]/div[2][descendant::img][@role='img']"));
            rateButtons.click();
            new WaitForPageToLoad();
        }
    }

    public void setValueToFirstInputFieldInLastCard(String value) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - 1).getAttribute("id");
        List<WebElementFacade> inputs = findAll(By.xpath("//div/input[ancestor::*[@id='" + id + "']]"));
        inputs.get(0).type(value);
    }
}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
