package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.auth.TokenBase;
import com.touch.models.touch.auth.TokenDto;
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

    public Response getListOfTockens(String token) {
        return requestEngine.getRequest(EndPointsClass.AUTH_TOCKEN,new Header("Authorization", token));
    }

    public Response addTocken(TokenDto tokenDto, String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_TOCKEN, null, null, tokenDto, new Header("Authorization", token));
    }
    public Response authentificateTocken(TokenBase tokenBase, String token) {
        return requestEngine.postRequest(EndPointsClass.AUTH_AUTHENTICATED, null, null, tokenBase, new Header("Authorization", token));
    }




}
