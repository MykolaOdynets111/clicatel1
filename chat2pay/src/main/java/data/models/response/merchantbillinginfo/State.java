package data.models.response.merchantbillinginfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({
        "id",
        "isoCode2",
        "name"
})
@Getter
public class State {

    @JsonProperty("id")
    public int id;

    @JsonProperty("isoCode2")
    public String isoCode2;

    @JsonProperty("name")
    public String name;
}