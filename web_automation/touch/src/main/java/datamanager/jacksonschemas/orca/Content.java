package datamanager.jacksonschemas.orca;
import datamanager.jacksonschemas.orca.event.TextEvent;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "extraFields",
        "event"
})
public class Content {

    @JsonProperty("extraFields")
    private ExtraFields extraFields;
    @JsonProperty("event")
    private TextEvent event;

    public Content() {
    }

    public Content(String messageText){
        this.setExtraFields(new ExtraFields());
        this.setEvent(new TextEvent(messageText));
    }

    @JsonProperty("extraFields")
    public ExtraFields getExtraFields() {
        return extraFields;
    }

    @JsonProperty("extraFields")
    public void setExtraFields(ExtraFields extraFields) {
        this.extraFields = extraFields;
    }

    @JsonProperty("event")
    public TextEvent getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(TextEvent event) {
        this.event = event;
    }
}