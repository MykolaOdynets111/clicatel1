package api.models.response.c2pconfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static datetimeutils.DateTimeHelper.parseToLocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "updateTime",
        "whatsappChannelEnabled",
        "smsChannelEnabled",
        "integrators",
        "apiKey",
        "supportedCurrencies",
        "environment"
})
@Getter
public class ConfigurationBody {

    @JsonProperty("updateTime")
    public String updateTime;
    @JsonProperty("whatsappChannelEnabled")
    public boolean whatsappChannelEnabled;
    @JsonProperty("smsChannelEnabled")
    public boolean smsChannelEnabled;
    @JsonProperty("integrators")
    public List<ConfigurationIntegrator> integrators;
    @JsonProperty("apiKey")
    public String apiKey;
    @JsonProperty("supportedCurrencies")
    public List<SupportedCurrency> supportedCurrencies;
    @JsonProperty("environment")
    public String environment;

    public LocalDate getUpdatedTime() {
        return parseToLocalDate(this.updateTime);
    }
}
