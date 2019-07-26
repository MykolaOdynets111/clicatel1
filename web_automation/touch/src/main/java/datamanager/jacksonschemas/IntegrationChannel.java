package datamanager.jacksonschemas;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class IntegrationChannel {

    @JsonProperty("id")
    private String id;
    @JsonProperty("channelType")
    private String channelType;
    @JsonProperty("integrationType")
    private String integrationType;
    @JsonProperty("channelAdapter")
    private String channelAdapter;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("createdDate")
    private Long createdDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("integrationType")
    public String getIntegrationType() {
        return integrationType;
    }

    @JsonProperty("integrationType")
    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    @JsonProperty("channelType")
    public String getChannelType() {
        return channelType;
    }

    @JsonProperty("channelType")
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @JsonProperty("channelAdapter")
    public String getChannelAdapter() {
        return channelAdapter;
    }

    @JsonProperty("channelAdapter")
    public void setChannelAdapter(String channelAdapter) {
        this.channelAdapter = channelAdapter;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("createdDate")
    public Long getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
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
