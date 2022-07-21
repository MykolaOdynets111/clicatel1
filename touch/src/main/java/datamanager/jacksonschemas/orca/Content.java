package datamanager.jacksonschemas.orca;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.jacksonschemas.orca.event.Event;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "extraFields",
        "event"
})
@Data
public class Content {

    @JsonProperty("extraFields")
    private ExtraFields extraFields;
    @JsonProperty("event")
    private Event event;

    public Content() {
    }

    public Content(String messageText, String name){
        this.setExtraFields(new ExtraFields(name));
        this.setEvent(new Event(messageText));
    }
}