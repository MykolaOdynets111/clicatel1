package api.models.request.channels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "waOmniIntStatus",
        "smsOmniIntStatus"
})
public class ChannelStatus {

    @JsonProperty("waOmniIntStatus")
    private String waOmniIntStatus;

    @JsonProperty("smsOmniIntStatus")
    private String smsOmniIntStatus;
}
