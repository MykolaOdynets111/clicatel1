package datamanager.jacksonschemas;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "agentSupportHours",
        "supportHoursByDepartment"
})
@Data
public class SupportHoursItem {

    @JsonProperty("agentSupportHours")
    private List <AgentMapping> agentMapping;
    @JsonProperty("supportHoursByDepartment")
    private List <DepartmentMapping> departmentMapping;

    @Override
    public String toString() {
        return "SupportHoursItem {"
                + "agentSupportHours = " + agentMapping
                + ", supportHoursByDepartment = " + departmentMapping + '}';
    }
}
