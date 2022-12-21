package validators;

import api.ChatHubApiHelper;
import datamodelsclasses.validatebjects.ErrorValidatorObject;
import org.testng.Assert;

import java.util.Map;

public class Validator {

    public static void validatedErrorResponse(String URL, Map<String, String> data ){
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        Assert.assertTrue(ChatHubApiHelper.getChatHubQuery(URL, errorData.getResponseCode()).asString().contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned");
    }
}
