package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "options",
        "multipleSelection"
})
@Data @AllArgsConstructor @NoArgsConstructor
public class Groups {

    @JsonProperty("title")
    private String title = "Choose your duration for travel insurance";
    @JsonProperty("options")
    private List<Options> options = Arrays.asList(new Options());
    @JsonProperty("multipleSelection")
    private boolean multipleSelection = true;
}