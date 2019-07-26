
package datamanager.jacksonschemas.usersessioninfo;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sourceChannel",
    "chatRoomId",
    "tenantId",
    "userMeta"
})
public class Attributes {

    @JsonProperty("sourceChannel")
    private String sourceChannel;
    @JsonProperty("chatRoomId")
    private String chatRoomId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("userMeta")
    private UserMeta userMeta;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sourceChannel")
    public String getSourceChannel() {
        return sourceChannel;
    }

    @JsonProperty("sourceChannel")
    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    @JsonProperty("chatRoomId")
    public String getChatRoomId() {
        return chatRoomId;
    }

    @JsonProperty("chatRoomId")
    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("userMeta")
    public UserMeta getUserMeta() {
        return userMeta;
    }

    @JsonProperty("userMeta")
    public void setUserMeta(UserMeta userMeta) {
        this.userMeta = userMeta;
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
