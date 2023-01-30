package api.models.response.widgetresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "updateTime"
})

@Data
public class WidgetDelete {

    @JsonProperty("updateTime")
    private String updateTime;

    public LocalDate getTimestamp() {
        return parseToLocalDate(updateTime);
    }
}
