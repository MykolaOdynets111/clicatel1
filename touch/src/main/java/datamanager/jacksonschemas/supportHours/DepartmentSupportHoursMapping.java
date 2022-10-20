package datamanager.jacksonschemas.supportHours;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.Tenants;
import datamanager.jacksonschemas.departments.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static apihelper.ApiHelperDepartments.getDepartments;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "departmentId",
        "departmentName",
        "supportHours"
})
public class DepartmentSupportHoursMapping {

    @JsonProperty("departmentId")
    private String departmentId;
    @JsonProperty("departmentName")
    private String departmentName;
    @JsonProperty("supportHours")
    private List<SupportHoursMapping> supportHours = new ArrayList<>();

    public DepartmentSupportHoursMapping(Department department) {
        this.setDepartmentId(department.getId());
        this.setDepartmentName(department.getName());
        supportHours.add(new SupportHoursMapping());
    }

    public static List<DepartmentSupportHoursMapping> getAllDepartmentsWithDefaultValue() {
        List<DepartmentSupportHoursMapping> departmentMappingList = new ArrayList<>();
        for (Department department : getDepartments(Tenants.getTenantUnderTestOrgName())) {
            departmentMappingList.add(new DepartmentSupportHoursMapping(department));
        }
        return departmentMappingList;
    }

    public static List<DepartmentSupportHoursMapping> setDaysForDepartment(String department, List<String> days) {
        List<DepartmentSupportHoursMapping> updatedValue = getAllDepartmentsWithDefaultValue();
        updatedValue.stream()
                .filter(d -> d.getDepartmentName().equals(department)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("There is no department :" + department))
                .getSupportHours().get(0).setDays(days);
        return updatedValue;
    }
}
