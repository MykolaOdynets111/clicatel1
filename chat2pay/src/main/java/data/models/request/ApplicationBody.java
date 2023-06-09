package data.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import data.models.response.integration.NotificationUrls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "applicationId",
        "applicationName",
        "applicationType",
        "notificationUrls"
})

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationBody {

    @JsonProperty("status")
    private String status;

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("applicationType")
    private String applicationType;

    @JsonProperty("notificationUrls")
    private NotificationUrls notificationUrls;
}
