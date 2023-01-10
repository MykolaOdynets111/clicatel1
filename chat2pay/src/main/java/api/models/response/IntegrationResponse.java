package api.models.response;

import api.models.response.integration.Integrator;
import api.models.response.integration.NotificationUrls;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "notificationUrls",
        "integrationStatus",
        "integrator"
})

@Data
public class IntegrationResponse {
    @JsonProperty("notificationUrls")
    private NotificationUrls notificationUrls;
    @JsonProperty("integrationStatus")
    private String integrationStatus;
    @JsonProperty("integrator")
    private Integrator integrator;
}
