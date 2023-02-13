package api.models.response.widgetConfigurationResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "updateTime",
        "whatsappChannelEnabled",
        "smsChannelEnabled"
})

@Getter
public class ChannelManagementStatusResponse {

    @JsonProperty("updateTime")
    private String updateTime;

    @JsonProperty("whatsappChannelEnabled")
    public boolean whatsappChannelEnabled;

    @JsonProperty("smsChannelEnabled")
    public boolean smsChannelEnabled;

    public LocalDate getUpdateTime() {
        return parseToLocalDate(this.updateTime);
    }
}
