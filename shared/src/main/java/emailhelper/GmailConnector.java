package emailhelper;

import org.testng.Assert;

import javax.mail.*;
import java.util.Properties;

public class GmailConnector {

    private static String host = "imap.gmail.com";

    private static GmailAutentication.GMailAuthenticator gMailAuthenticator;

    private static ThreadLocal<Store> store = new ThreadLocal<>();

    private static ThreadLocal<Folder> folder = new ThreadLocal<>();


    public static synchronized Store getStore(){
        return store.get();
    }

    public static synchronized void setStore(Store newStore){
        store.set(newStore);
    }

    public static synchronized Folder getFolder(){
        return folder.get();
    }

    public static synchronized void setFolder(Folder newFolder){
        folder.set(newFolder);
    }

    public static void reopenFolder(){
        try {
            if(!getFolder().isOpen()) getFolder().open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static Folder loginAndGetInboxFolder(String... mail){
        gMailAuthenticator = GmailAutentication.initGMailAuthenticator(host, mail);
        Properties properties = GmailProperties.configureProperties(host);
        setStore(initStore(properties));
        try {
            getStore().connect(gMailAuthenticator.host, gMailAuthenticator.mail, gMailAuthenticator.password);
            setFolder(getStore().getFolder("INBOX"));
            getFolder().open(Folder.READ_WRITE);
        } catch (Exception e) {
            Assert.fail("\n MessagingException e: \n" + e + "mail creds: " + mail[0] + " " + mail[1]);
        }
        return getFolder();
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

    public static void cleanMailObjects(){
        folder.remove();
        store.remove();
    }
}
