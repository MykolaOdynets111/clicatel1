package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "isoCode2",
        "name"
})
@Getter
public class Country {

    @JsonProperty("id")
    private int id;

    @JsonProperty("isoCode2")
    private String isoCode2;

    @JsonProperty("name")
    private String name;
}