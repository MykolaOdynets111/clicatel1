package datamanager.model;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "number",
        "activated",
        "waEnabled",
        "integrationsCount",
        "waIntegrationsCount",
        "smsIntegrations",
        "waIntegrations",
        "registeredDate"
})
public class MC2SandboxNumber {

    @JsonProperty("id")
    private String id;
    @JsonProperty("number")
    private String number;
    @JsonProperty("activated")
    private Boolean activated;
    @JsonProperty("waEnabled")
    private Boolean waEnabled;
    @JsonProperty("integrationsCount")
    private Integer integrationsCount;
    @JsonProperty("waIntegrationsCount")
    private Integer waIntegrationsCount;
    @JsonProperty("smsIntegrations")
    private List<Object> smsIntegrations = null;
    @JsonProperty("waIntegrations")
    private List<Object> waIntegrations = null;
    @JsonProperty("registeredDate")
    private String registeredDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("activated")
    public Boolean getActivated() {
        return activated;
    }

    @JsonProperty("activated")
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @JsonProperty("waEnabled")
    public Boolean getWaEnabled() {
        return waEnabled;
    }

    @JsonProperty("waEnabled")
    public void setWaEnabled(Boolean waEnabled) {
        this.waEnabled = waEnabled;
    }

    @JsonProperty("integrationsCount")
    public Integer getIntegrationsCount() {
        return integrationsCount;
    }

    @JsonProperty("integrationsCount")
    public void setIntegrationsCount(Integer integrationsCount) {
        this.integrationsCount = integrationsCount;
    }

    @JsonProperty("waIntegrationsCount")
    public Integer getWaIntegrationsCount() {
        return waIntegrationsCount;
    }

    @JsonProperty("waIntegrationsCount")
    public void setWaIntegrationsCount(Integer waIntegrationsCount) {
        this.waIntegrationsCount = waIntegrationsCount;
    }

    @JsonProperty("smsIntegrations")
    public List<Object> getSmsIntegrations() {
        return smsIntegrations;
    }

    @JsonProperty("smsIntegrations")
    public void setSmsIntegrations(List<Object> smsIntegrations) {
        this.smsIntegrations = smsIntegrations;
    }

    @JsonProperty("waIntegrations")
    public List<Object> getWaIntegrations() {
        return waIntegrations;
    }

    @JsonProperty("waIntegrations")
    public void setWaIntegrations(List<Object> waIntegrations) {
        this.waIntegrations = waIntegrations;
    }

    @JsonProperty("registeredDate")
    public String getRegisteredDate() {
        return registeredDate;
    }

    @JsonProperty("registeredDate")
    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
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
