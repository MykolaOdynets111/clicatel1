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
        "abc",
        "clientBlocked",
        "cmnextToken",
        "company",
        "createdDate",
        "ctxSummary",
        "email",
        "facebook",
        "firstName",
        "id",
        "language",
        "lastName",
        "locale",
        "location",
        "mc2Token",
        "modifiedDate",
        "otpSent",
        "phone",
        "phoneVerified",
        "sms",
        "tenantId",
        "timeZone",
        "twitter",
        "useragent",
        "verified",
        "waPhone",
        "webchat",
        "whatsApp"
})
public class UserInfo {

    @JsonProperty("abc")
    private Abc abc;
    @JsonProperty("clientBlocked")
    private Boolean clientBlocked;
    @JsonProperty("cmnextToken")
    private String cmnextToken;
    @JsonProperty("company")
    private String company;
    @JsonProperty("createdDate")
    private Integer createdDate;
    @JsonProperty("ctxSummary")
    private String ctxSummary;
    @JsonProperty("email")
    private String email;
    @JsonProperty("facebook")
    private Facebook facebook;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("id")
    private String id;
    @JsonProperty("language")
    private String language;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("location")
    private String location;
    @JsonProperty("mc2Token")
    private String mc2Token;
    @JsonProperty("modifiedDate")
    private Integer modifiedDate;
    @JsonProperty("otpSent")
    private String otpSent;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("phoneVerified")
    private Boolean phoneVerified;
    @JsonProperty("sms")
    private Sms sms;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("timeZone")
    private String timeZone;
    @JsonProperty("twitter")
    private Twitter twitter;
    @JsonProperty("useragent")
    private String useragent;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonProperty("waPhone")
    private String waPhone;
    @JsonProperty("webchat")
    private Webchat webchat;
    @JsonProperty("whatsApp")
    private WhatsApp whatsApp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("abc")
    public Abc getAbc() {
        return abc;
    }

    @JsonProperty("abc")
    public void setAbc(Abc abc) {
        this.abc = abc;
    }

    @JsonProperty("clientBlocked")
    public Boolean getClientBlocked() {
        return clientBlocked;
    }

    @JsonProperty("clientBlocked")
    public void setClientBlocked(Boolean clientBlocked) {
        this.clientBlocked = clientBlocked;
    }

    @JsonProperty("cmnextToken")
    public String getCmnextToken() {
        return cmnextToken;
    }

    @JsonProperty("cmnextToken")
    public void setCmnextToken(String cmnextToken) {
        this.cmnextToken = cmnextToken;
    }

    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(String company) {
        this.company = company;
    }

    @JsonProperty("createdDate")
    public Integer getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(Integer createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("ctxSummary")
    public String getCtxSummary() {
        return ctxSummary;
    }

    @JsonProperty("ctxSummary")
    public void setCtxSummary(String ctxSummary) {
        this.ctxSummary = ctxSummary;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("facebook")
    public Facebook getFacebook() {
        return facebook;
    }

    @JsonProperty("facebook")
    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("mc2Token")
    public String getMc2Token() {
        return mc2Token;
    }

    @JsonProperty("mc2Token")
    public void setMc2Token(String mc2Token) {
        this.mc2Token = mc2Token;
    }

    @JsonProperty("modifiedDate")
    public Integer getModifiedDate() {
        return modifiedDate;
    }

    @JsonProperty("modifiedDate")
    public void setModifiedDate(Integer modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonProperty("otpSent")
    public String getOtpSent() {
        return otpSent;
    }

    @JsonProperty("otpSent")
    public void setOtpSent(String otpSent) {
        this.otpSent = otpSent;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("phoneVerified")
    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    @JsonProperty("phoneVerified")
    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    @JsonProperty("sms")
    public Sms getSms() {
        return sms;
    }

    @JsonProperty("sms")
    public void setSms(Sms sms) {
        this.sms = sms;
    }

    @JsonProperty("tenantId")
    public String getTenantId() {
        return tenantId;
    }

    @JsonProperty("tenantId")
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty("timeZone")
    public String getTimeZone() {
        return timeZone;
    }

    @JsonProperty("timeZone")
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @JsonProperty("twitter")
    public Twitter getTwitter() {
        return twitter;
    }

    @JsonProperty("twitter")
    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    @JsonProperty("useragent")
    public String getUseragent() {
        return useragent;
    }

    @JsonProperty("useragent")
    public void setUseragent(String useragent) {
        this.useragent = useragent;
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

    @JsonProperty("webchat")
    public Webchat getWebchat() {
        return webchat;
    }

    @JsonProperty("webchat")
    public void setWebchat(Webchat webchat) {
        this.webchat = webchat;
    }

    @JsonProperty("whatsApp")
    public WhatsApp getWhatsApp() {
        return whatsApp;
    }

    @JsonProperty("whatsApp")
    public void setWhatsApp(WhatsApp whatsApp) {
        this.whatsApp = whatsApp;
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
        return "UserInfo{" +
                "abc=" + abc +
                ", clientBlocked=" + clientBlocked +
                ", cmnextToken='" + cmnextToken + '\'' +
                ", company='" + company + '\'' +
                ", createdDate=" + createdDate +
                ", ctxSummary='" + ctxSummary + '\'' +
                ", email='" + email + '\'' +
                ", facebook=" + facebook +
                ", firstName='" + firstName + '\'' +
                ", id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", lastName='" + lastName + '\'' +
                ", locale='" + locale + '\'' +
                ", location='" + location + '\'' +
                ", mc2Token='" + mc2Token + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", otpSent='" + otpSent + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneVerified=" + phoneVerified +
                ", sms=" + sms +
                ", tenantId='" + tenantId + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", twitter=" + twitter +
                ", useragent='" + useragent + '\'' +
                ", verified=" + verified +
                ", waPhone='" + waPhone + '\'' +
                ", webchat=" + webchat +
                ", whatsApp=" + whatsApp +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}