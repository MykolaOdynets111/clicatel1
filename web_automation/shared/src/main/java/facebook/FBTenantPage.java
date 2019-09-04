package facebook;

import abstractclasses.AbstractSocialPage;
import facebook.uielements.MessengerWindow;
import facebook.uielements.PostFeed;
import facebook.uielements.VisitorPost;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import socialaccounts.FacebookUsers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FBTenantPage extends AbstractSocialPage {



    @FindAll({
            @FindBy(xpath = "//button[text()='Send Message']"),
            @FindBy(xpath = "//button/*[contains(text(), 'Send Message')]")
    })
    private WebElement sendMessageButton;

    @FindBy(xpath = "//a[text()='View Post.']")
    private WebElement viewPostButton;

    @FindBy(xpath = "//ul/li[contains(@data-gt, 'feed_comment')]")
    private WebElement notificationAboutNewComment;

    private String notificationAboutNewCommentXpath = "//ul/li[contains(@data-gt, 'feed_comment')]";

    private String postLocator = "//a//ancestor::div[@class='clearfix']//ancestor::li";

    private String newPostResponseCloseNotificationButton = "//button[@title='Close']";

    private MessengerWindow messengerWindow;
    private PostFeed postFeed;
    private FBYourPostPage FBYourPostPage;

    public FBTenantPage(WebDriver driver) {
        super(driver);
    }

    public FBYourPostPage getFBYourPostPage() {
        if(FBYourPostPage ==null){
            FBYourPostPage = new FBYourPostPage(this.getCurrentDriver());
        }
        return FBYourPostPage;
    }

    public MessengerWindow getMessengerWindow() {
        messengerWindow.setCurrentDriver(this.getCurrentDriver());
        return messengerWindow;
    }
    public PostFeed getPostFeed() {
        postFeed.setCurrentDriver(this.getCurrentDriver());
        return postFeed;
    }

    public MessengerWindow openMessenger(){
        clickElem(this.getCurrentDriver(), sendMessageButton, 5, "Send message button");
        messengerWindow.setCurrentDriver(this.getCurrentDriver());
        return messengerWindow;
    }

    public boolean isNotificationAboutNewCommentArrives(int wait){
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), notificationAboutNewCommentXpath, wait);
            return true;
        }catch (TimeoutException e) {
            return false;
        }
    }

    public void clickNewCommentNotification(){
        notificationAboutNewComment.click();
    }

    public void clickViewPostButton() {
        clickElem(this.getCurrentDriver(), viewPostButton, 5, "View fb Post button");
    }

    public void waitForNewPostNotificationToDisappear(){
        if (isElementShownByXpath(this.getCurrentDriver(), newPostResponseCloseNotificationButton, 5)) findElemByXPATH(this.getCurrentDriver(), newPostResponseCloseNotificationButton).click();
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), newPostResponseCloseNotificationButton, 6);
    }

    public VisitorPost getLastVisitorPost() {
        String loggedFBUserName = FacebookUsers.getLoggedInUser().getFBUserName() +" " + FacebookUsers.getLoggedInUser().getFBUserSurname();
        List<VisitorPost> allPosts = findElemsByXPATH(this.getCurrentDriver(), postLocator)
                .stream()
                .map(e -> new VisitorPost(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
        List<VisitorPost> postsFromLoggedInAQAUser = allPosts.stream()
                .filter(e-> e.getUserName().equals(loggedFBUserName)).collect(Collectors.toList());
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
