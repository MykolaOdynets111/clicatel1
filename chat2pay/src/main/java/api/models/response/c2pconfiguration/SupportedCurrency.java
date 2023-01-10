package api.models.response.c2pconfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "iso",
        "name",
        "symbol",
        "isDefault"
})
@Data
public class SupportedCurrency {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("iso")
    public String iso;
    @JsonProperty("name")
    public String name;
    @JsonProperty("symbol")
    public String symbol;
    @JsonProperty("isDefault")
    public boolean isDefault;
}
