
package dataprovider.jackson_schemas.TIE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import dataprovider.jackson_schemas.TIE.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "id",
    "entities"
})
public class NERTrainset {

    @JsonProperty("text")
    private String text;
    @JsonProperty("id")
    private String id;
    @JsonProperty("entities")
    private List<dataprovider.jackson_schemas.TIE.Entity> entities = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("entities")
    public List<dataprovider.jackson_schemas.TIE.Entity> getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
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
