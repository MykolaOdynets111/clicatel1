package data.models.response.widget;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import data.models.response.pageable.Pageable;
import data.models.response.pageable.Sort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "content",
        "pageable",
        "last",
        "totalPages",
        "totalElements",
        "first",
        "number",
        "sort",
        "numberOfElements",
        "size",
        "empty"
})

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WidgetsContent {

    @JsonProperty("content")
    private List<Widget> widgets;

    @JsonProperty("pageable")
    private Pageable pageable;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("totalElements")
    private int totalElements;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("number")
    private int number;

    @JsonProperty("sort")
    private Sort sort;

    @JsonProperty("numberOfElements")
    private int numberOfElements;

    @JsonProperty("size")
    private int size;

    @JsonProperty("empty")
    private boolean empty;
}
