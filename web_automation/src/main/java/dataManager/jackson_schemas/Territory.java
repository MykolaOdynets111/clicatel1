
package dataManager.jackson_schemas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "territoryId",
    "name",
    "timezone",
    "available",
    "country"
})
public class Territory {

    @JsonProperty("territoryId")
    private String territoryId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("available")
    private Boolean available;
    @JsonProperty("country")
    private List<Country> country = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("territoryId")
    public String getTerritoryId() {
        return territoryId;
    }

    @JsonProperty("territoryId")
    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @JsonProperty("country")
    public List<Country> getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(List<Country> country) {
        this.country = country;
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
