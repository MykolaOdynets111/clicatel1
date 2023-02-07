package api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "updateTime"
})

public class UpdatedEntityResponse {

    @JsonProperty("updateTime")
    private String updateTime;

    public LocalDate getUpdateTime() {
        return parseToLocalDate(updateTime);
    }
}
