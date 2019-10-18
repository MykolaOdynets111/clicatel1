package endtoend.basetests;


import listeners.TestAllureListener;
import mc2api.ApiHelperPlatform;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners({TestAllureListener.class})
public class SignUpBaseTest extends BaseTest {

    protected ThreadLocal<String> email = new ThreadLocal<>();
    protected ThreadLocal<String> pass = new ThreadLocal<>();
    protected ThreadLocal<String> firstName = new ThreadLocal<>();
    protected ThreadLocal<String> accountName = new ThreadLocal<>();


    @BeforeClass()
    protected void readCreatedAccountProps(){
        try(FileInputStream in = new FileInputStream("src/test/resources/newaccount.properties")) {
            Properties props = new Properties();
            props.load(in);
            email.set(props.getProperty("email"));
            pass.set(props.getProperty("pass"));
            firstName.set(props.getProperty("firstName"));
            accountName.set(props.getProperty("accountName"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

//    @AfterGroups("newaccount")
//    protected void closeAccount(){
//        System.out.println("\n !!     protected void closeAccount() !! \n");
//        try {
//            FileInputStream in = new FileInputStream("src/test/resources/newaccount.properties");
//            Properties props = new Properties();
//            props.load(in);
//
//            ApiHelperPlatform.closeAccount(props.getProperty("accountName"),
//                    props.getProperty("email"), props.getProperty("pass"));
//        }catch (IOException | AssertionError e){
//            // Nothing to do. Account was not activated.
//        }
//    }
}
