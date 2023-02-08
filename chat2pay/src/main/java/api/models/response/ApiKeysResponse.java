package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "apiKey",
        "createdTime"
})

@Getter
public class ApiKeysResponse {
    @JsonProperty("apiKey")
    public String apiKey;
    @JsonProperty("createdTime")
    public String createdTime;
}
