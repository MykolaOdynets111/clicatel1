package emailhelper;

import javax.mail.Message;
import org.testng.Assert;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailParser {


    private static String getCodePattern(String sender){
        switch (sender){
            case "clickatell <mc2-devs@clickatell.com>": {
                return "Click on this link to create a password";
            }
            default:{
                return "";
            }
        }
    }

    public static String parseSubject(Message newEmail){
        String emailTitle = null;
        try{
            emailTitle = newEmail.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return emailTitle;
    }

    public static String getAgentConfirmationLink(javax.mail.Message newEMail, String expectedSender) {
        String emailContent = null;
        try {
            emailContent = (String) ((Multipart) newEMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        List<String> lines = Arrays.asList(emailContent.split("\r\n"));
        return lines.stream().filter(e -> e.contains(getCodePattern(expectedSender.toLowerCase()))).findFirst().orElse("none");
    }

    public static List<String> parseChatTranscriptEmail(Message newEMail) {
        String emailContent = null;
        List<String> chat = new ArrayList<>();
        String [] list = null;
        try {
            emailContent = (String) ((Multipart) newEMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

        list = emailContent.split("\r\n");

        for (String line : list){
            if (line.startsWith("BOT:") || line.startsWith("AGENT:") || line.startsWith("USER:")) {
                line = line.substring(line.indexOf(" ") + 1);
                chat.add(line);
            }
        }
        return chat;
    }

    public static String getResetPasswordLink(javax.mail.Message newEMail, String expectedSender) {
        String emailContent = null;
        try {
            emailContent = (String) ((Multipart) newEMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        List<String> lines = Arrays.asList(emailContent.split("\r\n"));
        String url = lines.stream().filter(e -> e.startsWith("http")).findFirst().orElse("none");
        if(url.equalsIgnoreCase("none")){
            url = lines.stream().filter(e -> e.startsWith("Click here to verify your email address. [ http")).findFirst().orElse("none");
            try {
                url = url.split("\\[")[1].replace("\\[", "").replace("]", "");
            }catch (ArrayIndexOutOfBoundsException e) {
                Assert.fail("message\n" + lines.toString());
            }
        }
        return url;
    }


}
