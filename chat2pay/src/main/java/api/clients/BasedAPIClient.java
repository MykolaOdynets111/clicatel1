package api.clients;

import io.restassured.response.Response;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

import static utils.PropertiesReader.getProperty;

@Getter
public class BasedAPIClient {

    public static AtomicReference<BasedAPIClient> basedAPIClient = new AtomicReference<>(new BasedAPIClient());
    private Response response;
    protected static String baseUrl;
    protected static String c2pUrl;
    protected static String accountsEndpoint;
    protected static String signInEndpoint;
    protected static String widgetsEndpoint;
    protected static String eMail;
    protected static String password;



    static {
        baseUrl = getProperty("api.unity.base.url");
        c2pUrl = getProperty("c2p.base.url");
        accountsEndpoint = getProperty("unity.accounts");
        signInEndpoint = getProperty("unity.signin");
        widgetsEndpoint = getProperty("c2p.widgets") ;
        eMail =getProperty("email");
        password =getProperty("password");
    }


}
