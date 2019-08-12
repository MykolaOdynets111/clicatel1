package interfaces;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;


import java.util.List;


public interface WebActions extends WebWait {

    String widgetScroller = "div.scroller";


    // ==================================== Elements Actions ======================================= //


    default void clickElem(WebDriver driver, WebElement element, int wait, String elemName){
        try {
            waitForElementToBeClickable(driver, element, wait).click();
        } catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot click '" + elemName + "' because button is not clickable.");
        }
    }

    default void clickHoldRelease(WebDriver driver, WebElement element, int wait, String elemName){
        try {
            waitForElementToBeClickable(driver, element, wait);
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).release().perform();
        }catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot click '" + elemName + "' because button is not clickable.");
        }
    }

    default void pressEnterForWebElem(WebDriver driver, WebElement element, int wait, String elemName){
        try {
            waitForElementToBeClickable(driver, element, wait).sendKeys(Keys.ENTER);
        } catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot press enter for '" + elemName + "' because button is not clickable.");
        }
    }

    default String getTextFromElem(WebDriver driver, WebElement element, int wait, String elemName){
        try {
            waitForElementToBeVisible(driver, element, wait);
            return element.getText();
        } catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot get text from  '" + elemName + "' because element is not visible.");
            return "no text elem";
        }
    }

    default String getAttributeFromElemAgent(WebDriver driver, WebElement element, int wait,
                                             String elemName, String attribute){
        try {
            waitForElementToBeVisible(driver, element, wait);
            return element.getAttribute(attribute);
        } catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot get '" + attribute + "' attribute from  '"+elemName+"' because element is not visible.");
            return "no text elem";
        }
    }

    default void clickOnElementFromListByText(WebDriver driver, List<WebElement> elements, int wait, String text){
        waitForElementsToBeClickable(driver, elements, wait)
                .stream().filter(e -> e.getText().toUpperCase().equals(text.toUpperCase()))
                .findFirst().get().click();
    }

    default void inputText(WebDriver driver, WebElement element, int wait, String fieldName, String text){
        try {
            waitForElementToBeClickable(driver, element, wait).clear();
            element.sendKeys(text);
        } catch (TimeoutException e){
            Assert.fail("Cannot insert text in '" + fieldName + "' because input is not clickable.");
        }

    }


    // ============================== Elements State Verification ================================== //

    default boolean areElementsShown(WebDriver driver, List<WebElement> elements, int wait){
        try {
            return waitForElementsToBeVisible(driver, elements, wait).size()>0;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShown(WebDriver driver, WebElement element, int wait){
        try {
            return waitForElementToBeVisible(driver, element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownByXpath(WebDriver driver, String xpath, int wait){
        try {
            waitForElementToBeVisibleByXpath(driver, xpath, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownByCSS(WebDriver driver, String css, int wait){
        try {
            waitForElementToBeVisibleByCss(driver, css, wait);
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    default boolean isElementsExistsInDOMXpath(WebDriver driver, String xpath, int time){
        try {
            waitForElementsToBePresentByXpath(driver, xpath, time);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementExistsInDOMCss(WebDriver driver, String css, int wait){
        try {
            waitForElementToBePresentByCss(driver, css, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementExistsInDOMXpath(WebDriver driver, String xpath, int wait){
        try {
            waitForElementToBePresentByXpath(driver, xpath, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementNotShownByCSS(WebDriver driver, String css, int wait){
        try {
            waitForElementToBeInVisibleByCss(driver, css, wait);
            return false;
        } catch (TimeoutException|NoSuchElementException e) {
            return true;
        }
    }

    default boolean isElementNotShown(WebDriver driver, WebElement element, int wait){
        try {
            waitForElementToBeInvisible(driver, element, wait);
            return false;
        } catch (TimeoutException|NoSuchElementException e) {
            return true;
        }
    }

    default boolean isElementEnabled(WebDriver driver, WebElement element, int wait){
        try {
            return waitForElementToBeClickable(driver, element, wait).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }


    // =============================== Elements Locating  by raw locators =========================== //

    default List<WebElement> findElemsByXPATH(WebDriver driver, String xpath){
        return driver.findElements(By.xpath(xpath));
    }

    default WebElement findElemByXPATH(WebDriver driver, String xpath){
        return driver.findElement(By.xpath(xpath));
    }

    default List<WebElement> findElemntsByCSS(WebDriver driver, String css) {
        return driver.findElements(By.cssSelector(css));
    }

    default WebElement findElemByCSS(WebDriver driver, String css){
        return driver.findElement(By.cssSelector(css));
    }

    default List<WebElement> findElemsByCSS(WebDriver driver, String css){
        return driver.findElements(By.cssSelector(css));
    }


    // ======================================== Driver Actions ====================================== //

    default void switchDriver(WebDriver driver, String currentFocus){
        if(driver.getWindowHandles().size()<2) {
            return;
        }
        for (String winHandle : driver.getWindowHandles()) {
                if (!winHandle.equals(currentFocus)) {
                    driver.switchTo().window(winHandle);
                }
        }
    }

    default void closeTab(WebDriver driver, String windowToSwitch){
        driver.close();
        driver.switchTo().window(windowToSwitch);
    }

//    default void scrollUpWidget(int scrollPosition){
//        String styleTransform = "translate(0px, -%spx) translateZ(0px)";
//        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
//        jsExec.executeScript("arguments[0].style.transform='"+String.format(styleTransform, scrollPosition)+"';",
//                DriverFactory.getTouchDriverInstance().findElement(By.cssSelector(widgetScroller)));
//    }

//    /**
//     * Verify if scren of element equals image (deviation 5%).
//     *
//     * @param  element   WebElement for screen shot
//     * @param  image   File for comparing with scren shot
//     * @param agent
//     * @return         Boolean: true or false
//     * @throws Exception
//     */
//    default boolean isWebElementEqualsImage(WebElement element, File image, String agent){
//        boolean result=false;
//        Browser browser = new Browser(DriverFactory.getDriverForAgent(agent),true);
//        Double dpr= browser.getDevicePixelRatio();
//        try {
//           if (!image.canRead()) {
//                BufferedImage img = Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent),element,true ).getImage();
//                Image newimg = img.getScaledInstance((int)Math.ceil(img.getWidth()/dpr),(int)Math.ceil(img.getHeight()/dpr),Image.SCALE_DEFAULT);
//                File newFile = new File(image.getPath());
//                newFile.getParentFile().mkdirs();
//                new FileWriter(newFile);
//                BufferedImage buffered = imageToBufferedImage(newimg);
//                ImageIO.write(buffered,"PNG",newFile);
//                System.out.println("!!!!!! File was created !!!!!!!!! \n");
//            }
//            BufferedImage expImage = ImageIO.read(image);
//            BufferedImage expectedImage = imageToBufferedImage(expImage.getScaledInstance((int)Math.floor((expImage.getWidth()*dpr)),(int)Math.floor((expImage.getHeight()*dpr)),Image.SCALE_DEFAULT));
//            result = Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent), element, true).withName("Actual").equals(expectedImage, 0.07);
//            if (!result) {
//                Shutterbug.shootElement(DriverFactory.getDriverForAgent(agent), element,true).equalsWithDiff(expectedImage, "src/test/resources/imagediferense/"+image.getName().substring(0,image.getName().length()-4));
//            }
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            //Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),element).withName(name).save("src/test/resources/icons/");
//        }
//        return result;
//    }
//
//     static  BufferedImage imageToBufferedImage(Image im) {
//        BufferedImage bi = new BufferedImage (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
//        Graphics bg = bi.getGraphics();
//        bg.drawImage(im, 0, 0, null);
//        bg.dispose();
//        return bi;
//    }
//
//    default void createElementImage(WebElement element, String name, String path){
//            Shutterbug.shootElement(DriverFactory.getDriverForAgent("main"),element,true ).withName(name).save(path);
//    }
}
