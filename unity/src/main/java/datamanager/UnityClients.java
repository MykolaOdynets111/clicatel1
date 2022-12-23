package datamanager;

import drivermanager.Environment;
import lombok.*;

import static drivermanager.Environment.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UnityClients {
    // =======  Unity Clients used by CHAT 2 PAY ======== //

    QA_C2P_USER_ONE("chat2payqauser11+echo1@gmail.com", "Password#1","QA_C2P_USER_0NE",  QA),

    // =======  Unity Clients used by CHAT HUB ======== //

    QA_CH_USER_ONE("chathubabc123@gmail.com", "Password#1","QA_CH_USER_ONE",  QA),

    DEMO_CHAT_2_PAY_USER("chat2payqauser11+chathub@gmail.com", "Password#1");

    String username;
    String password;
    String email;
    String userPass;
    String clientName;
    Environment env;

    UnityClients(String username, String password){
        this.username= username;
        this.password = password;
    }
    UnityClients(String email, String userPass, String clientName, Environment env) {
        this.email = email;
        this.userPass = userPass;
        this.clientName = clientName;
        this.env = env;
    }
    public String getUnityClientPass() {

        return this.userPass;
    }

    public String getUnityClientEmail() {

        return this.email;
    }

    public String getUnityClientEnv() {

        return this.env.getEnv();
    }


    public UnityClients setEmail(String email) {
        this.email = email;
        return this;
    }


    public UnityClients setEnv(String env) {
        this.env = Environment.fromString(env);
        return this;
    }

    public UnityClients setPass(String pass) {
        this.userPass = pass;
        return this;
    }

    public UnityClients setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

}
