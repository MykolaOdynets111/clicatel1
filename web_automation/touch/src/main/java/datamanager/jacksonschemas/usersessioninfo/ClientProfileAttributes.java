package datamanager.jacksonschemas.usersessioninfo;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "phone",
    "email",
    "firstName",
    "lastName",
    "lastVisit",
    "otpSent"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientProfileAttributes {

    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("lastVisit")
    private String lastVisit;
    @JsonProperty("otpSent")
    private String otpSent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("lastVisit")
    public String getLastVisit() {
        return lastVisit;
    }

    @JsonProperty("lastVisit")
    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    @JsonProperty("otpSent")
    public String getOtpSent() {
        return otpSent;
    }

    @JsonProperty("otpSent")
    public void setOtpSent(String otpSent) {
        this.otpSent = otpSent;
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
