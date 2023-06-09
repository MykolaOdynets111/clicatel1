package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalAccountDetailsPage extends PortalAbstractPage {

   @FindBy(xpath = "//button[text()='Close account']")
   private WebElement closeAccountButton;

   @FindBy(xpath = "//span[text()='Close account?']")
   private WebElement closingConfirmationPopupHeader;

   @FindBy(css = "div.button-container button.button-primary")
   private WebElement closeAccountConfirmationButton;

   @FindBy(xpath = "//button[@ng-show='options.cancelText']")
   private WebElement cancelCloseAccountButton;

   @FindBy(xpath = "//span[text()='Account confirmation']")
   private WebElement accountConfirmationPopupHeader;

   @FindBy(name = "email")
   private WebElement confirmationEmailInput;

   @FindBy(name = "password")
   private WebElement confirmationPassInput;

    // == Constructors == //

    public PortalAccountDetailsPage() {
        super();
    }
    public PortalAccountDetailsPage(String agent) {
        super(agent);
    }
    public PortalAccountDetailsPage(WebDriver driver) {
        super(driver);
    }

   public void clickCloseAccountButton(){
       clickElem(this.getCurrentDriver(), closeAccountButton, 1, "Close account");
   }

   public boolean isClosingConfirmationPopupShown(){
       return isElementShown(this.getCurrentDriver(), closeAccountButton, 6);
   }

   public void confirmClosingAccount(){
       closeAccountConfirmationButton.click();
   }

   public boolean isAccountConfirmationPopupShown(){
       return isElementShown(this.getCurrentDriver(), accountConfirmationPopupHeader, 6);
   }

   public void confirmAccount(String email, String pass){
       confirmationEmailInput.sendKeys(email);
       confirmationPassInput.sendKeys(pass);
   }

    public void cancelClosingAccount(){
        cancelCloseAccountButton.click();
    }

    public boolean isAccountDetailsPageOpened(){
       return isElementShown(this.getCurrentDriver(), closeAccountButton, 5);
    }
}
