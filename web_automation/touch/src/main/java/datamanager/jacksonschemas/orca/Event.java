package datamanager.jacksonschemas.orca;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventType",
        "text"
})
public class Event {

    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("text")
    private String text;

    public Event (String messageText){
        this.setEventType("TEXT");
        this.setText(messageText);
    }

    @JsonProperty("eventType")
    public String getEventType() {
        return eventType;
    }

    @JsonProperty("eventType")
    public void setEventType(String eventType) {
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
}
