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
        "department",
        "token",
        "oauth2Token",
        "channelProfileName",
        "displayType",
        "messageId"
})
@Data @AllArgsConstructor @NoArgsConstructor
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
    private ExtraFieldsAbc abc;
    @JsonProperty("merchantSession")
    private String merchantSession;
    @JsonProperty("endpoints")
    private String endpoints;
    @JsonProperty("addressInfo")
    private String addressInfo;
    @JsonProperty("department")
    private String department;
    @JsonProperty("token")
    private String token;
    @JsonProperty("oauth2Token")
    private String oauth2Token;
    @JsonProperty("channelProfileName")
    private String channelProfileName;
    @JsonProperty("displayType")
    private String displayType;
    @JsonProperty("messageId")
    private String messageId;

    public ExtraFields(String name){
        this.setLocale("en_uk");
        this.setAgent("iOs");
        this.setChannelProfileName(name);
    }
}