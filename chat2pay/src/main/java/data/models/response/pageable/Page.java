package data.models.response.pageable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sort",
        "pageNumber",
        "pageSize",
        "offset",
        "unpaged",
        "paged"
})
public class Page {

    @JsonProperty("sort")
    public Sort sort;
    @JsonProperty("pageNumber")
    public Integer pageNumber;
    @JsonProperty("pageSize")
    public Integer pageSize;
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("unpaged")
    public Boolean unpaged;
    @JsonProperty("paged")
    public Boolean paged;
}
