
package datamanager.jacksonschemas.dotcontrol;

import com.github.javafaker.Faker;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apiToken",
    "clientId",
    "initContext",
    "departmentId",
    "history",
    "referenceId",
    "tenantMode"
})
public class DotControlInitRequest {

    @JsonProperty("apiToken")
    private String apiToken;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("context")
    private InitContext initContext;
    @JsonProperty("departmentId")
    private String departmentId;
    @JsonProperty("history")
    private List<History> history;
    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("tenantMode")
    private String tenantMode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    Faker faker = new Faker();


    public DotControlInitRequest(String apiToken, String clientId, String messageId){
        this.apiToken = apiToken;
//        this.clientId = "init_client_" + faker.number().randomNumber(7, false);
        this.clientId = clientId;
//        this.initContext = new InitContext();
        this.departmentId = null;
        this.history =  Arrays.asList(new History(messageId));
        this.referenceId = "string";  //"" + faker.number().randomNumber(6, false);
        this.tenantMode = "BOT";
    }

    @JsonProperty("apiToken")
    public String getApiToken() {
        return apiToken;
    }

    @JsonProperty("apiToken")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("context")
    public InitContext getInitContext() {
        return initContext;
    }

    @JsonProperty("context")
    public void setInitContext(InitContext initContext) {
        this.initContext = initContext;
    }

    @JsonProperty("departmentId")
    public String getDepartmentId() {
        return departmentId;
    }

    @JsonProperty("departmentId")
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @JsonProperty("history")
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
    }

    @JsonProperty("referenceId")
    public String getReferenceId() {
        return referenceId;
    }

    @JsonProperty("referenceId")
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @JsonProperty("tenantMode")
    public String getTenantMode() {
        return tenantMode;
    }

    @JsonProperty("tenantMode")
    public void setTenantMode(String tenantMode) {
        this.tenantMode = tenantMode;
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
