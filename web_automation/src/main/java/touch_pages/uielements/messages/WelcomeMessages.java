package touch_pages.uielements.messages;

import abstract_classes.AbstractPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class WelcomeMessages extends AbstractPage {

    @FindBy(css = "li.message-to.with-content")
    private WebElement welcomeCardContainer;

    @FindBy(xpath = "(//div[@data-name='card-container']//span)[1]")
    private WebElement welcomeCardText;

    @FindBy(css = "li.message-to.with-content button")
    private List<WebElement> welcomeCardButtons;

    public boolean isWelcomeCardContainerShown() {
        try {
            return isElementShown(welcomeCardContainer);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> getWelcomeCardButtonText(){
        return welcomeCardButtons.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public void clickActionButton(String buttonName){
        WebElement button = welcomeCardButtons.stream().filter(e->e.getText().equalsIgnoreCase(buttonName)).findFirst().get();
//        if(button != null) {
            button.click();
//        } else {
//            Assert.assertTrue(false,
//                    "There is no button with text "+buttonName+" in Welcome card");
//        }
    }
}
