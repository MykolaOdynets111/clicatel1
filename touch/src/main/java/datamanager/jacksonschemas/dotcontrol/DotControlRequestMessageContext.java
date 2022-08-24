package datamanager.jacksonschemas.dotcontrol;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "lastName",
        "phone",
        "ticket"
})
public class DotControlRequestMessageContext implements DotControlRequestContextInterface{

    @JsonProperty("email")
    private String email;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("ticket")
    private String ticket;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public DotControlRequestMessageContext(){
        this.email = "automation@aqa.com";
        this.lastName = "AQA";
        this.phone = "22354564545";
        this.ticket = "76767687";
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public DotControlRequestMessageContext setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("ticket")
    public String getTicket() {
        return ticket;
    }

    @JsonProperty("ticket")
    public void setTicket(String ticket) {
        this.ticket = ticket;
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
        return "\"email\": \"" + email + "\", \"lastName\": \"" + lastName + "\", \"phone\": " +
                "\"" + phone + "\", \"ticket\": \"" + ticket + "\"";
    }
}
