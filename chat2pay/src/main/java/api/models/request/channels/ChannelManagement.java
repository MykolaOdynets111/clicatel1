package api.models.request.channels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "smsOmniIntegrationId",
        "whatsappOmniIntegrationId"
})
public class ChannelManagement {

    @JsonProperty("smsOmniIntegrationId")
    private String smsOmniIntegrationId;

    @JsonProperty("whatsappOmniIntegrationId")
    private String whatsappOmniIntegrationId;
}
