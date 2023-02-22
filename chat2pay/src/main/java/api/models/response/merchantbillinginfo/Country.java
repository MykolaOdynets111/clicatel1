package api.models.response.merchantbillinginfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "isoCode2",
        "name"
})

public class Country {

    @JsonProperty("id")
    public int id;

    @JsonProperty("isoCode2")
    public String isoCode2;

    @JsonProperty("name")
    public String name;
}