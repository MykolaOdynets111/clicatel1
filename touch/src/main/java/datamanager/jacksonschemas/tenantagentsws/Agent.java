package datamanager.jacksonschemas.tenantagentsws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import datamanager.Agents;
import datamanager.Tenants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id", "mc2UserId", "tenantId", "agentType", "hasImage", "fullName", "email", "enable30DaysExpirePopUp",
        "enable2DaysExpirePopUp", "state", "departmentFilteringSelection", "sortingSelection"
})
public class Agent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("mc2UserId")
    private String mc2UserId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("agentType")
    private String agentType;
    @JsonProperty("hasImage")
    private boolean hasImage;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enable30DaysExpirePopUp")
    private boolean enable30DaysExpirePopUp;
    @JsonProperty("enable2DaysExpirePopUp")
    private boolean enable2DaysExpirePopUp;
    @JsonProperty("state")
    private String state;
    @JsonProperty("departmentFilteringSelection")
    private String departmentFilteringSelection;
    @JsonProperty("sortingSelection")
    private String sortingSelection;

    public Agent(String name, String email, String agentType) {
        Faker fake = new Faker();
        Agents agent = Agents.getMainAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName());
        setAgentType(agentType);
        setEmail(email);
        setEnable2DaysExpirePopUp(true);
        setEnable30DaysExpirePopUp(true);
        setFullName(name);
        setMc2UserId(fake.numerify("aqaId############"));
        setState("ACTIVE");
    }
}