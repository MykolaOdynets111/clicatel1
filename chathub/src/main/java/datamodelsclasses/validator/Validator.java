package datamodelsclasses.validator;

import api.ChatHubApiHelper;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import org.testng.Assert;

import java.util.Map;

public class Validator {
    public static void validatedErrorResponse(String URL, Map<String, String> data ){
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        String error = ChatHubApiHelper.getChatHubQuery(URL, errorData.getResponseCode()).asString();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n"+
                        "Error from server:" + error);
    }
}
