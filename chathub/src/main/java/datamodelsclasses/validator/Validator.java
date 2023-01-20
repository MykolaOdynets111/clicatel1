package datamodelsclasses.validator;

import api.ChatHubApiHelper;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import org.testng.Assert;

import java.util.Map;

public class Validator {
    public static void validatedErrorResponseforGet(String url, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.getChatHubQuery(url, errorData.getResponseCode()).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

    public static void validatedErrorResponseforPost(String url, Map<String, String> body, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.postChatHubQuery(url, body).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

    public static void validatedErrorResponseforPutWithoutAuth(String url, Map<String, String> body, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.putChatHubQueryWithoutAuth(url,body, 404).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

}