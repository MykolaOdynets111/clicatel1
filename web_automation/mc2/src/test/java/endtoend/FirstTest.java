package endtoend;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.StepOne;

@Test
public class FirstTest extends BaseTest {


    @Description("The very first test")
    @Test
    public void assertMethod(){
        Assert.assertEquals(1, new StepOne().createInt(), "equals");
    }
}
