package datamanager.jacksonschemas.dotcontrol;

import com.github.javafaker.Faker;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(value = { "fullName"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "firstName",
    "lastName",
    "email",
    "phone"
})
public class InitContext {

    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    Faker faker = new Faker();

    public InitContext(){
        this.firstName = faker.name().firstName();
        this.lastName = "DotC_" + faker.name().lastName();
        this.email = faker.lorem().word() + "@mail.com";
        this.phone = faker.phoneNumber().cellPhone();
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public InitContext setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public InitContext setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getFullName(){
        fullName = this.firstName + " " + this.lastName;
        return fullName;
    }

}
