package com.touch.steps.serenity;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.junit.Assert;

import java.util.List;

/**e
 * Created by kmakohoniuk on 11/22/2016.
 */
public class VerificationActions extends ScenarioSteps {

    @Step
    public void verifyThatValuesAreEqual(String expected, String actual){
        if(actual.contains("\n")){
            actual = actual.replace("\n", " ");
        }
        Assert.assertEquals("Values are not equal ", expected, actual);
    }
    @Step
    public void verifyThatValuesAreMatching(String regexp, String actual){
        Assert.assertTrue("Values are not match. Actual; value is: "+actual, actual.matches(regexp));
    }
    @Step
    public void verifyThatListsContainsSameElements(List<String> expected, List<String> actual){
        Assert.assertTrue(actual.containsAll(expected));

    }

}
