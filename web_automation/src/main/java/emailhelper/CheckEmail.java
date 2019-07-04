package emailhelper;

import com.sun.mail.imap.IMAPMessage;
import datamanager.TwitterUsers;
import javax.mail.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class CheckEmail {

    private static String host = "imap.gmail.com";

    public static String getConfirmationURL(String expectedSender, String... mail) throws Exception {
        GMailAuthenticator gMailAuthenticator = initGMailAuthenticator(mail);
        Properties properties = configureProperties();
        Store store = initStore(properties);
        Folder folder = loginAndGetInboxFolder(gMailAuthenticator, store);

        String verificationCode = null;
        folder.getNewMessageCount();

        for (int i=0; i<21; i++){
            List<Message> emails = Arrays.asList(folder.getMessages());
            List<Message> targetEmails = new ArrayList<>();
            for (Message msg: emails) {
                String sender = ((IMAPMessage) msg).getSender().toString();
                if (sender.equals(expectedSender)) {
                    targetEmails.add(msg);
                }
            }
            if(!targetEmails.isEmpty()&&targetEmails.stream().anyMatch(CheckEmail::ifNotOpenedMessageExists)){
                Message targetMessage = targetEmails.stream()
                        .filter(CheckEmail::ifNotOpenedMessageExists).findFirst().get();
                verificationCode = parser(targetMessage);
                targetMessage.setFlag(Flags.Flag.SEEN, true);
                targetMessage.setFlag(Flags.Flag.DELETED, true);
                folder.close(true);
                store.close();
                return verificationCode;
            }
        }
        folder.close(false);
        store.close();
        return "";
    }

    public static boolean ifNotOpenedMessageExists(Message e) {
        try {
            if(e.isSet(Flags.Flag.SEEN))
                return false;
            else
                return true;
        } catch (MessagingException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    private static Folder loginAndGetInboxFolder(GMailAuthenticator gMailAuthenticator, Store store){
        Folder folder = null;
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


    private static Properties configureProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/project.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "587");
        properties.put("mail.imap.auth", "true");
        return properties;
    }

    private static String parser(javax.mail.Message newTargetMail) {
        String emailContent = null;
        try {
            emailContent = (String) ((Multipart) newTargetMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        List<String> lines = Arrays.asList(emailContent.split("\r\n"));
        return lines.stream().filter(e -> e.contains("Click on this link to create a password")).findFirst().get();
    }

    private static GMailAuthenticator initGMailAuthenticator(String[] mail){
        GMailAuthenticator gMailAuthenticator;
        if(mail.length == 0){
            gMailAuthenticator = new GMailAuthenticator(TwitterUsers.getLoggedInUser().getTwitterUserEmail(),
                    TwitterUsers.getLoggedInUser().getTwitterUserPass(), host);
        }else{
            gMailAuthenticator = new GMailAuthenticator(mail[0], mail[1], host);
        }
        return gMailAuthenticator;
    }



    static class GMailAuthenticator extends Authenticator {
        String mail;
        String password;
        String host;
        public GMailAuthenticator (String username, String password, String host){
            super();
            this.mail = username;
            this.password = password;
            this.host = host;
        }
        public PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(mail, password);
        }
    }

}