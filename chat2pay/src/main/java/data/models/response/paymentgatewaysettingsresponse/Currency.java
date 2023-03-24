package data.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "iso",
        "name",
        "symbol"
})
@Getter
public class Currency {

    @JsonProperty("id")
    private int id;

    @JsonProperty("iso")
    private String iso;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;
}