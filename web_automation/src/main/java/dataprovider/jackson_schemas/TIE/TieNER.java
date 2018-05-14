
package dataprovider.jackson_schemas.TIE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "NER_trainset"
})
public class TieNER {

    @JsonProperty("NER_trainset")
    private List<NERTrainset> nERTrainset = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("NER_trainset")
    public List<NERTrainset> getNERTrainset() {
        return nERTrainset;
    }

    @JsonProperty("NER_trainset")
    public void setNERTrainset(List<NERTrainset> nERTrainset) {
        this.nERTrainset = nERTrainset;
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
