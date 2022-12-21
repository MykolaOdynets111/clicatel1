package datamanager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Credentials {

    DEMO_CHAT_2_PAY_USER("chat2payqauser11+chathub@gmail.com", "Password#1");

    String username;
    String password;

}
