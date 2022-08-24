package datamanager.jacksonschemas;


import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "clientId",
        "type",
        "integrationId",
        "createdDate",
        "modifiedDate",
        "attributes",
        "id"
})
public class ClientProfileOld {

    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("integrationId")
    private Object integrationId;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("modifiedDate")
    private String modifiedDate;
    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("id")
    private String id;

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("integrationId")
    public Object getIntegrationId() {
        return integrationId;
    }

    @JsonProperty("integrationId")
    public void setIntegrationId(Object integrationId) {
        this.integrationId = integrationId;
    }

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("modifiedDate")
    public String getModifiedDate() {
        return modifiedDate;
    }

    @JsonProperty("modifiedDate")
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientId", clientId).append("type", type).append("integrationId", integrationId).append("createdDate", createdDate).append("modifiedDate", modifiedDate).append("attributes", attributes).append("id", id).toString();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "lastName",
            "verified",
            "phone",
            "firstName",
            "email",
            "phoneVerified",
            "otpSent",
            "client_blocked",
            "location"
    })
    public class Attributes {

        @JsonProperty("lastName")
        private String lastName;
        @JsonProperty("verified")
        private String verified;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("firstName")
        private String firstName;
        @JsonProperty("email")
        private String email;
        @JsonProperty("phoneVerified")
        private String phoneVerified;
        @JsonProperty("otpSent")
        private String otpSent;
        @JsonProperty("client_blocked")
        private String clientBlocked;
        @JsonProperty("location")
        private String location;

        @JsonProperty("lastName")
        public String getLastName() {
            return lastName;
        }

        @JsonProperty("lastName")
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @JsonProperty("verified")
        public String getVerified() {
            return verified;
        }

        @JsonProperty("verified")
        public void setVerified(String verified) {
            this.verified = verified;
        }

        @JsonProperty("phone")
        public String getPhone() {
            return phone;
        }

        @JsonProperty("phone")
        public void setPhone(String phone) {
            this.phone = phone;
        }

        @JsonProperty("firstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("firstName")
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("email")
        public String getEmail() {
            return email;
        }

        @JsonProperty("email")
        public void setEmail(String email) {
            this.email = email;
        }

        @JsonProperty("phoneVerified")
        public String getPhoneVerified() {
            return phoneVerified;
        }

        @JsonProperty("phoneVerified")
        public void setPhoneVerified(String phoneVerified) {
            this.phoneVerified = phoneVerified;
        }

        @JsonProperty("otpSent")
        public String getOtpSent() {
            return otpSent;
        }

        @JsonProperty("otpSent")
        public void setOtpSent(String otpSent) {
            this.otpSent = otpSent;
        }

        @JsonProperty("client_blocked")
        public String getClientBlocked() {
            return clientBlocked;
        }

        @JsonProperty("client_blocked")
        public void setClientBlocked(String clientBlocked) {
            this.clientBlocked = clientBlocked;
        }

        @JsonProperty("location")
        public String getLocation() {
            return location;
        }

        @JsonProperty("location")
        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("lastName", lastName).append("verified", verified).append("phone", phone).append("firstName", firstName).append("email", email).append("phoneVerified", phoneVerified).append("otpSent", otpSent).append("clientBlocked", clientBlocked).append("location", location).toString();
        }

    }
}