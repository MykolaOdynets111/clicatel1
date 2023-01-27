package api.models.response.c2pconfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static datetimeutils.DateTimeHelper.getYYYY_MM_DD_HH_MM_SS;

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
@Data
public class ConfigurationBody {

    @JsonProperty("updateTime")
    private String updateTime;
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
        String localDateTime = Arrays.stream(updateTime.split("\\+")).findFirst()
                .orElseThrow(NoSuchElementException::new);

        return LocalDateTime.parse(localDateTime, getYYYY_MM_DD_HH_MM_SS()).toLocalDate();
    }
}
