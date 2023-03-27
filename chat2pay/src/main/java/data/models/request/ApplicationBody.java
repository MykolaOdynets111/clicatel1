package data.models.request;

import api.models.response.integration.NotificationUrls;
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
        "applicationId",
        "applicationName",
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

    @JsonProperty("notificationUrls")
    private NotificationUrls notificationUrls;

}
