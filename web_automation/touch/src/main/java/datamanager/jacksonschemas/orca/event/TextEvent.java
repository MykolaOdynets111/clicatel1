package datamanager.jacksonschemas.orca.event;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventType",
        "text",
        "initial",
        "release",
        "structuredText"
})
public class TextEvent {

    @JsonProperty("eventType")
    protected EventType eventType;
    @JsonProperty("text")
    private String text;
    @JsonProperty("initial")
    private String initial;
    @JsonProperty("release")
    private String release;
    @JsonProperty("structuredText")
    private String structuredText;

    public TextEvent() {
    }

    public TextEvent(String messageText){
        this.setEventType(EventType.TEXT);
        this.setText(messageText);
    }

    @JsonProperty("eventType")
    public EventType getEventType() {
        return eventType;
    }

    @JsonProperty("eventType")
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("initial")
    public String getInitial() {
        return initial;
    }

    @JsonProperty("initial")
    public void setInitial(String initial) {
        this.initial = initial;
    }

    @JsonProperty("release")
    public String getRelease() {
        return release;
    }

    @JsonProperty("release")
    public void setRelease(String release) {
        this.release = release;
    }

    @JsonProperty("structuredText")
    public String getStructuredText() {
        return structuredText;
    }

    @JsonProperty("structuredText")
    public void setStructuredText(String structuredText) {
        this.structuredText = structuredText;
    }
}
