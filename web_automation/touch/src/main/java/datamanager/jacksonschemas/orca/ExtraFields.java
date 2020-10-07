package datamanager.jacksonschemas.orca;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "locale",
        "agent"
})
public class ExtraFields {

    @JsonProperty("locale")
    private String locale;
    @JsonProperty("agent")
    private String agent;

    public ExtraFields(){
        this.setLocale("en_uk");
        this.setAgent("iOs");
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("agent")
    public String getAgent() {
        return agent;
    }

    @JsonProperty("agent")
    public void setAgent(String agent) {
        this.agent = agent;
    }

}