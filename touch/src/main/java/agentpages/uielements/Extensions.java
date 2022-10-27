package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FindBy(css =".cl-extensions-manager-overlay")
public class Extensions extends AbstractUIElement {

    private final String extensionsTabs = ".//li[text()='%s']";

    @FindBy(css=".cl-actions-menu__item-text")
    private WebElement paymentRequestItem;

    @FindBy(css="#findExtension")
    private WebElement searchExtensions;
    @FindBy (css = ".cl-extension-item__type")
    private List<WebElement> extensionType;

    String extensionTypeWithName = ".//div[text()='%s']/ancestor::div//span[contains(@class, 'cl-actions-menu__item-text')]";

    public void openC2pForm(){
        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "All Extensions"), 2, "All Extensions Tab button");
        executeJSclick(this.getCurrentDriver(), paymentRequestItem);
    }

    public void openC2pFormWithText(String text){
        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "All Extensions"), 10, "All Extensions Tab button");

        waitForElementToBeVisible(this.getCurrentDriver(), searchExtensions, 5);
        inputText(this.getCurrentDriver(), searchExtensions, 5, "Search extensions", text);
        executeJSclick(this.getCurrentDriver(), paymentRequestItem);

    }

    public List<String> allExtensionTabsExtensions(){
        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "All Extensions"), 2, "All Extensions Tab button");

        List<String> extensionTypes = new ArrayList<>();
        extensionType.stream().forEach(e -> {
            extensionTypes.add(e.getText());
            });

        return extensionTypes;
    }

    public boolean isFreqUsedTabsExtsExistInAllExtsTab(){
        List<String> allExtensions = allExtensionTabsExtensions();

        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "Frequently Used"), 2, "Frequently Used Tab button");

        return allExtensions.contains(getTextFromElem(this.getCurrentDriver(), extensionType.get(0), 5, "Extension element"));
    }

    public boolean frequentExtListSizeAllExtensionListSizeComparison(){
        List<String> allExtensions = allExtensionTabsExtensions();

        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "Frequently Used"), 2, "Frequently Used Tab button");

        List<String> frequentlyUsedExtension = new ArrayList<>();
        extensionType.stream().forEach(e -> {
            frequentlyUsedExtension.add(e.getText());
        });

        return allExtensions.size() >= frequentlyUsedExtension.size();
    }

    public int frequentExtListSize(){
        clickElemByXpath(this.getCurrentDriver(), String.format(extensionsTabs, "Frequently Used"), 2, "Frequently Used Tab button");

        List<String> frequentlyUsedExtension = new ArrayList<>();
        extensionType.stream().forEach(e -> {
            frequentlyUsedExtension.add(e.getText());
        });

        return frequentlyUsedExtension.size();
    }
}
