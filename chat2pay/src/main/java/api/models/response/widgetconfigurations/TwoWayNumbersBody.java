package api.models.response.widgetconfigurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "number",
        "default"
})

@Getter
public class TwoWayNumbersBody {

    @JsonProperty("number")
    private String number;

    @JsonProperty("default")
    private boolean isDefault;
}
