package api.models.response.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "notificationUrls",
        "integrationStatus",
        "integrator"
})

@Getter
public class IntegrationResponse {
    @JsonProperty("notificationUrls")
    private NotificationUrls notificationUrls;
    @JsonProperty("integrationStatus")
    private String integrationStatus;
    @JsonProperty("integrator")
    private Integrator integrator;
}
