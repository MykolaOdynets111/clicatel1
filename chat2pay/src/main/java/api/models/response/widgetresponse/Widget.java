package api.models.response.widgetresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

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

@Data
public class Widget {

    @JsonProperty("status")
    private String status;

    @JsonProperty("configStatus")
    private ConfigStatus configStatus;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("createdTime")
    private String createdTime;

    @JsonProperty("modifiedTime")
    private String modifiedTime;

    @JsonProperty("smsOmniIntegrationId")
    private Object smsOmniIntegrationId;

    @JsonProperty("waOmniIntegrationId")
    private Object waOmniIntegrationId;

    @JsonProperty("smsOmniIntegrationStatus")
    private boolean smsOmniIntegrationStatus;

    @JsonProperty("waOmniIntegrationStatus")
    private boolean waOmniIntegrationStatus;

    @JsonProperty("createdByUserName")
    private String createdByUserName;

    @JsonProperty("createdByUserId")
    private String createdByUserId;

    @JsonProperty("showTutorial")
    private boolean showTutorial;

    @JsonProperty("showLinkedApi")
    private boolean showLinkedApi;

    @JsonProperty("enabledApplicationCount")
    private int enabledApplicationCount;

    @JsonProperty("disabledApplicationCount")
    private int disabledApplicationCount;
}
