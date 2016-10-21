package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.tenant.*;
import io.restassured.response.Response;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class TenantActions {

    com.touch.engines.RequestEngine requestEngine;

    public TenantActions(com.touch.engines.RequestEngine requestEngine) {
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
    public List<TenantResponse> getNearestTenantsList(String lat, String lon, String radius) {
        return requestEngine.getRequest(EndPointsClass.TENANTS_NEW+"?lat="+lat+"&lon="+lon+"&radius="+radius).as(ListTenantResponse.class).getTenants();
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
    public InputStream getCommonFlowAsInputStream(String name) {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOW, name).asInputStream();
    }
    public <T> T getCommonFlow(String name, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOW, name).as(clazz);
    }
    public ListFlowResponse getAllCommonFlows() {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOWS).as(ListFlowResponse.class);
    }
    public int addCommonFlow(File file) {
        return requestEngine.postRequestWithFile(EndPointsClass.COMMON_FLOWS, null, null, file).getStatusCode();
    }
    public int deleteCommonFlow(String name) {
        return requestEngine.deleteRequest(EndPointsClass.DELETE_COMMON_FLOW, name).getStatusCode();
    }
    public InputStream getTanentFlowAsInputStream(String tenantId, String name) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOW, tenantId, name, null).asInputStream();
    }
    public <T> T getTanentFlow(String tenantId, String name, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOW, tenantId, name, null).as(clazz);
    }
    public ListFlowResponse getAllTanentFlows(String tenantId) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOWS, tenantId).as(ListFlowResponse.class);
    }
    public int addTanentFlow(String tenantId, File file) {
        return requestEngine.postRequestWithFile(EndPointsClass.TENANT_FLOWS, tenantId, null, file).getStatusCode();
    }
    public int deleteTanentFlow(String tenantId, String name) {
        return requestEngine.deleteRequest(EndPointsClass.DELETE_TENANT_FLOW, tenantId, name).getStatusCode();
    }
}
