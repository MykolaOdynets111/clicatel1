package com.touch.models;

import com.touch.utils.ConfigApp;

public class EndPointsClass {

    public static final String APP_CONFIG = ConfigApp.API_VERSION+"/app-config";
    public static final String APP_CONFIG_PROFILE = ConfigApp.API_VERSION+"/app-config/profile";


// TOUCH END POINTS
    //Tenants endpoint
    public final static String TENANTS =  ConfigApp.API_VERSION+"/tenants";
    public final static String TENANTS_NEW =  "/v6/tenants";
    public final static String TENANT =  TENANTS+"/{tenantId}";
    public final static String ADDRESS =  TENANT+"/address/{addressId}";
    public final static String COLOURS =  TENANT+"/colours";
    public final static String RESOURCES =  TENANT+"/resources/{name}";
    public final static String COMMON_FLOWS =  TENANTS+"/common/flows";
    public final static String COMMON_FLOW =  COMMON_FLOWS+"/files/{flow-name}";
    public final static String DELETE_COMMON_FLOW =COMMON_FLOWS+"/{flow-name}";
    public final static String TENANT_FLOWS =  TENANTS+"/{tenant-id}/flows";
    public final static String TENANT_FLOW =  TENANT_FLOWS+"/files/{flow-name}";
    public final static String DELETE_TENANT_FLOW =  TENANT_FLOWS+"/{flow-name}";
    //User profiles endpoint
    public static final String TOUCH_USER_PROFILES = ConfigApp.API_VERSION+"/user-profiles";
    public static final String TOUCH_USER_PROFILE = TOUCH_USER_PROFILES+"/{profileId}";
    public static final String TOUCH_USER_PROFILE_IMAGE = TOUCH_USER_PROFILE+"/image";
}
