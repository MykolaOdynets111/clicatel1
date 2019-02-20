package apihelper;

import datamanager.TwitterUsers;
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
    private static Twitter testUserTwitter = null;

    private static Twitter getTwitter() {
        if (twitter==null) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("esd9NkdjoaguQaDcPDLyorLrT")
                    .setOAuthConsumerSecret("VzB11V94Wh303DVM54ndkCmQFFYBhKHXq5xXCv1EpvxSFAOQ60")
                    .setOAuthAccessToken("989806611945443333-a21LskxrDbbMegFDlhFSqiPTg2HY0Q3")
                    .setOAuthAccessTokenSecret("nv3hu9olSVfqg8zQNMsCSBDGCwB8TzyZdm99WJ1di2gOb");
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            return twitter;
        } else {
            return twitter;
        }
    }

    private static Twitter getTestUserTwitter() {
        if (testUserTwitter==null) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("0Bil1fRe3OqOaUsiZVAvFB7Ob")
                    .setOAuthConsumerSecret("fBAnImckv1WBDH6bLKZPYiPZAZPaVcbmXcb3puUgg1xn4cazZH")
                    .setOAuthAccessToken("979311039996157952-70Xpy1xN1IM6hu8JpRvIOfLogNrd76Q")
                    .setOAuthAccessTokenSecret("7Q9PZxChbEzSW1FRWvocWV1ieF8uhAY9APTPK2970Is4b");
            TwitterFactory tf = new TwitterFactory(cb.build());
            testUserTwitter = tf.getInstance();
            return testUserTwitter;
        } else {
            return testUserTwitter;
        }
    }


    public static void deleteTweetsFromTestUser(){
        Twitter testUserTwitter = getTestUserTwitter();
        List<Status> allTweets = new ArrayList<>();
        try {
            allTweets = testUserTwitter.getHomeTimeline();
            allTweets.stream().map(e -> e.getId()).collect(Collectors.toList())
                    .stream().forEach(e -> {
                try {
                    testUserTwitter.destroyStatus(e);
                } catch (TwitterException e1) {
                    e1.printStackTrace();
                }
            });
        } catch (TwitterException e) {
            e.printStackTrace();
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
