package api.models.response.failedresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.time.LocalDate;
import java.util.Map;

import static datetimeutils.DateTimeHelper.parseToLocalDate;
import static java.lang.Integer.parseInt;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "status",
        "error",
        "path"
})
public class UnauthorisedResponse {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("error")
    public String error;
    @JsonProperty("path")
    public String path;

    public LocalDate getTimestamp() {
        return parseToLocalDate(timestamp);
    }

    public static void verifyUnauthorisedResponse(Map<String, String> valuesMap, Response response) {
        UnauthorisedResponse unsuccessful = response.as(UnauthorisedResponse.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(parseInt(valuesMap.get("responseCode"))).isEqualTo(unsuccessful.status);
        softly.assertThat(valuesMap.get("error")).isEqualTo(unsuccessful.error);
        softly.assertThat(valuesMap.get("path")).isEqualTo(unsuccessful.path);
        softly.assertThat(unsuccessful.getTimestamp()).isEqualTo(LocalDate.now());
        softly.assertAll();
    }
}
