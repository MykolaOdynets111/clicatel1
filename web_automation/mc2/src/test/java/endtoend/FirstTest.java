package endtoend;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class FirstTest {

    @Description("The very first test")
    @Test
    public void assertMethod(){
        Assert.assertEquals(1, 1, "equals");
    }
}
