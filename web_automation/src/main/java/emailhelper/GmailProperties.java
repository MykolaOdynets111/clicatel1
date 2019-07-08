package emailhelper;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;


public class GmailProperties {

        public static Properties configureProperties(String host){
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "587");
        properties.put("mail.imap.auth", "true");
        return properties;
    }
}
