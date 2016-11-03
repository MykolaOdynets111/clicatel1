package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.agent.AgentMaxChatsResponse;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AgentActions {

    com.touch.engines.RequestEngine requestEngine;

    public AgentActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public <T> T getListOfAgents(String id, String jabberId, Class<T> clazz) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id",id);
        parameters.put("jabberId",jabberId);
        return requestEngine.getRequest(EndPointsClass.AGENTS+EndPointsClass.generateQueryPath(parameters)).as(clazz);
    }
    public <T> T getCredentials(String token,Class<T> clazz) {
        return requestEngine.getRequest(EndPointsClass.AGENTS_CREDENTIALS,new Header("Authorization", token)).as(clazz);
    }

    public AgentMaxChatsResponse getAgentMaxChats(String agentId, String departmentId) {
        return requestEngine.getRequest(EndPointsClass.AGENTS_MAX_CHATS+"?agentId="+agentId+"&departmentId="+departmentId).as(AgentMaxChatsResponse.class);
    }

    public Response getAgentImage(String agentId) {
        return requestEngine.getRequest(EndPointsClass.AGENT_IMAGE,agentId);
    }
    public Response updateAgentImage(String agentId, File file){
        return  requestEngine.putFile(EndPointsClass.AGENT_IMAGE, agentId, file);
    }
    public Response deleteAgent(String agentId){
        return  requestEngine.deleteRequest(EndPointsClass.AGENT, agentId);
    }
    public Response deleteAgentImage(String agentId){
        return  requestEngine.deleteRequest(EndPointsClass.AGENT_IMAGE, agentId);
    }
}
