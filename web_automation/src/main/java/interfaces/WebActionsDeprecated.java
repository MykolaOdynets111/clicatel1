package interfaces;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.Browser;
import drivermanager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Deprecated
public interface WebActionsDeprecated extends WebWaitDeprecated {

    String widgetScroller = "div.scroller";

    default void waitForDeprecated(int milisecs){
        try {
            Thread.sleep(milisecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    default void click(WebElement element){
        waitForElementToBeClickable(element).click();
    }

    default void clickElemAgent(WebElement element, int time, String agent, String buttonName){
        try {
            waitForElementToBeClickableAgent(element, time, agent)
            .click();
        } catch (TimeoutException|NoSuchElementException e){
            Assert.assertTrue(false, "Cannot click '"+buttonName+"' because button is not clickable.");
        }
    }

    default String getTextFromElemAgent(WebElement element, int time, String agent, String elemName){
        try {
            waitForElementToBeVisibleAgent(element, time, agent);
            return element.getText();
        } catch (TimeoutException|NoSuchElementException e){
            Assert.assertTrue(false, "Cannot get text from  '"+elemName+"' because element is not visible.");
            return "no text elem";
        }
    }

    default String getAttributeFromElemAgent(WebElement element, String attribute, int time, String agent, String elemName){
        try {
            waitForElementToBeVisibleAgent(element, time, agent);
            return element.getAttribute(attribute);
        } catch (TimeoutException|NoSuchElementException e){
            Assert.assertTrue(false, "Cannot get text from  '"+elemName+"' because element is not visible.");
            return "no text elem";
        }
    }

    default void clickOnElementFromListByText(List<WebElement> elements, String text){
        waitForElementsToBeClickable(elements).stream().filter(e -> e.getText().toUpperCase().equals(text.toUpperCase())).findFirst().get().click();
    }

    default void inputText(WebElement element, String text){
        try {
            waitForElementToBeClickable(element).clear();
            element.sendKeys(text);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Cannot insert text '"+text+"' because input is not clickable.");
        }

    }

    default void inputTextAgent(WebElement element, int time, String agent, String fieldName,String text ){
        try {
            waitForElementToBeClickableAgent(element, time, agent)
            .sendKeys(text);
        } catch (TimeoutException|NoSuchElementException e){
            Assert.assertTrue(false, "Cannot insert text in '"+fieldName+"' because input is not clickable.");
        }

    }

    default boolean areElementsShown(List<WebElement> elements){
        try {
            return waitForElementsToBeVisible(elements, 5).size()>0;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean areElementsShownAgent(List<WebElement> elements, int time, String agent){
        try {
            return waitForElementsToBeVisibleAgent(elements, time, agent).size()>0;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShown(WebElement element){
        try {
            return waitForElementToBeVisible(element, 5).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShown(WebElement element, int wait){
        try {
            return waitForElementToBeVisible(element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementsExistsInDOM(String xpath, int time){
        try {
            waitForElementsToBePresentByXpathAgent(xpath, time);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }


    default boolean isElementExistsInDOMAgentCss(String css, int time, String agent){
        try {
            waitForElementToBePresentByCssAgent(css, time, agent);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownByXpath(String xpath, int wait){
        try {
            waitForElementToBeVisibleByXpath(xpath, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownAgent(WebElement element){
        try {
            return waitForElementToBeVisibleAgent(element, 5).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownAgent(WebElement element, int wait){
        try {
            return waitForElementToBeVisibleAgent(element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException|NoSuchWindowException|NullPointerException e) {
            return false;
        }
    }

    default boolean isElementShownAgent(WebElement element, int wait, String agent){
        try {
            return waitForElementToBeVisibleAgent(element, wait, agent).isDisplayed();
        } catch (WebDriverException e) {
            return false;
        }
    }

    default boolean isElementShownAgentByXpath(String xpath, int wait, String agent){
        try {
            waitForElementToBeVisibleByXpathAgent(xpath, wait, agent);
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    default boolean isElementNotShownAgentByCSS(String css, int wait){
        try {
            waitForElementToBeInVisibleByCssAgent(css, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownAgentByCSS(String css, int wait, String agent){
        try {
            waitForElementToBeVisibleByCssAgent(css, wait, agent);
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }


    default boolean isElementEnabledAgent(WebElement element, int wait, String agent){
        try {
            return waitForElementToBeClickableAgent(element, wait, agent).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    default boolean isElementNotShown(WebElement element, int wait){
        try {
            waitForElementToBeInvisibleWithNoSuchElementException(element, wait);
            return true;
        } catch (TimeoutException e) {
            try {
                return element.isDisplayed();
            } catch(NoSuchElementException e1) {
                return true;
            }
        }
    }

    default boolean isElementNotShownAgent(WebElement element, int wait){
        try {
            waitForElementToBeInvisibleAgent(element, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementNotShownAgent(WebElement element, int wait, String agent){
        try {
            waitForElementToBeInvisibleWithNoSuchElementExceptionAgent(element, wait, agent);
            return true;
        } catch (TimeoutException e) {
            try {
                return !element.isDisplayed();
            } catch(NoSuchElementException e1) {
                return true;
            }
        }
    }


    default String getTextFrom(WebElement elem) {
        try{
            String text = elem.getText();
            if(text.isEmpty()) {
                waitForDeprecated(200);
                text = elem.getText();
            }
            return text;
        } catch (NoSuchElementException e) {
            return "no element to get text from";
        }
    }

    default WebElement findElemByXPATH(String xpath){
        return DriverFactory.getTouchDriverInstance().findElement(By.xpath(xpath));
    }


    default WebElement findElemByCSS(String css){
        return DriverFactory.getTouchDriverInstance().findElement(By.cssSelector(css));
    }

    default List<WebElement> findElemntsByCSS(String css) {
        return DriverFactory.getTouchDriverInstance().findElements(By.cssSelector(css));
    }

    default WebElement findElemByXPATHAgent(String xpath){
        return DriverFactory.getAgentDriverInstance().findElement(By.xpath(xpath));
    }

    default WebElement findElemByXPATHAgent(String xpath, String agent){
        return DriverFactory.getDriverForAgent(agent).findElement(By.xpath(xpath));
    }

    default WebElement findElemByCSSAgent(String css){
        return DriverFactory.getAgentDriverInstance().findElement(By.cssSelector(css));
    }

    default WebElement findElemByCSSAgent(String css, String agent){
        return DriverFactory.getDriverForAgent(agent).findElement(By.cssSelector(css));
    }

    default List<WebElement> findElemsByXPATH(String xpath){
        return DriverFactory.getTouchDriverInstance().findElements(By.xpath(xpath));
    }

    default List<WebElement> findElemsByCSSAgent(String css){
        return DriverFactory.getAgentDriverInstance().findElements(By.cssSelector(css));
    }

    default List<WebElement> findElemsByCSS(String css){
        return DriverFactory.getTouchDriverInstance().findElements(By.cssSelector(css));
    }

    default List<WebElement> findElemsByXPATHAgent(String xpath){
        return DriverFactory.getAgentDriverInstance().findElements(By.xpath(xpath));
    }

    default void pressEnterForWebElem(WebElement elem){
        elem.sendKeys(Keys.ENTER);
    }

    default void scrollUpWidget(int scrollPosition){
        String styleTransform = "translate(0px, -%spx) translateZ(0px)";
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("arguments[0].style.transform='"+String.format(styleTransform, scrollPosition)+"';",
                DriverFactory.getTouchDriverInstance().findElement(By.cssSelector(widgetScroller)));
    }

    default void clickHoldRelease(WebDriver driver, WebElement elem){
        Actions actions = new Actions(driver);
        actions.clickAndHold(elem).release().perform();
    }

    /**
     * Verify if scren of element equals image (deviation 5%).
     *
     * @param  element   WebElement for screen shot
     * @param  image   File for comparing with scren shot
     * @param agent
     * @return         Boolean: true or false
     * @throws Exception
     */
    default boolean isWebElementEqualsImage(WebElement element, File image, String agent){
        boolean result=false;
        Browser browser = new Browser(DriverFactory.getDriverForAgent(agent),true);
        Double dpr= browser.getDevicePixelRatio();
        try {
           if (!image.canRead()) {
                BufferedImage img = Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent),element,true ).getImage();
                Image newimg = img.getScaledInstance((int)Math.ceil(img.getWidth()/dpr),(int)Math.ceil(img.getHeight()/dpr),Image.SCALE_DEFAULT);
                File newFile = new File(image.getPath());
                newFile.getParentFile().mkdirs();
                new FileWriter(newFile);
                BufferedImage buffered = imageToBufferedImage(newimg);
                ImageIO.write(buffered,"PNG",newFile);
                System.out.println("!!!!!! File was created !!!!!!!!! \n");
            }
            BufferedImage expImage = ImageIO.read(image);
            BufferedImage expectedImage = imageToBufferedImage(expImage.getScaledInstance((int)Math.floor((expImage.getWidth()*dpr)),(int)Math.floor((expImage.getHeight()*dpr)),Image.SCALE_DEFAULT));
            result = Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent), element, true).withName("Actual").equals(expectedImage, 0.07);
            if (!result) {
                Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent), element,true).equalsWithDiff(expectedImage, "src/test/resources/imagediferense/"+image.getName().substring(0,image.getName().length()-4));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            //Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),element).withName(name).save("src/test/resources/icons/");
        }
        return result;
    }

     static  BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    default void createElementImage(WebElement element,String name, String path){
            Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),element,true ).withName(name).save(path);
    }
}
