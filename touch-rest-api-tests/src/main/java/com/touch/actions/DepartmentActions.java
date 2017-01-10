package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.department.DepartmentDto;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class DepartmentActions {

    com.touch.engines.RequestEngine requestEngine;

    public DepartmentActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getListOfDepartments(String token) {
        return requestEngine.getRequest(EndPointsClass.DEPARTMENTS, new Header("Authorization", token));
    }

    public Response addDepartment(Object department, String token) {
        return requestEngine.postRequest(EndPointsClass.DEPARTMENTS, null,null,department, new Header("Authorization", token));
    }
    public Response getDepartment(String departmentId, String token) {
        return requestEngine.getRequest(EndPointsClass.DEPARTMENT, departmentId, new Header("Authorization", token));
    }

    public Response updateDepartment(String id, String name, String description, String sessionCapacity, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(!name.isEmpty())
        parameters.put("name", name);
        if(!description.isEmpty())
        parameters.put("description", description);
        if(!sessionCapacity.isEmpty())
        parameters.put("sessionCapacity", sessionCapacity);
        return requestEngine.putRequest(EndPointsClass.DEPARTMENT + EndPointsClass.generateQueryPath(parameters), id, new Header("Authorization", token));
    }

    public Response deleteDepartment(String departmentId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.DEPARTMENT, departmentId, new Header("Authorization", token));
    }

    public Response putAgentInDepartment(String departmentId, String agentId, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(!departmentId.isEmpty())
        parameters.put("departmentIds", departmentId);
        if(!agentId.isEmpty())
        parameters.put("agentIds", agentId);
        return requestEngine.putRequest(EndPointsClass.DEPARTMENTS_AGENTS+ EndPointsClass.generateQueryPath(parameters), null, new Header("Authorization", token));
    }
    public Response deleteAgentInDepartment(String departmentId, String agentId, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("departmentIds", departmentId);
        parameters.put("agentIds", agentId);
        return requestEngine.deleteRequest(EndPointsClass.DEPARTMENTS_AGENTS+ EndPointsClass.generateQueryPath(parameters), null, new Header("Authorization", token));
    }
}
