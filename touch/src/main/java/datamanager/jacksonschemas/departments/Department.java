package datamanager.jacksonschemas.departments;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"Id", "tenantId", "name", "description", "agentIds", "primary"})
public class Department {

    @JsonProperty("id")
    private String id;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("primary")
    private String primary;
    @JsonProperty("agentIds")
    private List<String> agents;
}
