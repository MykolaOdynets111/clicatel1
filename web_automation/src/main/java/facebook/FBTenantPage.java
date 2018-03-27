package facebook;

import abstract_classes.AbstractPage;
import dataprovider.FacebookUsers;
import driverManager.DriverFactory;
import facebook.uielements.MessengerWindow;
import facebook.uielements.PostFeed;
import facebook.uielements.VisitorPost;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FBTenantPage extends AbstractPage {

    @FindBy(xpath = "//span[text()='Send Message']")
    private WebElement sendMessageButton;

    @FindBy(xpath = "//a[text()='View Post.']")
    private WebElement viewPostButton;

    private String postLocator = "//a//ancestor::div[@class='clearfix']//ancestor::li";

    private MessengerWindow messengerWindow;
    private PostFeed postFeed;

    public MessengerWindow getMessengerWindow() {
        return messengerWindow;
    }
    public PostFeed getPostFeed() {
        return postFeed;
    }

    public MessengerWindow openMessenger(){
        waitForElementToBeVisible(sendMessageButton);
        sendMessageButton.click();
        return messengerWindow;
    }

    public void clickViewPostButton() {
        waitForElementToBeVisible(viewPostButton).click();
    }

    public VisitorPost getLastVisitorPost() {
        String loggedFBUserName = FacebookUsers.getLoggedInUser().getFBUserName() +" " + FacebookUsers.getLoggedInUser().getFBUserSurname();
        List<VisitorPost> allPosts = DriverFactory.getInstance().findElements(By.xpath(postLocator))
                .stream().map(e -> new VisitorPost(e)).collect(Collectors.toList());
        List<VisitorPost> postsFromLoggedInAQAUser = allPosts.stream().filter(e-> e.getUserName().equals(loggedFBUserName)).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime latestTime = null;
        VisitorPost targetPost = null;
        for (VisitorPost post:postsFromLoggedInAQAUser) {
            String postHoursAndMinutes = post.getPostTime().split(" ")[2];
            LocalTime postTime = LocalTime.parse(postHoursAndMinutes, formatter);
            if(latestTime==null){
                latestTime=postTime;
                targetPost=post;
            }
            if(postTime.isAfter(latestTime)){
                latestTime = postTime;
                targetPost=post;
            }
        }
        return targetPost;
    }
}
