package emailhelper;

import org.testng.Assert;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
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


    public static String getAgentConfirmationLink(javax.mail.Message newEMail, String expectedSender) {
        String emailContent = null;
        try {
            emailContent = (String) ((Multipart) newEMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        List<String> lines = Arrays.asList(emailContent.split("\r\n"));
        return lines.stream().filter(e -> e.contains(getCodePattern(expectedSender.toLowerCase()))).findFirst().orElse("none");
    }


    public static String getResetPasswordLink(javax.mail.Message newEMail, String expectedSender) {
        String emailContent = null;
        try {
            emailContent = (String) ((Multipart) newEMail.getContent()).getBodyPart(0).getContent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        List<String> lines = Arrays.asList(emailContent.split("\r\n"));
        String url = lines.stream().filter(e -> e.startsWith("http")).findFirst().orElse("none");
        if(url.equalsIgnoreCase("none")){
            Assert.fail("message\n" + lines.toString());
        }
        return url;
    }


}
