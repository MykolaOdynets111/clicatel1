package touchpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TenantRow extends Widget implements WebActions {

    @FindBy(css = "input[name='tenantId']")
    private WebElement radioButton;

    public TenantRow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public void selectTenant(){
        radioButton.click();
    }
}
