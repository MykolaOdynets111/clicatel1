package portalpages.uielements;

import com.github.javafaker.Faker;
import drivermanager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "form[name=billingDetailsForm]")
public class BillingContactsDetails extends BasePortalWindow {

    Faker faker = new Faker();

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "companyName")
    private WebElement companyNameInput;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(name = "emailAddress")
    private WebElement emailAddressInput;

    @FindBy(name = "address1")
    private WebElement address1Input;

    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(name = "postal")
    private WebElement postalInput;

    @FindBy(css = "div[placeholder='Select country']")
    private WebElement selectCountry;

    @FindBy(css = "div[placeholder='Select account type']")
    private WebElement selectAccount;

    @FindBy(xpath = "//span[text()='Personal']")
    private WebElement personalAccount;

    @FindBy(xpath = "//span[text()='Albania']")
    private WebElement country;

    public void fillInBillingDetailsForm() {
        inputTextAgent(firstNameInput, 1, "main", "'First name' Billing details field", faker.name().firstName());
        inputTextAgent(lastNameInput, 1, "main", "'Last name' Billing details field", faker.name().lastName());
        inputTextAgent(companyNameInput, 1, "main", "'Company name' Billing details field", faker.company().name());
        inputTextAgent(phoneInput, 1, "main", "'Phone' Billing details field", faker.phoneNumber().cellPhone());
        inputTextAgent(emailAddressInput, 1, "main", "'Email' Billing details field", "test@test.com");
        inputTextAgent(address1Input, 1, "main", "'Address' Billing details field", faker.address().streetAddress());
        clickElemAgent(selectCountry,1, "main", "'Select country' dropdown");
        clickElemAgent(country,1, "main", "'Albania' country");
        inputTextAgent(cityInput, 1, "main", "'City' Billing details field", faker.address().city());
        inputTextAgent(postalInput, 1, "main", "'Postal Code' Billing details field", faker.address().zipCode());
        clickElemAgent(selectAccount,1, "main", "'Select account' dropdown");
        clickElemAgent(selectAccount,1, "main", "'Select account' dropdown");
        clickElemAgent(personalAccount,1, "main", "'Personal account' option");

    }

}
