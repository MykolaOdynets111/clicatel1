package data.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
@Getter
public class TransactionType {

    @JsonProperty("id")
    public int id;

    @JsonProperty("name")
    public String name;
}