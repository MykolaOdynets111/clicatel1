package api.models.request.widgetconfigurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numbers",
        "defaultNumber"
})
public class TwoWayNumberConfiguration {

    @JsonProperty("numbers")
    private List<String> numbers;

    @JsonProperty("defaultNumber")
    private String defaultNumber;
}
