package datamanager.jacksonschemas.chatusers;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "externalUserId",
        "integrationId"
})
public class Webchat {

    @JsonProperty("externalUserId")
    private String externalUserId;
    @JsonProperty("integrationId")
    private String integrationId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("externalUserId")
    public String getExternalUserId() {
        return externalUserId;
    }

    @JsonProperty("externalUserId")
    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    @JsonProperty("integrationId")
    public String getIntegrationId() {
        return integrationId;
    }

    @JsonProperty("integrationId")
    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Webchat{" +
                "externalUserId='" + externalUserId + '\'' +
                ", integrationId='" + integrationId + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
