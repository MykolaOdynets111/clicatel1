package portalpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalAccountDetailsPage extends PortalAbstractPage {

    // page_action_to_remove
   @FindBy(xpath = "//button[text()='Close account']")
   private WebElement closeAccountButton;

   @FindBy(xpath = "//span[text()='Close account?']")
   private WebElement closingConfirmationPopupHeader;

   @FindBy(css = "div.button-container button.button-primary")
   private WebElement closeAccountConfirmationButton;

    @FindBy(css = "div.button-container button.button-secondary")
    private WebElement cancelCloseAccountButton;

   @FindBy(xpath = "//span[text()='Account confirmation']")
   private WebElement accountConfirmationPopupHeader;

   @FindBy(name = "email")
   private WebElement confirmationEmailInput;

   @FindBy(name = "password")
   private WebElement confirmationPassInput;

   public void clickCloseAccountButton(){
       closeAccountButton.click();
   }

   public boolean isClosingConfirmationPopupShown(){
       return isElementShownAgent(closeAccountButton, 6);
   }

   public void confirmClosingAccount(){
       closeAccountConfirmationButton.click();
   }

   public boolean isAccountConfirmationPopupShown(){
       return isElementShownAgent(accountConfirmationPopupHeader, 6);
   }

   public void confirmAccount(String email, String pass){
       confirmationEmailInput.sendKeys(email);
       confirmationPassInput.sendKeys(pass);
   }

    public void cancelClosingAccount(){
        cancelCloseAccountButton.click();
    }

    public boolean isAccountDetailsPageOpened(){
       return isElementShownAgent(closeAccountButton, 5);
    }
}
