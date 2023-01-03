package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;


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

@Data
public class Content {

    @JsonProperty("content")
    private ArrayList<Widget> widgets;

    @JsonProperty("pageable")
    private Object pageable;

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
    private Object sort;

    @JsonProperty("numberOfElements")
    private int numberOfElements;

    @JsonProperty("size")
    private int size;

    @JsonProperty("empty")
    private boolean empty;

}