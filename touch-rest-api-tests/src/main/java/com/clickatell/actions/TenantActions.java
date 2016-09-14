package com.clickatell.actions;

import com.clickatell.engines.RequestEngine;
import com.clickatell.models.EndPointsClass;
import com.clickatell.models.touch.tenant.*;
import io.restassured.response.Response;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class TenantActions {

    RequestEngine requestEngine;

    public TenantActions(RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public <T> T updateTenant(TenantUpdateDto tenant, Class<T> clazz) {
        return requestEngine.putRequest(EndPointsClass.TENANTS, tenant).as(clazz);
    }

    public int deleteTenant(String tenantId) {
        return requestEngine.deleteRequest(EndPointsClass.TENANT, tenantId).statusCode();
    }

    public List<TenantResponse> getTenantsList() {
        return requestEngine.getRequest(EndPointsClass.TENANTS).as(ListTenantResponse.class).getTenants();
    }

    public <T> T getTenant(String tenantId, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TENANT, tenantId).as(clazz);
    }


    public int updateTenantAddressLongitudeAndLatitude(String tenantId, String addressId, GpsRequest gpsRequest) {
        return requestEngine.putRequest(EndPointsClass.ADDRESS, tenantId, addressId, gpsRequest).getStatusCode();
    }

    /*
    colours part
     */
    public List<TenantColourDto> getColoursForTenant(String tenantId) {
        return requestEngine.getRequest(EndPointsClass.COLOURS, tenantId).as(TenantColours.class).getTenantColours();
    }

    //I have to check this ability because this post request has parameters and values for them, however, we don't<>
    // have any body, that it why may be we need some special approach for that </>
    public <T> T addColour(String tenantId, String name, String value, Class<T> clazz) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        paramMap.put("value",value);
        return requestEngine.postRequestWithQueryParameters(EndPointsClass.COLOURS,tenantId, paramMap).as(clazz);
    }

    // same issue here
    public int deleteColour(String tenantId, String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        return requestEngine.deleteRequestWithQueryParameters(EndPointsClass.COLOURS, tenantId, paramMap).getStatusCode();
    }
    /*
    resources part
    we need verify all these methods
    */
    public <T> T getResourceForTenant(String tenantId, String name, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.RESOURCES, tenantId, name, null).as(clazz);
    }
    public InputStream getResourceAsInputStreamForTenant(String tenantId, String name) {
        return requestEngine.getRequest(EndPointsClass.RESOURCES, tenantId, name, null).asInputStream();
    }
    public <T> int addResource(String tenantId, String name, File file, Class<T> clazz) {
        return requestEngine.postRequestWithFile(EndPointsClass.RESOURCES, tenantId, name, file).getStatusCode();
    }
    public int deleteResource(String tenantId, String name) {
        return requestEngine.deleteRequest(EndPointsClass.RESOURCES, tenantId, name).getStatusCode();
    }

        //not working till this issue will not be fixed in Touch project
    public <T> T createNewTenantInTouchSide(TenantRequest tenantRequest, Class<T> clazz) {
        Response response = requestEngine.postRequest(EndPointsClass.TENANTS, tenantRequest);
//        response.then().assertThat().statusCode(201);
        return response.as(clazz);
    }


}
