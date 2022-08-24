package datamanager.jacksonschemas.tenantagentsws;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import datamanager.Agents;
import datamanager.Tenants;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "agentType",
        "email",
        "enable2DaysExpirePopUp",
        "enable30DaysExpirePopUp",
        "fullName",
        "mc2UserId",
        "state"
})
@Generated("jsonschema2pojo")
public class Agent {

    @JsonProperty("agentType")
    private String agentType;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enable2DaysExpirePopUp")
    private Boolean enable2DaysExpirePopUp;
    @JsonProperty("enable30DaysExpirePopUp")
    private Boolean enable30DaysExpirePopUp;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("mc2UserId")
    private String mc2UserId;
    @JsonProperty("state")
    private String state;

    public Agent(String name, String email, String agentType ){
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

    @JsonProperty("agentType")
    public String getAgentType() {
        return agentType;
    }

    @JsonProperty("agentType")
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("enable2DaysExpirePopUp")
    public Boolean getEnable2DaysExpirePopUp() {
        return enable2DaysExpirePopUp;
    }

    @JsonProperty("enable2DaysExpirePopUp")
    public void setEnable2DaysExpirePopUp(Boolean enable2DaysExpirePopUp) {
        this.enable2DaysExpirePopUp = enable2DaysExpirePopUp;
    }

    @JsonProperty("enable30DaysExpirePopUp")
    public Boolean getEnable30DaysExpirePopUp() {
        return enable30DaysExpirePopUp;
    }

    @JsonProperty("enable30DaysExpirePopUp")
    public void setEnable30DaysExpirePopUp(Boolean enable30DaysExpirePopUp) {
        this.enable30DaysExpirePopUp = enable30DaysExpirePopUp;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("mc2UserId")
    public String getMc2UserId() {
        return mc2UserId;
    }

    @JsonProperty("mc2UserId")
    public void setMc2UserId(String mc2UserId) {
        this.mc2UserId = mc2UserId;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

}