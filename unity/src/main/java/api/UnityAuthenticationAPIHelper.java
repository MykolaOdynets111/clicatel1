package api;

import datamanager.UnityClients;
import datamodelclasses.authentication.AuthTokenBody;
import datamodelclasses.jwt.UnityJWT;
import driverfactory.UnityURLs;

import java.util.HashMap;
import java.util.Map;

public class UnityAuthenticationAPIHelper extends MainApi {
    public static String getAuthToken(UnityClients unityClient) {
        //Create a body
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", unityClient.getUsername());
        credentials.put("password", unityClient.getPassword());
        //Get Token and ID
        AuthTokenBody authTokenBody = postQueryWithoutAuth(UnityURLs.AUTH_ACCOUNTS, credentials,200).as(AuthTokenBody.class);
        Map<String, Object> body = new HashMap<>();
        body.put("token", authTokenBody.getToken());
        body.put("accountId", authTokenBody.getAccounts().get(0).getId());
        //Return JWT
        return postQueryWithoutAuth(UnityURLs.JWT_ACCOUNT,body,200).as(UnityJWT.class).getToken();
    }
}
