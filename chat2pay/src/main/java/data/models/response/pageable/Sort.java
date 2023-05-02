package data.models.response.pageable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "unsorted",
        "sorted",
        "empty"
})
public class Sort {

    @JsonProperty("unsorted")
    public Boolean unsorted;
    @JsonProperty("sorted")
    public Boolean sorted;
    @JsonProperty("empty")
    public Boolean empty;
}
