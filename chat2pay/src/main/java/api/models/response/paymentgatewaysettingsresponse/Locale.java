package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "lcid",
        "name"
})
@Getter
public class Locale {

    @JsonProperty("id")
    private int id;

    @JsonProperty("lcid")
    private String lcid;

    @JsonProperty("name")
    private String name;
}
