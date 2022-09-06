package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "departmentId",
        "departmentName",
        "supportHours",
        "days"
})
@Data
public class DepartmentMapping {

    @JsonProperty("departmentId")
    private String departmentId;
    @JsonProperty("departmentName")
    private String departmentName;
    @JsonProperty("supportHours")
    private List<String> supportHours;
    @JsonProperty("days")
    private List<String> days;

    @Override
    public String toString() {
        return "SupportHoursByDepartment {"
                + "departmentId=' " + departmentId + '\''
                + "departmentName=' " + departmentName + '\''
                + ", supportHours= " + supportHours + '}';
    }
}
