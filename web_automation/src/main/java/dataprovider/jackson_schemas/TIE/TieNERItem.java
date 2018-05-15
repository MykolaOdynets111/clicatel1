
package dataprovider.jackson_schemas.TIE;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text",
    "id",
    "entities"
})
public class TieNERItem {

    @JsonProperty("text")
    private String text;
    @JsonProperty("id")
    private String id;
    @JsonProperty("entities")
    private List<Entity> entities = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public TieNERItem setText(String text) {
        this.text = text;
        return this;
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
    public List<Entity> getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public TieNERItem setEntities(List<Entity> entities) {
        this.entities = entities;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString(){
        return "{\"text\":  \""+this.getText()+"\", \"entities\": [{ \"start\": "+this.getEntities().get(0).getStart()+", \"end\": "+this.getEntities().get(0).getEnd()+", \"type\": \""+this.getEntities().get(0).getType()+"\"}]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TieNERItem that = (TieNERItem) o;
        return
                Objects.equals(text, that.text) &&
                        Objects.equals(entities, that.entities);
    }
}
