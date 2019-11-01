
package datamanager.jacksonschemas.chathistory;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "id",
    "chatId",
    "tenantId",
    "sessionId",
    "source",
    "exclude",
    "dateTime",
    "message"
})
public class Message {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("chatId")
    private String chatId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("exclude")
    private List<Object> exclude = null;
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("message")
    private Message_ message;

    public Message(String type, String id, String chatId, String tenantId, String sessionId, String source, String dateTime){
        this.type = type;
        this.id = id;
        this.chatId = chatId;
        this.tenantId = tenantId;
        this.sessionId = sessionId;
        this.source = source;
        this.dateTime = dateTime;
        this.exclude = new ArrayList<>();
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("chatId")
    public String getChatId() {
        return chatId;
    }

    @JsonProperty("chatId")
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("exclude")
    public List<Object> getExclude() {
        return exclude;
    }

    @JsonProperty("exclude")
    public void setExclude(List<Object> exclude) {
        this.exclude = exclude;
    }

    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("dateTime")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty("message")
    public Message_ getMessage() {
        return message;
    }

    @JsonProperty("message")
    public Message setMessage(Message_ message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", chatId='" + chatId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", source='" + source + '\'' +
                ", exclude=" + exclude +
                ", dateTime='" + dateTime + '\'' +
                ", message=" + message +
                '}';
    }
}
