package urlproxymanager;

import api.MainApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Proxymanager {
    public int addProxy(String url) throws IOException {
        //Check if the URL is working
        //URL link = new URL(postActiveConfiguration.getAuthenticationLink());
        URL link = new URL(url);
        String proxy = "proxy.mydomain.com";
        String port = "8080";
        Properties sys = System.getProperties();
        sys.setProperty("http.proxyHost", proxy);
        sys.setProperty("http.proxyPort", port);
        HttpURLConnection connection = (HttpURLConnection) link.openConnection();
        int code = connection.getResponseCode();
        return code;
    }
}
