package api.models.response.widgetresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "widgetId",
        "createdTime"
})

@Data
public class WidgetCreation {
    @JsonProperty("widgetId")
    private String widgetId;

    @JsonProperty("createdTime")
    private String createdTime;

    public LocalDate getCreatedTime() {
        return parseToLocalDate(createdTime);
    }
}
