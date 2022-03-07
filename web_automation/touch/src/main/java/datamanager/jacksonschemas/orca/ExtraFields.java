package datamanager.jacksonschemas.orca;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "locale",
        "agent",
        "intent",
        "group",
        "abc",
        "merchantSession",
        "endpoints",
        "addressInfo",
        "token",
        "oauth2Token"
})
public class ExtraFields {

    @JsonProperty("locale")
    private String locale;
    @JsonProperty("agent")
    private String agent;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("group")
    private String group;
    @JsonProperty("abc")
    private ExtraFieldsAbc abc = null;
    @JsonProperty("merchantSession")
    private String merchantSession;
    @JsonProperty("endpoints")
    private String endpoints;
    @JsonProperty("addressInfo")
    private String addressInfo;
    @JsonProperty("token")
    private String token;
    @JsonProperty("oauth2Token")
    private String oauth2Token;

    public ExtraFields(){
        this.setLocale("en_uk");
        this.setAgent("iOs");
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("agent")
    public String getAgent() {
        return agent;
    }

    @JsonProperty("agent")
    public void setAgent(String agent) {
        this.agent = agent;
    }

    @JsonProperty("intent")
    public String getIntent() {
        return intent;
    }

    @JsonProperty("intent")
    public void setIntent(String intent) {
        this.intent = intent;
    }

    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
    }

    @JsonProperty("abc")
    public ExtraFieldsAbc getAbc() {
        return abc;
    }

    @JsonProperty("abc")
    public void setAbc(ExtraFieldsAbc abc) {
        this.abc = abc;
    }

    @JsonProperty("merchantSession")
    public String getMerchantSession() {
        return merchantSession;
    }

    @JsonProperty("merchantSession")
    public void setMerchantSession(String merchantSession) {
        this.merchantSession = merchantSession;
    }

    @JsonProperty("endpoints")
    public String getEndpoints() {
        return endpoints;
    }

    @JsonProperty("endpoints")
    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }

    @JsonProperty("addressInfo")
    public String getAddressInfo() {
        return addressInfo;
    }

    @JsonProperty("addressInfo")
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("oauth2Token")
    public String getOauth2Token() {
        return oauth2Token;
    }

    @JsonProperty("oauth2Token")
    public void setOauth2Token(String oauth2Token) {
        this.oauth2Token = oauth2Token;
    }

    @Override
    public String toString() {
        return "ExtraFields{" +
                "locale='" + locale + '\'' +
                ", agent='" + agent + '\'' +
                ", intent='" + intent + '\'' +
                ", group='" + group + '\'' +
                ", abc=" + abc +
                ", merchantSession='" + merchantSession + '\'' +
                ", endpoints='" + endpoints + '\'' +
                ", addressInfo='" + addressInfo + '\'' +
                ", token='" + token + '\'' +
                ", oauth2Token='" + oauth2Token + '\'' +
                '}';
    }
}