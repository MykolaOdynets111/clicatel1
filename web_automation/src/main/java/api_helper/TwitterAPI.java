package api_helper;

import com.github.javafaker.Faker;
import dataprovider.TwitterUsers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.digest.HmacUtils;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterAPI {

    private static Twitter twitter = null;

    private static Twitter getTwitter() {
        if (twitter==null) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("eFxUEbDPBjMyEuQdXXESHzN6m")
                    .setOAuthConsumerSecret("xECSBWTtNKuqUmE5sMvPDCjHwtqVNy5FZcsfPpO3XEx5zhBNrc")
                    .setOAuthAccessToken("952841504724324352-wJcYK2aQoekPxmJPIaXRdFO5kBuCsdX")
                    .setOAuthAccessTokenSecret("viPs6b8NywjHiHUsXNq585Z78FLhtEXSkP9yUrIAS5KP7");
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            return twitter;
        } else {
            return twitter;
        }
    }



    public static List<Status> getAllTweetsToTestUser(){
        List<Status> toTestUserTweets = new ArrayList<>();
        try {
            List<Status> allTweets = getTwitter().getHomeTimeline();
            allTweets =  allTweets.stream().filter(e -> !(e.getInReplyToScreenName() == null)).collect(Collectors.toList());
            for(Status tweet : allTweets){
                if (tweet.getInReplyToScreenName().equals(TwitterUsers.getLoggedInUser().getScreenName()
                        .replace("@", ""))) {
                    toTestUserTweets.add(tweet);
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return toTestUserTweets;
    }


    public static void deleteToTestUserTweets() {
        List<Status> toTestUserTweets = getAllTweetsToTestUser();
        int a =2;
        toTestUserTweets.stream().map(e -> e.getId()).collect(Collectors.toList())
                        .stream().forEach(e -> {
                try {
                    getTwitter().destroyStatus(e);
                } catch (TwitterException e1) {
                    e1.printStackTrace();
                }
            });
    }

    public static void main(String [] args){
       TwitterAPI.deleteToTestUserTweets();
    }
}
