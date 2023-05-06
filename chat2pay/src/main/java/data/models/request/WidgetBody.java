package data.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import data.models.response.widget.ConfigStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "configStatus",
        "environment"
})
public class WidgetBody {

    @JsonProperty("type")
    private String type = "CHAT_TO_PAY";
    @JsonProperty("configStatus")
    private ConfigStatus configStatus = new ConfigStatus();
    @JsonProperty("environment")
    private String environment = "SANDBOX";
}
