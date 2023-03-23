package data.models.response.widget;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigStatus {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}
