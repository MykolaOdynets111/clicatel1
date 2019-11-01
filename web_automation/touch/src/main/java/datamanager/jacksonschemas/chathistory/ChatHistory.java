
package datamanager.jacksonschemas.chathistory;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "previousChatId",
    "tenantId",
    "chatUserId",
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
    @JsonProperty("chatUserId")
    private String chatUserId;
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

    public ChatHistory(String previousChatId, String tenantId, String userId, String agentId, String channelId, String chatStarted, String chatEnded) {
        this.previousChatId = previousChatId;
        this.tenantId = tenantId;
        this.chatUserId = userId;
        this.agentId = agentId;
        this.channelId = channelId;
        this.chatStarted = chatStarted;
        this.chatEnded = chatEnded;
    }

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

    @JsonProperty("chatUserId")
    public String getChatUserId() {
        return chatUserId;
    }

    @JsonProperty("chatUserId")
    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
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
    public ChatHistory setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    @Override
    public String toString() {
        return "ChatHistory{" +
                "previousChatId='" + previousChatId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", chatUserId='" + chatUserId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", chatStarted='" + chatStarted + '\'' +
                ", chatEnded='" + chatEnded + '\'' +
                ", messages=" + messages +
                '}';
    }
}
