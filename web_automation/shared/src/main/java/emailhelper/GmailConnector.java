package emailhelper;
import javax.mail.*;
import java.util.Properties;

public class GmailConnector {

    private static String host = "imap.gmail.com";

    private static GmailAutentication.GMailAuthenticator gMailAuthenticator;

    private static Store store;

    private static Folder folder;


    public static Store getStore(){
        return store;
    }

    public static Folder getFolder(){
        return folder;
    }

    public static void reopenFolder(){
        try {
            if(!folder.isOpen()) folder.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static Folder loginAndGetInboxFolder(String... mail){
        gMailAuthenticator = GmailAutentication.initGMailAuthenticator(host, mail);
        Properties properties = GmailProperties.configureProperties(host);
        store = initStore(properties);
        try {
            store.connect(gMailAuthenticator.host, gMailAuthenticator.mail, gMailAuthenticator.password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return folder;
    }

    private static Store initStore(Properties properties) {
        Session session = Session.getDefaultInstance(properties);
        Store store = null;
        try {
            store = session.getStore("imaps");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return store;
    }
}
