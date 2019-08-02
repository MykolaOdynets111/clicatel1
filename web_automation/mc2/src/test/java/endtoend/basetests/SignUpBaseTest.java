package endtoend.basetests;


import listeners.TestAllureListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners({TestAllureListener.class})
public class SignUpBaseTest extends BaseTest {

    protected ThreadLocal<String> email = new ThreadLocal<>();
    protected ThreadLocal<String> pass = new ThreadLocal<>();
    protected ThreadLocal<String> firstName = new ThreadLocal<>();

    @BeforeClass()
    protected void readCreatedAccountProps(){
        try {
            FileInputStream in = new FileInputStream("src/test/resources/newaccount.properties");
            Properties props = new Properties();
            props.load(in);
            email.set(props.getProperty("email"));
            pass.set(props.getProperty("pass"));
            firstName.set(props.getProperty("firstName"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
