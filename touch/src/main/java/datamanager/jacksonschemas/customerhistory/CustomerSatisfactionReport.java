package datamanager.jacksonschemas.customerhistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "averageScore",
        "scoreCount",
        "endDate",
        "hour"
})
public class CustomerSatisfactionReport {
    @JsonProperty("scoreCount")
    private Integer scoreCount;
    @JsonProperty("averageScore")
    private Double averageScore;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("hour")
    private Integer hour;

    @JsonProperty("scoreCount")
    public Integer getScoreCount() {
        return scoreCount;
    }

    @JsonProperty("scoreCount")
    public void setScoreCount(Integer scoreCount) {
        this.scoreCount = scoreCount;
    }

    @JsonProperty("averageScore")
    public Double getAverageScore() {
        return averageScore;
    }

    @JsonProperty("averageScore")
    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
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
