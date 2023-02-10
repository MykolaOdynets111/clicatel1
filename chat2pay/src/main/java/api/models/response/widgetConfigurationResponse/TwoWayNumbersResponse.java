package api.models.response.widgetConfigurationResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "number",
        "default"
})
@Getter
public class TwoWayNumbersResponse {

    public List<TwoWayNumbersBody> twoWayNumbers;
}
