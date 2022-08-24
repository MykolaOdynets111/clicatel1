package testflo.jacksonschemas;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isGroup",
        "cells"
})
public class NewTCTStep {


    @JsonProperty("isGroup")
    private Boolean isGroup;
    @JsonProperty("cells")
    private List<String> cells = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public NewTCTStep(){
        this.isGroup = false;
    }


    @JsonProperty("isGroup")
    public Boolean getIsGroup() {
        return isGroup;
    }

    @JsonProperty("isGroup")
    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    @JsonProperty("cells")
    public List<String> getCells() {
        return cells;
    }

    @JsonProperty("cells")
    public NewTCTStep setCells(List<String> cells) {
        this.cells = cells;
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

    public NewTCTStep updateCells(String newValue, int index){
        this.cells.add(index, newValue);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                ", isGroup=" + isGroup +
                ", cells=" + cells +
                '}';
    }
}
