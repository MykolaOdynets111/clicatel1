package facebook;

import abstract_classes.AbstractPage;
import dataManager.FacebookUsers;
import driverManager.DriverFactory;
import facebook.uielements.MessengerWindow;
import facebook.uielements.PostFeed;
import facebook.uielements.VisitorPost;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FBTenantPage extends AbstractPage {

    @FindBy(xpath = "//span[text()='Send Message']")
    private WebElement sendMessageButton;

    @FindBy(xpath = "//a[text()='View Post.']")
    private WebElement viewPostButton;

    @FindBy(xpath = "//ul/li[contains(@data-gt, 'feed_comment')]")
    private WebElement notificationAboutNewComment;

    private String postLocator = "//a//ancestor::div[@class='clearfix']//ancestor::li";

    private String newPostResponseCloseNotificationButton = "//button[@title='Close']";

    private MessengerWindow messengerWindow;
    private PostFeed postFeed;
    private FBYourPostPage FBYourPostPage;

    public FBYourPostPage getFBYourPostPage() {
        if(FBYourPostPage ==null){
            FBYourPostPage = new FBYourPostPage();
        }
        return FBYourPostPage;
    }

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

    public boolean isNotificationAboutNewCommentArrives(int wait){
        return isElementShown(notificationAboutNewComment, wait);
    }

    public void clickNewCommentNotification(){
        notificationAboutNewComment.click();
    }

    public void clickViewPostButton() {
        waitForElementToBeVisible(viewPostButton).click();
    }

    public void waitForNewPostNotificationToDisappear(){
        if (isElementShownByXpath(newPostResponseCloseNotificationButton, 5)) findElemByXPATH(newPostResponseCloseNotificationButton).click();
        waitForElementToBeInvisible(notificationAboutNewComment, 5);
    }

    public VisitorPost getLastVisitorPost() {
        String loggedFBUserName = FacebookUsers.getLoggedInUser().getFBUserName() +" " + FacebookUsers.getLoggedInUser().getFBUserSurname();
        List<VisitorPost> allPosts = DriverFactory.getTouchDriverInstance().findElements(By.xpath(postLocator))
                .stream().map(VisitorPost::new).collect(Collectors.toList());
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
