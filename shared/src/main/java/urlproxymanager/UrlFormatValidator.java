package urlproxymanager;

import java.net.URL;

public class UrlFormatValidator {
    public static boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
