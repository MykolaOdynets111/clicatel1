package testflo.jacksonschemas;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "defects",
        "attachments",
        "isGroup",
        "cells",
        "group"
})
public class NewTCStep {

    @JsonProperty("defects")
    private List<Object> defects = null;
    @JsonProperty("attachments")
    private List<Object> attachments = null;
    @JsonProperty("isGroup")
    private Boolean isGroup;
    @JsonProperty("cells")
    private List<String> cells = null;
    @JsonProperty("group")
    private Boolean group;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public NewTCStep(){
        this.defects = Arrays.asList(new Object[0]);
        this.attachments = Arrays.asList(new Object[0]);;
        this.isGroup = false;
        this.group = false;
    }

    @JsonProperty("defects")
    public List<Object> getDefects() {
        return defects;
    }

    @JsonProperty("defects")
    public void setDefects(List<Object> defects) {
        this.defects = defects;
    }

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
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
    public NewTCStep setCells(List<String> cells) {
        this.cells = cells;
        return this;
    }

    @JsonProperty("group")
    public Boolean getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(Boolean group) {
        this.group = group;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public NewTCStep updateCells(String newValue, int index){
        this.cells.add(index, newValue);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "defects=" + defects +
                ", attachments=" + attachments +
                ", isGroup=" + isGroup +
                ", cells=" + cells +
                ", group=" + group +
                '}';
    }
}
