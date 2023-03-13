package api.models.response.widget;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "configStatus",
        "type",
        "name",
        "id",
        "accountId",
        "environment",
        "createdTime",
        "modifiedTime",
        "smsOmniIntegrationId",
        "waOmniIntegrationId",
        "smsOmniIntegrationStatus",
        "waOmniIntegrationStatus",
        "createdByUserName",
        "createdByUserId",
        "showTutorial",
        "showLinkedApi",
        "enabledApplicationCount",
        "disabledApplicationCount"
})

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Widget {

    @JsonProperty("status")
    public String status;

    @JsonProperty("configStatus")
    public ConfigStatus configStatus;

    @JsonProperty("type")
    public String type;

    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public String id;

    @JsonProperty("accountId")
    public String accountId;

    @JsonProperty("environment")
    public String environment;

    @JsonProperty("createdTime")
    public String createdTime;

    @JsonProperty("modifiedTime")
    public String modifiedTime;

    @JsonProperty("smsOmniIntegrationId")
    public Object smsOmniIntegrationId;

    @JsonProperty("waOmniIntegrationId")
    public Object waOmniIntegrationId;

    @JsonProperty("smsOmniIntegrationStatus")
    public boolean smsOmniIntegrationStatus;

    @JsonProperty("waOmniIntegrationStatus")
    public boolean waOmniIntegrationStatus;

    @JsonProperty("createdByUserName")
    public String createdByUserName;

    @JsonProperty("createdByUserId")
    public String createdByUserId;

    @JsonProperty("showTutorial")
    public boolean showTutorial;

    @JsonProperty("showLinkedApi")
    public boolean showLinkedApi;

    @JsonProperty("enabledApplicationCount")
    public int enabledApplicationCount;

    @JsonProperty("disabledApplicationCount")
    public int disabledApplicationCount;
}
