package com.touch.pages.cards;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 11/23/2016.
 */
public class TicketsCard extends PageObject {
    private final String allCardXpath = "//div[contains(@id,'card')]";


    public String getTitleFromCardWithNumberFromEnd(int number) {
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - number).getAttribute("id");
        List<WebElementFacade> titles = findAll(By.xpath("//div/span[ancestor::*[@id='" + id + "']]"));
        titles.get(0).waitUntilVisible();
        return titles.get(0).getText();
    }
    public List<Ticket> getTicketListFromCardWithNumberFromEnd(int number){
        List<Ticket> tickets = new ArrayList<>();
        List<WebElementFacade> all = findAll(By.xpath(allCardXpath));
        String id = all.get(all.size() - number).getAttribute("id");
        List<WebElementFacade> items = findAll(By.xpath("//div[@id='" + id + "']/div/div/div"));
//        all.get(0).thenFindAll()
        for(int i=1;i<items.size();i++){
            int row=i+1;
            String subject = find(By.xpath("//div[@id='" + id + "']/div/div/div[" + row + "]/div[1]/span[1]")).getText();
            String status = find(By.xpath("//div[@id='" + id + "']/div/div/div[" + row + "]/div/span/span")).getText();
            String description = find(By.xpath("//div[@id='" + id + "']/div/div/div[" + row + "]/div[2]/span[1]")).getText();
            String ticketNumber = find(By.xpath("//div[@id='" + id + "']/div/div/div[" + row + "]/div[3]/span[1]")).getText().split(": ")[1];
            String assignTo = find(By.xpath("//div[@id='" + id + "']/div/div/div[" + row + "]/div[3]/span[2]")).getText().split(": ")[1];
            tickets.add(new Ticket(subject,status,description,ticketNumber,assignTo));
        }
        return tickets;
    }




}
