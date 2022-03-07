package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "createdDate",
        "modifiedDate",
        "tenantId",
        "name",
        "surname",
        "email",
        "agentJid",
        "maxChats",
        "imageUrl",
        "userId",
        "attributes",
        "departments",
        "activeJid",
        "agentStatus"
})
public class AvailableAgent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("createdDate")
    private Long createdDate;
    @JsonProperty("modifiedDate")
    private Long modifiedDate;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("agentJid")
    private String agentJid;
    @JsonProperty("maxChats")
    private Integer maxChats;
    @JsonProperty("imageUrl")
    private Object imageUrl;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("attributes")
    private AvailableAgentAttributes attributes;
    @JsonProperty("departments")
    private List<Object> departments = null;
    @JsonProperty("activeJid")
    private String activeJid;
    @JsonProperty("agentStatus")
    private String agentStatus;
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

    @JsonProperty("createdDate")
    public Long getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("modifiedDate")
    public Long getModifiedDate() {
        return modifiedDate;
    }

    @JsonProperty("modifiedDate")
    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    @JsonProperty("surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email)
    {
        this.email = email;
    }

    @JsonProperty("agentJid")
    public String getAgentJid() {
        return agentJid;
    }

    @JsonProperty("agentJid")
    public void setAgentJid(String agentJid) {
        this.agentJid = agentJid;
    }

    @JsonProperty("maxChats")
    public Integer getMaxChats() {
        return maxChats;
    }

    @JsonProperty("maxChats")
    public void setMaxChats(Integer maxChats) {
        this.maxChats = maxChats;
    }

    @JsonProperty("imageUrl")
    public Object getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("imageUrl")
    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("attributes")
    public AvailableAgentAttributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(AvailableAgentAttributes attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("departments")
    public List<Object> getDepartments() {
        return departments;
    }

    @JsonProperty("departments")
    public void setDepartments(List<Object> departments) {
        this.departments = departments;
    }

    @JsonProperty("activeJid")
    public String getActiveJid() {
        return activeJid;
    }

    @JsonProperty("activeJid")
    public void setActiveJid(String activeJid) {
        this.activeJid = activeJid;
    }

    @JsonProperty("agentStatus")
    public String getAgentStatus() {
        return agentStatus;
    }

    @JsonProperty("agentStatus")
    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getAgentFullName(){
        return name + " " + surname;
    }
}
