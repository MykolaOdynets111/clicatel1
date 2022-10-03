package datamanager.jacksonschemas.supportHours;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

import static java.util.Collections.singletonList;

@Data
@JsonPropertyOrder({
        "agentSupportHours",
        "supportHoursByDepartment"
})
public class GeneralSupportHoursItem {

    @JsonProperty("agentSupportHours")
    private List<SupportHoursMapping> agentMapping;

    @JsonProperty("supportHoursByDepartment")
    private List<DepartmentSupportHoursMapping> departmentMapping;

    public GeneralSupportHoursItem(List<String> agentDays) {
        this.agentMapping = singletonList(new SupportHoursMapping(agentDays));
        this.departmentMapping = DepartmentSupportHoursMapping.getAllDepartmentsWithDefaultValue();
    }

    public GeneralSupportHoursItem(String startWorkTime, String endWorkTime, List<String> agentDays) {
        this.agentMapping = singletonList(new SupportHoursMapping(startWorkTime, endWorkTime, agentDays));
        this.departmentMapping = DepartmentSupportHoursMapping.getAllDepartmentsWithDefaultValue();
    }

    public GeneralSupportHoursItem() {
        this.agentMapping = singletonList(new SupportHoursMapping());
        this.departmentMapping = DepartmentSupportHoursMapping.getAllDepartmentsWithDefaultValue();
    }
    public GeneralSupportHoursItem(List<SupportHoursMapping> agentHoursModel,
                                   List<DepartmentSupportHoursMapping> departmentHoursModel) {
        this.agentMapping = agentHoursModel;
        this.departmentMapping = departmentHoursModel;
    }
}
