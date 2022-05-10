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
        "firstName",
        "integrationId",
        "lastName",
        "location",
        "phoneNumber",
        "profileImage",
        "username",
        "verified",
        "waPhone"
})
public class WhatsApp {

    @JsonProperty("externalUserId")
    private String externalUserId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("integrationId")
    private String integrationId;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("profileImage")
    private String profileImage;
    @JsonProperty("username")
    private String username;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonProperty("waPhone")
    private String waPhone;
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

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("integrationId")
    public String getIntegrationId() {
        return integrationId;
    }

    @JsonProperty("integrationId")
    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("profileImage")
    public String getProfileImage() {
        return profileImage;
    }

    @JsonProperty("profileImage")
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("verified")
    public Boolean getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @JsonProperty("waPhone")
    public String getWaPhone() {
        return waPhone;
    }

    @JsonProperty("waPhone")
    public void setWaPhone(String waPhone) {
        this.waPhone = waPhone;
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
        return "WhatsApp{" +
                "externalUserId='" + externalUserId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", integrationId='" + integrationId + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", username='" + username + '\'' +
                ", verified=" + verified +
                ", waPhone='" + waPhone + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
