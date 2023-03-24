package data.models.response.widget;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "widgetId",
        "createdTime"
})

@Getter
public class WidgetCreation {
    @JsonProperty("widgetId")
    private String widgetId;

    @JsonProperty("createdTime")
    private String createdTime;

    public LocalDate getCreatedTime() {
        return parseToLocalDate(this.createdTime);
    }
}
