package datamanager.jacksonschemas.orca;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

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
        "oauth2Token",
        "channelProfileName"
})
@Data
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
    @JsonProperty("department")
    private String department;
    @JsonProperty("oauth2Token")
    private String oauth2Token;
    @JsonProperty("channelProfileName")
    private String channelProfileName;


    public ExtraFields(String name){
        this.setLocale("en_uk");
        this.setAgent("iOs");
        this.setChannelProfileName(name);
    }

}