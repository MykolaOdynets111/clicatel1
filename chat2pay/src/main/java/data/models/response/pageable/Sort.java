package data.models.response.pageable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "unsorted",
        "sorted",
        "empty"
})
public class Sort {

    @JsonProperty("unsorted")
    private Boolean unsorted;
    @JsonProperty("sorted")
    private Boolean sorted;
    @JsonProperty("empty")
    private Boolean empty;
}
