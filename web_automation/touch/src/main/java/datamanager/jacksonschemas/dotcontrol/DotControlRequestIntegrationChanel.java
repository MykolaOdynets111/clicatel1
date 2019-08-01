package datamanager.jacksonschemas.dotcontrol;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonInclude;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "enabled",
        "url",
        "adapter",
        "config"

})
public class DotControlRequestIntegrationChanel {

    @JsonProperty("name")
    private String name;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("url")
    private String url;
    @JsonProperty("adapter")
    private String adapter;
    @JsonProperty("config")
    private Object config;

    public DotControlRequestIntegrationChanel(String url, String adapter ){
        this.name = "adapter:" + adapter;
        this.enabled = true;
        this.url = url;
        this.adapter = adapter;
        this.config = new Object();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public Object getConfig() {
        return config;
    }

    public void setConfig(Object config) {
        this.config = config;
    }



    @Override
    public String toString(){
        return "    {\n" +
                "      \"apiKey\": \"" + "" + "\",\n" +
                "      \"name\": \"" + this.getName() + "\",\n" +
                "      \"enabled\": " + this.getEnabled() + ",\n" +
                "      \"url\": \"" + this.getUrl() + "\",\n" +
                "      \"type\": \""  + this.getAdapter() + "\"\n"+
                "    }\n";
    }

}
