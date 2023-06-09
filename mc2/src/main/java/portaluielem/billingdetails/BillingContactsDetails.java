package portaluielem.billingdetails;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.BasePortalWindow;

import java.util.*;
import java.util.stream.Collectors;

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

    @Step("Fill in new billing info")
    public Map fillInBillingDetailsForm() {
        Map billingInfo = new HashMap();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone =  "+380931576235";
        String email = "test@test.com";
        String city =  faker.address().city();
        String address1 = faker.address().streetAddress();
        String zipCode = faker.address().zipCode();
        billingInfo.put("billingContact", "{firstName="+firstName+", lastName="+lastName+", emailAddress="+email+", " +
                "cellPhone="+phone +"}");
        billingInfo.put("accountTypeId", "3");
        billingInfo.put("companyName", faker.company().name());
        billingInfo.put("billingAddress", "Albania, " + city + ", " + address1 +", " + zipCode);

        inputText(getCurrentDriver(), firstNameInput, 1,  "'First name' Billing details field", firstName);
        inputText(getCurrentDriver(), lastNameInput, 1,  "'Last name' Billing details field", lastName);
        inputText(getCurrentDriver(), companyNameInput, 1, "'Company name' Billing details field", (String) billingInfo.get("companyName"));
        inputText(getCurrentDriver(), phoneInput, 1, "'Phone' Billing details field", phone);
        inputText(getCurrentDriver(), emailAddressInput, 1, "'Email' Billing details field", email);
        inputText(getCurrentDriver(), address1Input, 1, "'Address' Billing details field",address1);
        clickElem(getCurrentDriver(), selectCountry,1, "'Select country' dropdown");
        clickElem(getCurrentDriver(), country,1, "'Albania' country");
        inputText(getCurrentDriver(), cityInput, 1,"'City' Billing details field", city);
        inputText(getCurrentDriver(), postalInput, 1, "'Postal Code' Billing details field", zipCode);
        clickElem(getCurrentDriver(), selectAccount,1, "'Select account' dropdown");
        clickElem(getCurrentDriver(), personalAccount,1, "'Personal account' option");
        clickElem(getCurrentDriver(), selectCurrency,1,"'Select currency' option");
        billingInfo.put("currency", firstAvailableCurrency.getText());
        clickElem(getCurrentDriver(), firstAvailableCurrency,1,"First 'Select currency' option");
        clickSaveChanges();
       return billingInfo;
    }

    @Step(value = "Verify mandatory input fields")
    public Map<Boolean, List<String>> verifyMandatoryInputFields(){
        Map<Boolean, List<String>> resultMap = new HashMap<>();
        List<String> inputsFailedVerification = new ArrayList<>();
        List<WebElement> requiredInputsList = Arrays.asList(firstNameInput, lastNameInput, companyNameInput,
                                        emailAddressInput, address1Input, cityInput, postalInput);
        requiredInputsList.forEach(e -> {
                boolean check = e.getAttribute("class").contains("ng-invalid");
                if(!check){
                    inputsFailedVerification.add(e.getAttribute("placeholder"));
                }
        });
        boolean result = inputsFailedVerification.size() == 0;
        resultMap.put(result, inputsFailedVerification);
        return resultMap;
    }

    @Step(value = "Verify mandatory dropdows fields")
    public Map<Boolean, List<String>> verifyMandatoryDropdowns(){
        Map<Boolean, List<String>> resultMap = new HashMap<>();
        List<String> inputsFailedVerification = new ArrayList<>();
        List<WebElement> requiredDropdown = Arrays.asList(selectCountry, selectAccount, selectCurrency).stream()
                .map(e -> (WebElement) e.findElement(By.xpath(".//parent::div"))).collect(Collectors.toList());

        requiredDropdown.forEach(e -> {
            boolean check = e.getAttribute("class").contains("ng-invalid ng-invalid-required");
            if(!check){
                inputsFailedVerification.add(e.getAttribute("innerText").trim());
            }
        });
        boolean result = inputsFailedVerification.size() == 0;
        resultMap.put(result, inputsFailedVerification);
        return resultMap;
    }

    @Step(value = "Enter new postal code")
    public BillingContactsDetails enterPostalCode(String value){
        postalInput.clear();
        postalInput.sendKeys(value);
        return this;
    }

    @Step(value = "Save changes")
    public void clickSaveChanges(){
        clickElem(this.getCurrentDriver(), saveChangesButton, 1, "'Save changes' button");
    }

    @Step(value = "Get postal code")
    public String getPostalCode(){
        return readValueFromInput(postalInput, this.getCurrentDriver());
    }
}
