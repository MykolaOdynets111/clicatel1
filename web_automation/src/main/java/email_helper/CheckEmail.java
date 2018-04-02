package email_helper;

//import Pages.SignUp.ThankYouPage;
import org.jsoup.nodes.Document;
//import support.Url;

import javax.mail.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ozaporozhets on 26-Apr-16.
 */

public class CheckEmail {

    private static String host = "imap.gmail.com";

    public static String getConfirmationURLforNewCompany(String strSubject, String... mail) throws Exception {
        GMailAuthenticator gMailAuthenticator = initGMailAuthenticator(mail);
        Properties properties = configureProperties();
        Folder folder = loginAndGetInboxFolder(gMailAuthenticator, properties);

        boolean isMailFound = false;
        Message mailFromMetis = null;

        int iteration = 0;

        while(!folder.getMessage(folder.getMessageCount()).getAllRecipients()[0].toString().equals(mail[0].toString())){
            iteration++;
            Thread.sleep(300);
//      System.out.println("iteration time - " + System.currentTimeMillis());
            if(iteration == 100){
//                ThankYouPage thankYouPage = new ThankYouPage();
//                thankYouPage.sendInvitation();
//                thankYouPage.checkAlertPresent();
//                thankYouPage.checkAlert();
            }else if(iteration > 200){
                break;
            }
        }
        int messageCount = folder.getMessageCount();
        if(folder.getMessage(messageCount).getAllRecipients()[0].toString().equals(mail[0].toString())) {
            mailFromMetis = folder.getMessage(messageCount);
            isMailFound = true;
        }
        String link = parser(isMailFound, mailFromMetis, strSubject, properties);
//    System.out.print("\nlink - "+link);
        return link;
    }

    public static String getConfirmationURL(String strSubject, String... mail) throws Exception {
        GMailAuthenticator gMailAuthenticator = initGMailAuthenticator(mail);
        Properties properties = configureProperties();
        Folder folder = loginAndGetInboxFolder(gMailAuthenticator, properties);

        boolean isMailFound = false;
        Message mailFromMetis = null;

        int iteration = 0;

        while(!folder.getMessage(folder.getMessageCount()).getAllRecipients()[0].toString().equals(mail[0].toString())){
            iteration++;
            Thread.sleep(400);
            if(iteration == 100){
                break;
            }
        }
        int messageCount = folder.getMessageCount();
        if(folder.getMessage(messageCount).getAllRecipients()[0].toString().equals(mail[0].toString())) {
            mailFromMetis = folder.getMessage(messageCount);
            isMailFound = true;
        }
        return parser(isMailFound, mailFromMetis, strSubject, properties);
    }

    private static Folder loginAndGetInboxFolder(GMailAuthenticator gMailAuthenticator, Properties properties){
        Session session = Session.getDefaultInstance(properties);
        Store store = null;
        try {
            store = session.getStore("imaps");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
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

    private static String parser(boolean isMailFound, javax.mail.Message mailFromMetis, String strSubject, Properties properties) throws Exception {
        String hrefRes = null;
        String href = null;

        //Test fails if no unread mail was found from Metis
        if (!isMailFound) {
            throw new Exception("Could not find new mail from METIS");
            //Read the content of mail and launch registration URL
        } else {
            String line;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromMetis.getInputStream()));

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            //Testing
            //     System.out.println(buffer);

            //Logic to split the message and get the Registration URL goes here
            if (strSubject.equals("Reset password instructions"))
            {
                Pattern p = Pattern.compile("<http:(.*?)>", Pattern.DOTALL);
                Matcher m = p.matcher(buffer);
                while (m.find()) {
//          System.out.println(m.group(1));
                    hrefRes = "http:" + m.group(1);
                }
            }

            Document document;

            if (strSubject.equals("Confirmation instructions")) {
                Pattern p = Pattern.compile("thislink<(.*?)>", Pattern.DOTALL);
                Matcher m = p.matcher(buffer);
                while (m.find()) {
//          System.out.println(m.group(1));
                    href = m.group(1);
                    int lenght = href.length();
                    properties.load(new FileInputStream("src/main/resources/project.properties"));
//                    if(Url.getUrl().equals("https://flash:5af36f282c8395e1cf2337a247fc321b@go.getmetis.com/")){
//                        hrefRes = href.substring(0, lenght-22) + href.substring(lenght-20);
//                    }else if(Url.getUrl().toUpperCase().contains("local".toUpperCase())){
////            System.out.println("\n"+href);
//                        String[] links = href.split("3D");
//                        hrefRes = links[0]+links[1].replace("=", "");
//                        //           System.out.println("\n"+hrefRes);
//                    } else {
////            System.out.println("\n"+href+"\n----------------------------------------------------------------");
//                        hrefRes = href.substring(0, lenght - 23) + href.substring(lenght - 21, href.lastIndexOf('=')) + href.substring(href.lastIndexOf('=') + 1);
////          System.out.println("\n"+hrefRes+"\n----------------------------------------------------------------");
//                    }
                }
            }

            if (strSubject.equals("Invitation instructions"))
            {
                Pattern p = Pattern.compile("thislink<(.*?)>", Pattern.DOTALL);
                Matcher m = p.matcher(buffer);
                while (m.find()) {
                    System.out.println(m.group(1));
                    href = m.group(1);
                    int length = href.length();
//                    if(Url.getUrl().equals("https://flash:5af36f282c8395e1cf2337a247fc321b@go.getmetis.com/")){
//                        hrefRes = href.substring(0, length-22) + href.substring(length-20);
//                    }else if(Url.getUrl().toUpperCase().contains("local".toUpperCase())){
//                        String[] links = href.split("3D");
//                        hrefRes = links[0]+links[1].replace("=", "");
//                    } else {
//                        hrefRes = href.substring(0, length - 23) + href.substring(length - 21, href.lastIndexOf('=')) + href.substring(href.lastIndexOf('=') + 1);
//                    }
                }
            }
        }
        return hrefRes;
    }

    private static GMailAuthenticator initGMailAuthenticator(String[] mail){
        GMailAuthenticator gMailAuthenticator;
        if(mail.length == 0){
            gMailAuthenticator = new GMailAuthenticator("automation@perfectial.com", "PerfTaqa01", host);
        }else if(mail[0].contains("automation")) {
            gMailAuthenticator = new GMailAuthenticator(mail[0], "PerfTaqa01", host);
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