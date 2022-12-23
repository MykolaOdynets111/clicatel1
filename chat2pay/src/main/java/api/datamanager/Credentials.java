package api.datamanager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Credentials {

    DEV_CHAT_2_PAY_USER("testuserc2p+001@gmail.com", "Password#1");

    String username;
    String password;
}
