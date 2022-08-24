package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "departmentId",
        "supportHours"
})
public class DepartmentMapping {

    @JsonProperty("departmentId")
    private String departmentId;
    @JsonProperty("supportHours")
    private List<DepartmentSupportHour> supportHours = null;

    @JsonProperty("departmentId")
    public String getDepartmentId() {
        return departmentId;
    }

    @JsonProperty("departmentId")
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @JsonProperty("supportHours")
    public List<DepartmentSupportHour> getSupportHours() {
        return supportHours;
    }

    @JsonProperty("supportHours")
    public void setSupportHours(List<DepartmentSupportHour> supportHours) {
        this.supportHours = supportHours;
    }

    @Override
    public String toString() {
        return "DepartmentMapping{" +
                "departmentId='" + departmentId + '\'' +
                ", supportHours=" + supportHours +
                '}';
    }
}
