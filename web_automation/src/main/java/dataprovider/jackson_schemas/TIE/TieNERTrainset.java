//package dataprovider.jackson_schemas.TIE;
//
//import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonInclude;
//import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;
//import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//import java.util.List;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "NER_trainset"
//})
//public class TieNERTrainset {
//
//    @JsonProperty("NER_trainset")
//    private List<TieNERItem> tieNERItems = null;
//
//    @JsonProperty("NER_trainset")
//    public List<TieNERItem> getTieNERItems() {
//        return tieNERItems;
//    }
//
//    @JsonProperty("NER_trainset")
//    public void setTieNERItems(List<TieNERItem> entities) {
//        this.tieNERItems = entities;
//    }
//}



package dataprovider.jackson_schemas.TIE;

        import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "NER_trainset"
})
public class TieNERTrainset {

    @JsonProperty("NER_trainset")
    private List<TieNERItem> nERTrainset = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("NER_trainset")
    public List<TieNERItem> getNERTrainset() {
        return nERTrainset;
    }

    @JsonProperty("NER_trainset")
    public void setNERTrainset(List<TieNERItem> nERTrainset) {
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
