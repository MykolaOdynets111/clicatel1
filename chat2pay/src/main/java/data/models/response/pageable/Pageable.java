package data.models.response.pageable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sort",
        "pageNumber",
        "pageSize",
        "offset",
        "unpaged",
        "paged"
})
public class Pageable {

    @JsonProperty("sort")
    private Sort sort;
    @JsonProperty("pageNumber")
    private Integer pageNumber;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("unpaged")
    private Boolean unpaged;
    @JsonProperty("paged")
    private Boolean paged;
}
