package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.department.DepartmentDto;
import com.touch.models.touch.department.DepartmentResponse;
import com.touch.models.touch.department.ListDepartmentResponse;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class DepartmentActions {

    com.touch.engines.RequestEngine requestEngine;

    public DepartmentActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public ListDepartmentResponse getListOfDepartments() {
        return requestEngine.getRequest(EndPointsClass.DEPARTMENTS).as(ListDepartmentResponse.class);
    }

    public DepartmentResponse addDepartment(DepartmentDto department) {
        return requestEngine.postRequest(EndPointsClass.DEPARTMENTS, department).as(DepartmentResponse.class);
    }
    public DepartmentResponse getDepartment(String departmentId) {
        return requestEngine.getRequest(EndPointsClass.DEPARTMENT, departmentId).as(DepartmentResponse.class);
    }
//    here is mistake in creating path - TODO rewrite this method
    public int updateDepartment(String id, String name, String description, String sessionId) {
        int count = 0;
        String path = EndPointsClass.DEPARTMENT;
        if (!(name == null) || !(description == null) || !(sessionId == null))
            path += "?";
        if (!(name == null)) {
            path += name;
            count++;
        }
        if (!(description == null)) {
            if (count > 0)
                path += "&";
            path += description;
            count++;
        }
        if (!(sessionId == null)) {
            if (count > 0)
                path += "&";
            path += sessionId;
        }
        return requestEngine.putRequest(path, id).getStatusCode();
    }

    public int deleteDepartment(String departmentId) {
        return requestEngine.deleteRequest(EndPointsClass.DEPARTMENT, departmentId).getStatusCode();
    }

    public int putAgentInDepartment(String departmentId, String agentId) {
        return requestEngine.putRequest(EndPointsClass.DEPARTMENTS_AGENTS+"?departmentIds="+departmentId+"&agentIds="+agentId, null).getStatusCode();
    }
    public int deleteAgentInDepartment(String departmentId, String agentId) {
        return requestEngine.deleteRequest(EndPointsClass.DEPARTMENTS_AGENTS+"?departmentIds="+departmentId+"&agentIds="+agentId).getStatusCode();
    }
}
