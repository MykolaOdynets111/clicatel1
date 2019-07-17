package emailhelper;

import datamanager.TwitterUsers;

import javax.mail.*;


public class GmailAutentication {


    public static GMailAuthenticator initGMailAuthenticator(String host, String[] mail){
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
