
package datamanager.jacksonschemas.chathistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "previousChatId",
    "tenantId",
    "integration",
    "userId",
    "agentId",
    "channelId",
    "chatStarted",
    "chatEnded",
    "messages"
})
public class ChatHistory {

    @JsonProperty("previousChatId")
    private String previousChatId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("integration")
    private String integration;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("agentId")
    private String agentId;
    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("chatStarted")
    private String chatStarted;
    @JsonProperty("chatEnded")
    private String chatEnded;
    @JsonProperty("messages")
    private List<Message> messages = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("previousChatId")
    public String getPreviousChatId() {
        return previousChatId;
    }

    @JsonProperty("previousChatId")
    public void setPreviousChatId(String previousChatId) {
        this.previousChatId = previousChatId;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("integration")
    public String getIntegration() {
        return integration;
    }

    @JsonProperty("integration")
    public void setIntegration(String integration) {
        this.integration = integration;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("agentId")
    public String getAgentId() {
        return agentId;
    }

    @JsonProperty("agentId")
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @JsonProperty("channelId")
    public String getChannelId() {
        return channelId;
    }

    @JsonProperty("channelId")
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @JsonProperty("chatStarted")
    public String getChatStarted() {
        return chatStarted;
    }

    @JsonProperty("chatStarted")
    public void setChatStarted(String chatStarted) {
        this.chatStarted = chatStarted;
    }

    @JsonProperty("chatEnded")
    public String getChatEnded() {
        return chatEnded;
    }

    @JsonProperty("chatEnded")
    public void setChatEnded(String chatEnded) {
        this.chatEnded = chatEnded;
    }

    @JsonProperty("messages")
    public List<Message> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<Message> messages) {
        this.messages = messages;
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
