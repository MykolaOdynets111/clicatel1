package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.tenant.*;
import io.restassured.http.Header;
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

    public <T> T updateTenant(String tenantId, TenantUpdateDtoV5 tenant, String token, Class<T> clazz) {
        return requestEngine.putRequest(EndPointsClass.TENANT, tenantId, null, tenant,new Header("Authorization", token)).as(clazz);
    }

    public int deleteTenant(String tenantId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.TENANT, tenantId, new Header("Authorization", token)).statusCode();
    }

    public List<TenantResponseV5> getTenantsList(String token) {
        return requestEngine.getRequest(EndPointsClass.TENANTS, new Header("Authorization", token)).as(ListTenantResponse.class).getTenants();
    }
    public Response getTenantsList(String accountId, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANTS+"?accountId="+accountId, new Header("Authorization", token));
    }
    public List<TenantResponseV5> getNearestTenantsList(String lat, String lon, String radius, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANTS+"?lat="+lat+"&lon="+lon+"&radius="+radius,new Header("Authorization", token)).as(ListTenantResponse.class).getTenants();
    }
    public <T> T getTenant(String tenantId,String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TENANT, tenantId,new Header("Authorization", token)).as(clazz);
    }
    public Response getTenant(String tenantId,String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT, tenantId,new Header("Authorization", token));
    }

    public Response getTenantTBot(String tenantId,String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_TBOT, tenantId,new Header("Authorization", token));
    }
    public Response getTenantConfig(String tenantId,String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_CONFIG, tenantId,new Header("Authorization", token));
    }
    public <T> T getBusinessHoursFromAddress(String tenantId, String addressId, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.BUSINESS_HOURS_FOR_ADDRESS, tenantId, addressId,null, new Header("Authorization", token)).as(clazz);
    }
    public Response getBusinessHoursFromTenant(String tenantId, String token) {
        return requestEngine.getRequest(EndPointsClass.BUSINESS_HOURS_FOR_TENANT, tenantId, new Header("Authorization", token));
    }
    public <T> T getFAQs(String tenantId, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FAQS, tenantId, new Header("Authorization", token)).as(clazz);
    }
    public Response getTAGs(String tenantId, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_TAGS, tenantId, new Header("Authorization", token));
    }
    public int updateTenantAddressLongitudeAndLatitude(String tenantId, String addressId, GpsRequest gpsRequest, String token) {
        return requestEngine.putRequest(EndPointsClass.ADDRESS, tenantId, addressId, gpsRequest,new Header("Authorization", token)).getStatusCode();
    }
    public Response updateBusinessHoursForAddress(String tenantId, String addressId, String hoursId, AddressBusinessHourRequest bussinesHours, String token) {
        return requestEngine.putRequest(EndPointsClass.BUSINESS_HOURS_ID_FOR_ADDRESS, tenantId, addressId, hoursId, bussinesHours,new Header("Authorization", token));
    }
    public Response updateBusinessHoursForTenant(String tenantId, String hoursId, BusinessHourRequest bussinesHours, String token) {
        return requestEngine.putRequest(EndPointsClass.BUSINESS_HOURS_ID_FOR_TENANT, tenantId, hoursId, bussinesHours,new Header("Authorization", token));
    }
    public Response updateFAQ(String tenantId, String faqId, TenantFaqRequest faqBody, String token) {
        return requestEngine.putRequest(EndPointsClass.TENANT_FAQ, tenantId, faqId, faqBody,new Header("Authorization", token));
    }
    public Response updateConfig(String tenantId, TenantConfig config, String token) {
        return requestEngine.putRequest(EndPointsClass.TENANT_CONFIG, tenantId, null, config,new Header("Authorization", token));
    }
    /*
    colours part
     */
    public List<TenantProperties> getPropertiesForTenant(String tenantId, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_PROPERTIES, tenantId,new Header("Authorization", token)).as(ListTenantProperties.class).getTenantProperties();
    }

    //I have to check this ability because this post request has parameters and values for them, however, we don't<>
    // have any body, that it why may be we need some special approach for that </>
    public <T> T addProperty(String tenantId, String name, String value, String token, Class<T> clazz) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        paramMap.put("value",value);
        return requestEngine.postRequestWithQueryParameters(EndPointsClass.TENANT_PROPERTIES,tenantId, paramMap,new Header("Authorization", token)).as(clazz);
    }
    public Response addBusinessHoursForAddress(String tenantId, String addressId, BusinessHourRequest bussinesHours, String token) {
        return requestEngine.postRequest(EndPointsClass.BUSINESS_HOURS_FOR_ADDRESS, tenantId, addressId, bussinesHours,new Header("Authorization", token),new Header("Accept", "application/json"));
    }
    public Response addBusinessHoursForTenant(String tenantId, BusinessHourRequest bussinesHours, String token) {
        return requestEngine.postRequest(EndPointsClass.BUSINESS_HOURS_FOR_TENANT, tenantId, null, bussinesHours,new Header("Authorization", token));
    }
    public Response addFAQs(String tenantId, TenantFaqRequest faqs, String token) {
        return requestEngine.postRequest(EndPointsClass.TENANT_FAQS, tenantId, null, faqs,new Header("Authorization", token));
    }
    public Response addTag(String tenantId, TenantTagRequest tag, String token) {
        return requestEngine.postRequest(EndPointsClass.TENANT_TAGS, tenantId, null, tag,new Header("Authorization", token));
    }
    // same issue here
    public int deleteColour(String tenantId, String name, String token) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        return requestEngine.deleteRequestWithQueryParameters(EndPointsClass.TENANT_PROPERTIES, tenantId, paramMap, new Header("Authorization", token)).getStatusCode();
    }
    /*
    resources part
    we need verify all these methods
    */
    public <T> T getResourceForTenant(String tenantId, String name, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.RESOURCES, tenantId, name, null, new Header("Authorization", token)).as(clazz);
    }
    public InputStream getResourceAsInputStreamForTenant(String tenantId, String name, String token) {
        return requestEngine.getRequest(EndPointsClass.RESOURCES, tenantId, name, null, new Header("Authorization", token)).asInputStream();
    }
    public <T> int addResource(String tenantId, String name, File file, String token, Class<T> clazz) {
        return requestEngine.postRequestWithFile(EndPointsClass.RESOURCES, tenantId, name, file, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteResource(String tenantId, String name, String token) {
        return requestEngine.deleteRequest(EndPointsClass.RESOURCES, tenantId, name, new Header("Authorization", token)).getStatusCode();
    }

        //not working till this issue will not be fixed in Touch project
    public <T> T createNewTenantInTouchSide(TenantRequest tenantRequest, String token, Class<T> clazz) {
        Response response = requestEngine.postRequest(EndPointsClass.TENANTS,null,null, tenantRequest,new Header("Authorization", token));
//        response.then().assertThat().statusCode(201);
        return response.as(clazz);
    }
    public InputStream getCommonFlowAsInputStream(String name, String token) {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOW, name, new Header("Authorization", token)).asInputStream();
    }
    public <T> T getCommonFlow(String name, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOW, name, new Header("Authorization", token)).as(clazz);
    }
    public ListFlowResponse getAllCommonFlows(String token) {
        return requestEngine.getRequest(EndPointsClass.COMMON_FLOWS,new Header("Authorization", token)).as(ListFlowResponse.class);
    }
    public int addCommonFlow(File file, String token) {
        return requestEngine.postRequestWithFile(EndPointsClass.COMMON_FLOWS, null, null, file, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteCommonFlow(String name, String token) {
        return requestEngine.deleteRequest(EndPointsClass.DELETE_COMMON_FLOW, name, new Header("Authorization", token)).getStatusCode();
    }
    public InputStream getTenantFlowAsInputStream(String tenantId, String name, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOW, tenantId, name, null, new Header("Authorization", token)).asInputStream();
    }
    public <T> T getTenantFlow(String tenantId, String name, String token, Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOW, tenantId, name, null, new Header("Authorization", token)).as(clazz);
    }
    public ListFlowResponse getAllTenantFlows(String tenantId, String token) {
        return requestEngine.getRequest(EndPointsClass.TENANT_FLOWS, tenantId, new Header("Authorization", token)).as(ListFlowResponse.class);
    }
    public int addTenantFlow(String tenantId, File file, String token) {
        return requestEngine.postRequestWithFile(EndPointsClass.TENANT_FLOWS, tenantId, null, file, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteTenantFlow(String tenantId, String name, String token) {
        return requestEngine.deleteRequest(EndPointsClass.DELETE_TENANT_FLOW, tenantId, name, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteBusinessHoursForAddress(String tenantId, String addressId, String hoursId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.BUSINESS_HOURS_ID_FOR_ADDRESS, tenantId, addressId, hoursId, new Header("Authorization", token)).getStatusCode();
    }
    public int deleteBussinesHoursForTenant(String tenantId, String hoursId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.BUSINESS_HOURS_ID_FOR_TENANT, tenantId, hoursId, new Header("Authorization", token)).getStatusCode();
    }
    public Response deleteFAQs(String tenantId,String faqId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.TENANT_FAQS+"?faq-id="+faqId, tenantId, new Header("Authorization", token));
    }
    public Response deleteTAGs(String tenantId, String tag, String token) {
        return requestEngine.deleteRequest(EndPointsClass.TENANT_TAGS+"?tag="+tag, tenantId, new Header("Authorization", token));
    }
    public Response deleteTenantConfig(String tenantId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.TENANT_CONFIG, tenantId, new Header("Authorization", token));
    }
}
