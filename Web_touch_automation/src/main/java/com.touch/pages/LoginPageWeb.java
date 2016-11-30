package com.touch.pages;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

public class LoginPageWeb extends PageObject {

	public LoginPageWeb(WebDriver driver){
		super(driver);
	}

	@FindBy(xpath="//p[text()='General bank']/input")
	public static WebElementFacade generalBankRB;
	@FindBy(xpath="//p[text()='Motor Car Solutions']/input")
	public static WebElementFacade motorCarSolutionRB;
	@FindBy(xpath="//p[text()='Clickatell']/input")
	public static WebElementFacade clickatellRB;
	
	@FindBy(css=".ctl-chat-widget-btn-open")
	public static WebElementFacade openChatRoomButton;
	@FindBy(css=".ctl-chat-widget-btn-close")
	public static WebElementFacade closeChatRoomButton;
	@FindBy(xpath="//div[@class='ctl-chat-widget closed visible']/style")
	public static WebElementFacade styleOfIcon;


	public void clickOnClickatellRB(){
		clickatellRB.waitUntilClickable();
		clickatellRB.click();
	}
	public void clickOnGenbankRB(){
		generalBankRB.click();
	}
	public void clickOnTenantRBWithName(String name){
		find(By.xpath("//p[text()='"+name+"']/input")).click();
	}
	public void clickOnOpenChatRoomButton(){
		openChatRoomButton.waitUntilClickable();
		openChatRoomButton.click();
	}
	public void clickOnCloseChatRoomButton(){
		closeChatRoomButton.waitUntilClickable();
		closeChatRoomButton.click();
	}
	public void refreshBrowser(){
		getDriver().navigate().refresh();
		new WaitForPageToLoad();
	}

	
		
	}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
