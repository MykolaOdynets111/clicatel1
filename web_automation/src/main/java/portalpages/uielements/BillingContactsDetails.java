package portalpages.uielements;

import com.github.javafaker.Faker;
import drivermanager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @FindBy(xpath = "//div[@required='required']/div[@placeholder='Select currency']")
    private WebElement selectCurrency;

    @FindBy(xpath = "//div[@required='required']/div[@placeholder='Select currency']/following-sibling::ul/li[1]")
    private WebElement firstAvailableCurrency;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveChangesButton;

    public Map fillInBillingDetailsForm() {
        Map billingInfo = new HashMap();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone =  faker.phoneNumber().cellPhone();
        String email = "test@test.com";
        String city =  faker.address().city();
        String address1 = faker.address().streetAddress();
        String zipCode = faker.address().zipCode();
        billingInfo.put("billingContact", "{firstName="+firstName+", lastName="+lastName+", emailAddress="+email+", " +
                "cellPhone="+phone.replace("+", "").replace("-","").replace(".", "")+"}");
        billingInfo.put("accountTypeId", "3");
        billingInfo.put("companyName", faker.company().name());
        billingInfo.put("billingAddress", "Albania, " + city + ", " + address1 +", " + zipCode);

        inputTextAgent(firstNameInput, 1, "main", "'First name' Billing details field", firstName);
        inputTextAgent(lastNameInput, 1, "main", "'Last name' Billing details field", lastName);
        inputTextAgent(companyNameInput, 1, "main", "'Company name' Billing details field", (String) billingInfo.get("companyName"));
        inputTextAgent(phoneInput, 1, "main", "'Phone' Billing details field", phone);
        inputTextAgent(emailAddressInput, 1, "main", "'Email' Billing details field", email);
        inputTextAgent(address1Input, 1, "main", "'Address' Billing details field",address1);
        clickElemAgent(selectCountry,1, "main", "'Select country' dropdown");
        clickElemAgent(country,1, "main", "'Albania' country");
        inputTextAgent(cityInput, 1, "main", "'City' Billing details field", city);
        inputTextAgent(postalInput, 1, "main", "'Postal Code' Billing details field", zipCode);
        clickElemAgent(selectAccount,1, "main", "'Select account' dropdown");
        clickElemAgent(personalAccount,1, "main", "'Personal account' option");
        clickElemAgent(selectCurrency,1, "main", "'Select currency' option");
        billingInfo.put("currency", firstAvailableCurrency.getText());
        clickElemAgent(firstAvailableCurrency,1, "main", "First 'Select currency' option");
        clickElemAgent(saveChangesButton,1, "main", "'Save changes' button");
       return billingInfo;
    }

}
