package datamanager.jacksonschemas.orca;
import datamanager.jacksonschemas.orca.event.Event;
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
    private Event event;

    public Content() {
    }

    public Content(String messageText){
        this.setExtraFields(new ExtraFields());
        this.setEvent(new Event(messageText));
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
    public Event getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Content{" +
                "extraFields=" + extraFields +
                ", event=" + event +
                '}';
    }
}