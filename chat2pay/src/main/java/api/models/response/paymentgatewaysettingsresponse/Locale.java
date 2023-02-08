package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "lcid",
        "name"
})
public class Locale {

    @JsonProperty("id")
    private int id;

    @JsonProperty("lcid")
    private String lcid;

    @JsonProperty("name")
    private String name;
}
