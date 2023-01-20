package datamodelsclasses.validator;

import api.ChatHubApiHelper;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import org.testng.Assert;

import java.util.Map;

public class Validator {
    public static void validatedErrorResponseforGet(String URL, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.getChatHubQuery(URL, errorData.getResponseCode()).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

    public static void validatedErrorResponseforPost(String URL, Map<String, String> body, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.postChatHubQuery(URL, body).asString();
        System.out.println("wait here");
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

    public static void validatedErrorResponseforPutWithAuthWithoutBody(String url, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.putChatHubQuerywithAuthNoBody(url,errorData.getResponseCode()).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }
}