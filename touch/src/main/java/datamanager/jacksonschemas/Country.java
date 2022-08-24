
package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "countryId",
    "name",
    "available"
})
public class Country {

    @JsonProperty("countryId")
    private String countryId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("available")
    private Boolean available;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("countryId")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("countryId")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
