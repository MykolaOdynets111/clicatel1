
package dataprovider.jackson_schemas.user_session_info;

import java.util.HashMap;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sessionId",
    "clientId",
    "tenantName",
    "state",
    "startedDate",
    "endedDate",
    "lastActivity",
    "attributes"
})
public class UserSession {

    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("tenantName")
    private String tenantName;
    @JsonProperty("state")
    private String state;
    @JsonProperty("startedDate")
    private String startedDate;
    @JsonProperty("endedDate")
    private String endedDate;
    @JsonProperty("lastActivity")
    private Object lastActivity;
    @JsonProperty("attributes")
    private Attributes attributes;
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

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("tenantName")
    public String getTenantName() {
        return tenantName;
    }

    @JsonProperty("tenantName")
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("startedDate")
    public String getStartedDate() {
        return startedDate;
    }

    @JsonProperty("startedDate")
    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    @JsonProperty("endedDate")
    public String getEndedDate() {
        return endedDate;
    }

    @JsonProperty("endedDate")
    public void setEndedDate(String endedDate) {
        this.endedDate = endedDate;
    }

    @JsonProperty("lastActivity")
    public Object getLastActivity() {
        return lastActivity;
    }

    @JsonProperty("lastActivity")
    public void setLastActivity(Object lastActivity) {
        this.lastActivity = lastActivity;
    }

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
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
