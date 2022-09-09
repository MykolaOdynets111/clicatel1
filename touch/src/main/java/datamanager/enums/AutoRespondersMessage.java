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
    END_CHAT("Out of Support Hours message", Message.END_CHAT_MESSAGE);

    private final String title;
    private final String text;

    public static String getMessageByTitle(String title) {
        return Arrays.stream(AutoRespondersMessage.values())
                .filter(m -> m.getTitle().equals(title))
                .findFirst().orElseThrow(NoSuchElementException::new)
                .getText();
    }

    private static class Message {
        public static final String END_CHAT_MESSAGE = "Thank you for reaching out. " +
                "You've reached us outside of our support hours. We will get back to you as soon as possible.";
        public static final String CONNECT_AGENT_MESSAGE = "Thanks ${firstName}, " +
                "let me connect you with one of our available agents.";
    }
}

