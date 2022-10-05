package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "text",
        "imageRef",
        "description"
})
@Data @AllArgsConstructor @NoArgsConstructor
public class Options {

    @JsonProperty("text")
    private String text = "Up to 30 days";
    @JsonProperty("imageRef")
    private String imageRef = "null";
    @JsonProperty("description")
    private String description = "Receive up to 30 days of coverage";

}