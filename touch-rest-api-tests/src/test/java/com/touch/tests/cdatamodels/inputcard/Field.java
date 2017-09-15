package com.touch.tests.cdatamodels.inputcard;

/**
 * Created by oshcherbatyy on 15-09-17.
 */
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "encrypt",
        "placeholder",
        "required"
})
public class Field {

    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("encrypt")
    private String encrypt;
    @JsonProperty("placeholder")
    private String placeholder;
    @JsonProperty("required")
    private String required;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("encrypt")
    public String getEncrypt() {
        return encrypt;
    }

    @JsonProperty("encrypt")
    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    @JsonProperty("placeholder")
    public String getPlaceholder() {
        return placeholder;
    }

    @JsonProperty("placeholder")
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @JsonProperty("required")
    public String getRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(String required) {
        this.required = required;
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