package datamanager;

public enum Credentials {
    Demo_Chat2PayUser("chat2payqauser11+chathub@gmail.com", "Password#1");
    String username;
    String password;

    Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return this.username;}
    public String getPassword() {return this.password;}

}
