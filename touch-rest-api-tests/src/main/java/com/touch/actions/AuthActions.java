package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.auth.*;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AuthActions {

    RequestEngine requestEngine;

    public AuthActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getListOfTokens(String token) {
        return requestEngine.getRequest(EndPointsClass.AUTH_TOCKEN,new Header("Authorization", token));
    }

    public Response addToken(TokenDto tokenDto, String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_TOCKEN, null, null, tokenDto, new Header("Authorization", token));
    }
    public Response authentificateToken(TokenBase tokenBase, String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_AUTHENTICATED, null, null, tokenBase, new Header("Authorization", token));
    }

    public String getRefreshToken(String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_REFRESH_TOKEN, null, null, new Header("Authorization", token)).as(RefreshTokenResponse.class).getRefreshToken();
    }
    public String getAccessToken(AccessTokenRequest accessTokenRequest, String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_ACCESS_TOKEN, null, null, accessTokenRequest, new Header("Authorization", token)).as(AccessTokenResponse.class).getAccessToken();
    }

}
