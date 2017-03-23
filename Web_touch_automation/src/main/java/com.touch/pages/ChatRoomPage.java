package com.touch.pages;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.*;


import java.util.ArrayList;
import java.util.List;

public class ChatRoomPage extends ChatPage {

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
        searchElementAndScrollUPDownToIt(button, 20, 50);
        button.waitUntilClickable();
        button.click();
        new WaitForPageToLoad().setDefaultTimeout(30000);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void clickOnRadioButtonWithText(String text) {
        WebElementFacade button = find(By.xpath("//div/span[text()='" + text + "']/../../div[1]"));
        searchElementAndScrollUPDownToIt(button, 20, 50);
        button.waitUntilClickable();
        button.click();
        new WaitForPageToLoad().setDefaultTimeout(30000);
        try {
            Thread.sleep(6000);
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
        searchElementAndScrollUPDownToIt(titles.get(0), 20, 100);
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
        WebElementFacade element = all.get(all.size() - number);
        String id = element.getAttribute("id");
        String startScrollPositionY = getCurrentScrollPositionY();
        scrollUpUntilVisible(element, 20);
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        scrollUpUntilVisible(titles.get(0), 20);
        String text = titles.get(0).getText();
//        setScrollToPosition(startScrollPositionY);
        return text;
    }

    public List<String> getListOfInformationRowsFromLastCard() {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        WebElementFacade element = all.get(all.size() - 1);
        String id = element.getAttribute("id");
        searchElementAndScrollUPDownToIt(element, 20, 100);
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        for (int i = 1; i < titles.size(); i++) {
            searchElementAndScrollDownUpToIt(titles.get(i), 20, 100);
            list.add(titles.get(i).getText());
        }
        return list;
    }


    public List<String> getListOfInformationRowsFromCardWithNumberFromEnd(int number) {
        ArrayList<String> list = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        WebElementFacade element = all.get(all.size() - number);
        String id = element.getAttribute("id");
        scrollUpUntilVisible(element, 20);
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        for (int i = 1; i < titles.size(); i++) {
            scrollUpUntilVisible(titles.get(i), 20, 20);
            list.add(titles.get(i).getText());
        }
//        setScrollToPosition(startScrollPositionY);
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
        WebElementFacade element = all.get(all.size() - 1);
        String id = element.getAttribute("id");
        searchElementAndScrollUPDownToIt(element, 20, 100);
        List<WebElementFacade> buttons = findAll(By.xpath("//button/span[ancestor::*[@id='" + id + "']]"));
        for (WebElementFacade button : buttons) {
            searchElementAndScrollDownUpToIt(button, 20, 50);
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
        WebElementFacade element = all.get(all.size() - number);
        String id = element.getAttribute("id");
        String startScrollPositionY = getCurrentScrollPositionY();
        scrollUpUntilVisible(element, 20);
        List<WebElementFacade> buttons = findAll(By.xpath("//button/span[ancestor::*[@id='" + id + "']]"));
        for (WebElementFacade button : buttons) {
            scrollUpUntilVisible(button, 20, 20);
            list.add(button.getText());
        }
//        setScrollToPosition(startScrollPositionY);
        return list;
    }

    public void openFaqDetailsForCardWithNumberFromEnd(int number) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        WebElementFacade element = all.get(all.size() - number);
        String id = element.getAttribute("id");
        String startScrollPositionY = getCurrentScrollPositionY();
        WebElementFacade openFaqButton = find(By.cssSelector("#" + id + " svg"));
        scrollUpUntilVisible(element, 20);
        openFaqButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void openDetailsForRowFromLastCard(String rowTitle) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        WebElementFacade element = all.get(all.size() - 1);
        String id = element.getAttribute("id");
        List<WebElementFacade> infoList = findAll(By.xpath("//*[@id='" + id + "']//span[1]"));
        int rowIndex = -1;
        for (int i = 1; i < infoList.size(); i++) {
            searchElementAndScrollDownUpToIt(infoList.get(i), 20, 100);
            if (infoList.get(i).getText().equals(rowTitle)) {
                rowIndex = i - 1;
                break;
            }
        }
        List<WebElementFacade> openRowDetailsList = findAll(By.cssSelector("#" + id + " svg"));
        WebElementFacade rowDetailsButton = openRowDetailsList.get(rowIndex);
        searchElementAndScrollDownUpToIt(rowDetailsButton, 20, 20);
        rowDetailsButton.click();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public String getYourLastRequest() {
        List<WebElementFacade> all = findAll(By.xpath(messageFromXpath));
        WebElementFacade element = all.get(all.size() - 1);
        String startScrollPositionY = getCurrentScrollPositionY();
//        Point location = element.getLocation();
//        setScrollToPosition(String.valueOf(location.getY() - 150));
        scrollUpUntilVisible(element, 20);
        String text = element.getText();
        setScrollToPosition(startScrollPositionY);
        return text;
    }


    public String getLastResponceMessage() {
        List<WebElementFacade> all = findAll(By.xpath(messageToXpath));
        searchElementAndScrollUPDownToIt(all.get(all.size() - 1), 20, 100);
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
            String test = "#" + id + " svg";
            List<WebElementFacade> ratesList = findAll(By.cssSelector("#" + id + " svg"));
            if (Integer.valueOf(rate) < ratesList.size())
                ratesList.get(Integer.valueOf(rate)-1).click();
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






















