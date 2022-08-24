package emailhelper;

import com.sun.mail.imap.IMAPMessage;
import org.testng.Assert;
import javax.mail.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CheckEmail {

    private Message lastTargetMessage;

    public Message getLastTargetMessage(){
        return lastTargetMessage;
    }

    public static String getConfirmationURL(String expectedSender, int wait){
        try {
            if (!GmailConnector.getFolder().isOpen())
                GmailConnector.getFolder().open(Folder.READ_WRITE);
            if(expectedSender.equals("Clickatell <mc2-devs@clickatell.com>")) return getConfirmation(expectedSender, wait);
            if(expectedSender.equals("Clickatell <no-reply@clickatell.com>")) return getResetConfirmation(expectedSender, wait);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("error with getting url \n" + Arrays.toString(e.getStackTrace()));
        }
        return "none";
    }

    private static String getConfirmation(String expectedSender, int wait) throws Exception {
        String verificationCode = null;

        if(newLetterFromSenderArrives(expectedSender, wait)) {
            List<Message> targetSenderNewMails =
                    getNewMessagesFromSender(GmailConnector.getFolder(), expectedSender);
            List<Message> targetMessages = targetSenderNewMails.stream()
                    .filter(e -> !(GmailParser.getAgentConfirmationLink(e, expectedSender)).equals("none"))
                    .collect(Collectors.toList());
            Message targetMessage = targetMessages.get(targetMessages.size()-1);
            verificationCode = GmailParser.getAgentConfirmationLink(targetMessage, expectedSender);
            targetMessage.setFlag(Flags.Flag.SEEN, true);
            targetMessage.setFlag(Flags.Flag.DELETED, true);
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
            return verificationCode;
        }
        GmailConnector.getFolder().close(false);
        GmailConnector.getStore().close();
        return "none";
    }

    private static String getResetConfirmation(String expectedSender, int wait) throws Exception {
        String verificationCode = null;

        if(newLetterFromSenderArrives(expectedSender, wait)) {
            List<Message> targetSenderNewMails =
                    getNewMessagesFromSender(GmailConnector.getFolder(), expectedSender);
            List<Message> targetMessages = targetSenderNewMails.stream()
                    .filter(e -> !(GmailParser.getResetPasswordLink(e, expectedSender)).equals("none"))
                    .collect(Collectors.toList());
            Message targetMessage = targetMessages.get(targetMessages.size()-1);
            verificationCode = GmailParser.getResetPasswordLink(targetMessage, expectedSender);
            targetMessage.setFlag(Flags.Flag.SEEN, true);
            targetMessage.setFlag(Flags.Flag.DELETED, true);
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
            return verificationCode;
        }
        GmailConnector.getFolder().close(false);
        GmailConnector.getStore().close();
        return "none";
    }

    public static Message getLastMessageBySender(String expectedSender, int secondsWait){
        Message lastTargetMessage = null;
        try {
            if (newLetterFromSenderArrives(expectedSender, secondsWait)) {
                List<Message> targetSenderNewMails = getAllMessagesFromSender(GmailConnector.getFolder(), expectedSender);
                lastTargetMessage = targetSenderNewMails.get(targetSenderNewMails.size()-1);
                return lastTargetMessage;
            }
            GmailConnector.getFolder().close(false);
            GmailConnector.getStore().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastTargetMessage;
    }

    public static String getEmailSubject(Message message){
        return GmailParser.parseSubject(message);
    }

    public static List<String> getChatTranscriptEmailContent(Message message){
        List<String> emailContent = null;
        try {
            if (!GmailConnector.getFolder().isOpen()) {
                GmailConnector.getFolder().open(Folder.READ_WRITE);
            }
            emailContent = GmailParser.parseChatTranscriptEmail(message);
            message.setFlag(Flags.Flag.SEEN, true);
            message.setFlag(Flags.Flag.DELETED, true);
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
            return emailContent;
        } catch (MessagingException e){
            e.printStackTrace();
        }

        return emailContent;
    }

    public static boolean newLetterFromSenderArrives(String expectedSender, int secondsWait){
        List<Message> targetSenderNewMails = getNewMessagesFromSender(GmailConnector.getFolder(), expectedSender);
        for (int i=0; i<secondsWait; i++){
            if(targetSenderNewMails.isEmpty()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                targetSenderNewMails = getNewMessagesFromSender(GmailConnector.getFolder(), expectedSender);
            }
            else
                return true;
        }
        return false;
    }

    public List<Message> waitForNeLetterToArrive(String expectedSender, int secondsWait){
        if(!newLetterFromSenderArrives(expectedSender, secondsWait)) Assert.fail("No new letter arrives");
        return  getNewMessagesFromSender(GmailConnector.getFolder(), expectedSender);
    }

    public static void deleteAllNewMassages(){
        List<Message> msgs = getAllNewMessages(GmailConnector.getFolder());
        try {
            for(Message msg : msgs) {
                msg.setFlag(Flags.Flag.SEEN, true);
                msg.setFlag(Flags.Flag.DELETED, true);
        }
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMassage(Message msg){
        try {
            msg.setFlag(Flags.Flag.SEEN, true);
            msg.setFlag(Flags.Flag.DELETED, true);
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static List<Message> getAllNewMessages(Folder folder){
        List<Message> msgs = new ArrayList<>();
        try {
            msgs =  Arrays.stream(folder.getMessages()).filter(msg ->
                    notOpenedMessage(msg)).collect(Collectors.toList());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msgs;
    }

    public static List<Message> getNewMessagesFromSender(Folder folder, String sender){
        List<Message> allMsgs = getAllMessagesFromSender(folder, sender);
        return allMsgs.stream().filter(msg -> notOpenedMessage(msg)).collect(Collectors.toList());
    }

    public static List<Message> getAllMessagesFromSender(Folder folder, String sender) {
        List<Message> msgs = new ArrayList<>();
        try {
            msgs =  Arrays.stream(folder.getMessages()).filter(e ->
                        getSenderAsString(e).equals(sender)).collect(Collectors.toList());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msgs;
    }

    private static String getSenderAsString(Message msg){
        String sender = "";
        try {
            sender = ((IMAPMessage) msg).getSender().toString();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return sender;
    }

    public static boolean notOpenedMessage(Message e) {
        try {
            return !e.isSet(Flags.Flag.SEEN);
        } catch (MessagingException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public static void clearEmailInbox(){
        try {
            if (!GmailConnector.getFolder().isOpen())
                GmailConnector.getFolder().open(Folder.READ_WRITE);
            for (Message message : GmailConnector.getFolder().getMessages()){
                message.setFlag(Flags.Flag.DELETED, true);
            }
            GmailConnector.getFolder().close(true);
            GmailConnector.getStore().close();
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

}