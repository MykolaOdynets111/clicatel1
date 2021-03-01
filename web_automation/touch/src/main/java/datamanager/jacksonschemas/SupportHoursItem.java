package datamanager.jacksonschemas;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "departmentMapping",
        "agentMapping",
        "department"
})
public class SupportHoursItem {

    @JsonProperty("departmentMapping")
    private List<DepartmentMapping> departmentMapping = null;
    @JsonProperty("agentMapping")
    private List<AgentMapping> agentMapping = null;
    @JsonProperty("department")
    private Boolean department;

    @JsonProperty("departmentMapping")
    public List<DepartmentMapping> getDepartmentMapping() {
        return departmentMapping;
    }

    @JsonProperty("departmentMapping")
    public void setDepartmentMapping(List<DepartmentMapping> departmentMapping) {
        this.departmentMapping = departmentMapping;
    }

    @JsonProperty("agentMapping")
    public List<AgentMapping> getAgentMapping() {
        return agentMapping;
    }

    @JsonProperty("agentMapping")
    public void setAgentMapping(List<AgentMapping> agentMapping) {
        this.agentMapping = agentMapping;
    }

    @JsonProperty("department")
    public Boolean getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(Boolean department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "SupportHoursItem{" +
                "departmentMapping=" + departmentMapping +
                ", agentMapping=" + agentMapping +
                ", department=" + department +
                '}';
    }
}
