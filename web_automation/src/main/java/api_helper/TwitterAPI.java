package api_helper;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.digest.HmacUtils;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Instant;
import java.time.LocalDateTime;

public class TwitterAPI {

    private static Twitter twitter = null;

    private static void createTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("eFxUEbDPBjMyEuQdXXESHzN6m")
                .setOAuthConsumerSecret("xECSBWTtNKuqUmE5sMvPDCjHwtqVNy5FZcsfPpO3XEx5zhBNrc")
                .setOAuthAccessToken("966677566840025088-dXnb8XOVWSsotUgAJ2SQp3GP9L3fHay")
                .setOAuthAccessTokenSecret("O1IwzassrIXddUdj4jM94iC69PjpfzacmNHfRlg9HWJca");
        TwitterFactory tf = new TwitterFactory(cb.build());
         twitter = tf.getInstance();
    }




    public static void getAllTweets(){

        createTwitter();
        try {
            twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    public static void main(String [] args)
    {
       TwitterAPI.getAllTweets();
    }
}
