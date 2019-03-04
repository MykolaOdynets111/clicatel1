package datamanager.jacksonschemas;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

public class ChatHistoryItem {
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("messageTime")
    private Long messageTime;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("messageText")
    private String messageText;
    @JsonProperty("displayMessage")
    private String displayMessage;
    @JsonProperty("messageType")
    private String messageType;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("roomJid")
    private Object roomJid;
    @JsonProperty("deliveryStatuses")
    private String deliveryStatuses;
    @JsonProperty("agent")
    private Object agent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("messageTime")
    public Long getMessageTime() {
        return messageTime;
    }

    @JsonProperty("messageTime")
    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    @JsonProperty("messageId")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("messageId")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("messageText")
    public String getMessageText() {
        return messageText;
    }

    @JsonProperty("messageText")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @JsonProperty("displayMessage")
    public String getDisplayMessage() {
        return displayMessage;
    }

    @JsonProperty("displayMessage")
    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @JsonProperty("messageType")
    public String getMessageType() {
        return messageType;
    }

    @JsonProperty("messageType")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("roomJid")
    public Object getRoomJid() {
        return roomJid;
    }

    @JsonProperty("roomJid")
    public void setRoomJid(Object roomJid) {
        this.roomJid = roomJid;
    }

    @JsonProperty("deliveryStatuses")
    public String getDeliveryStatuses() {
        return deliveryStatuses;
    }

    @JsonProperty("deliveryStatuses")
    public void setDeliveryStatuses(String deliveryStatuses) {
        this.deliveryStatuses = deliveryStatuses;
    }

    @JsonProperty("agent")
    public Object getAgent() {
        return agent;
    }

    @JsonProperty("agent")
    public void setAgent(Object agent) {
        this.agent = agent;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
