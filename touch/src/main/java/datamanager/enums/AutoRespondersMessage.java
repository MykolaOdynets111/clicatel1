package datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@ToString
@AllArgsConstructor
public enum AutoRespondersMessage {

    CONNECT_AGENT("Connect Agent message", Message.CONNECT_AGENT_MESSAGE),
    DIRECTING_AGENT("Directing to Agent (Social Channels)", Message.DIRECTING_AGENT_MESSAGE),
    SESSION_TIMEOUT("Session Timeout message (Web Chat)", Message.SESSION_TIMEOUT_MESSAGE),
    WEB_CHAT_GREETINGS("Web Chat Greeting message", Message.WEB_CHAT_GREETINGS_MESSAGE),
    CONNECTING_AGENT("Connecting Agent message", Message.CONNECTING_AGENT_MESSAGE),
    CONNECTING_AGENT_SC("Connecting Agent message (Social Channels)", Message.CONNECTING_AGENT_SC_MESSAGE),
    AGENT_BUSY("Agent Busy message", Message.AGENT_BUSY_MESSAGE),
    OUT_OF_SUPPORT_HOURS("Out of Support Hours message", Message.OUT_OF_SUPPORT_HOURS_MESSAGE),
    END_CHAT("End Chat message", Message.END_CHAT_MESSAGE),
    DIRECT_CHANNEL("Direct Channel message", Message.DIRECT_CHANNEL_MESSAGE),
    END_CHAT_AND_OPT("End Chat and Opt-Out Keywords message (All Channels)", Message.END_CHAT_AND_OPT_MESSAGE);

    private final String title;
    private final String text;

    public static String getMessageByTitle(String title) {
        return Arrays.stream(AutoRespondersMessage.values())
                .filter(m -> m.getTitle().equals(title))
                .findFirst().orElseThrow(NoSuchElementException::new)
                .getText();
    }

    private static class Message {
        public static final String CONNECT_AGENT_MESSAGE = "Thanks ${firstName}, let me connect you with one of our available agents.";
        public static final String DIRECTING_AGENT_MESSAGE = "We are directing you to the first available Agent. Thank you for your patience.";
        public static final String SESSION_TIMEOUT_MESSAGE = "The session timed out due to inactivity. Please type something to start a new chat.";
        public static final String WEB_CHAT_GREETINGS_MESSAGE = "Thank you for reaching out. How can we help you?";
        public static final String CONNECTING_AGENT_MESSAGE = "Thanks for your patience, ${firstName}. You have now been connected with ${agentName}.";
        public static final String CONNECTING_AGENT_SC_MESSAGE = "Thanks for your patience. You have now been connected with ${agentName}.";
        public static final String AGENT_BUSY_MESSAGE = "Thank you for reaching out. All our Agents are currently busy, but will attend to you as soon as possible.";
        public static final String OUT_OF_SUPPORT_HOURS_MESSAGE = "Thank you for reaching out. You've reached us outside of our support hours. We will get back to you as soon as possible.";
        public static final String END_CHAT_MESSAGE = "Thank you. Please don't hesitate to reach out if you ever need help!";
        public static final String DIRECT_CHANNEL_MESSAGE = "For further questions please contact us via Direct Message (DM).";
        public static final String END_CHAT_AND_OPT_MESSAGE = "Please note that you can end this chat at any time during your engagement with our agents by replying with //end or //stop.";
    }
}

