package api.models.response.updateEntityResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "updateTime",
        "message",
        "showLinkedApi",
        "enabledApplicationCount",
        "disabledApplicationCount"
})

@Getter
public class UpdatedEntityResponse {

    @JsonProperty("updateTime")
    private String updateTime;

    @JsonProperty("message")
    private String message;

    @JsonProperty("showLinkedApi")
    public boolean showLinkedApi;

    @JsonProperty("enabledApplicationCount")
    public int enabledApplicationCount;

    @JsonProperty("disabledApplicationCount")
    public int disabledApplicationCount;

    public LocalDate getUpdateTime() {
        return parseToLocalDate(this.updateTime);
    }
}
