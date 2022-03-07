package datamanager.jacksonschemas.customerhistory;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "negativeSentimentsCount",
        "positiveSentimentsCount",
        "neutralSentimentsCount",
        "negativeSentimentsChange",
        "positiveSentimentsChange",
        "neutralSentimentsChange",
        "hour",
        "endDate"
})
public class PastSentimentReport {
    @JsonProperty("negativeSentimentsCount")
    private Integer negativeSentimentsCount;
    @JsonProperty("positiveSentimentsCount")
    private Integer positiveSentimentsCount;
    @JsonProperty("neutralSentimentsCount")
    private Integer neutralSentimentsCount;
    @JsonProperty("negativeSentimentsChange")
    private Double negativeSentimentsChange;
    @JsonProperty("positiveSentimentsChange")
    private Double positiveSentimentsChange;
    @JsonProperty("neutralSentimentsChange")
    private Double neutralSentimentsChange;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("hour")
    private Integer hour;

    @JsonProperty("negativeSentimentsCount")
    public Integer getNegativeSentimentsCount() {
        return negativeSentimentsCount;
    }

    @JsonProperty("negativeSentimentsCount")
    public void setNegativeSentimentsCount(Integer negativeSentimentsCount) {
        this.negativeSentimentsCount = negativeSentimentsCount;
    }

    @JsonProperty("positiveSentimentsCount")
    public Integer getPositiveSentimentsCount() {
        return positiveSentimentsCount;
    }

    @JsonProperty("positiveSentimentsCount")
    public void setPositiveSentimentsCount(Integer positiveSentimentsCount) {
        this.positiveSentimentsCount = positiveSentimentsCount;
    }

    @JsonProperty("neutralSentimentsCount")
    public Integer getNeutralSentimentsCount() {
        return neutralSentimentsCount;
    }

    @JsonProperty("neutralSentimentsCount")
    public void setNeutralSentimentsCount(Integer neutralSentimentsCount) {
        this.neutralSentimentsCount = neutralSentimentsCount;
    }

    @JsonProperty("negativeSentimentsChange")
    public Double getNegativeSentimentsChange() {
        return negativeSentimentsChange;
    }

    @JsonProperty("negativeSentimentsChange")
    public void setNegativeSentimentsChange(Double negativeSentimentsChange) {
        this.negativeSentimentsChange = negativeSentimentsChange;
    }

    @JsonProperty("positiveSentimentsChange")
    public Double getPositiveSentimentsChange() {
        return positiveSentimentsChange;
    }

    @JsonProperty("positiveSentimentsChange")
    public void setPositiveSentimentsChange(Double positiveSentimentsChange) {
        this.positiveSentimentsChange = positiveSentimentsChange;
    }

    @JsonProperty("neutralSentimentsChange")
    public Double getNeutralSentimentsChange() {
        return neutralSentimentsChange;
    }

    @JsonProperty("neutralSentimentsChange")
    public void setNeutralSentimentsChange(Double neutralSentimentsChange) {
        this.neutralSentimentsChange = neutralSentimentsChange;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("hour")
    public Integer getHour() {
        return hour;
    }

    @JsonProperty("hour")
    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
