package api_helper;

import dataprovider.TwitterUsers;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterAPI {

    private static Twitter twitter = null;

    private static Twitter getTwitter() {
        if (twitter==null) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("tBUvZUbeBe10U3XJLSIFdQ4go")
                    .setOAuthConsumerSecret("Rjd4yDboITR0HwBcvvbbIdm1HQXI8m2BvTB4QNji07gy8V1r0r")
                    .setOAuthAccessToken("989806611945443333-IAK7erkrKFRzcwg0mNu5EGPi7yuhWW7")
                    .setOAuthAccessTokenSecret("ZzdEZWIFtgPItat2yYaHgDhnKQc5RABelLQ7ZOdnleB7u");
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
