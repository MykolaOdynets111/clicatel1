package datamanager.jacksonschemas.orca.event;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URLConnection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventType",
        "text",
        "initial",
        "release",
        "structuredText",
        "caption",
        "contentType",
        "size",
        "ref"
})
@Generated("jsonschema2pojo")
public class Event {

    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("text")
    private String text;
    @JsonProperty("initial")
    private String initial;
    @JsonProperty("release")
    private String release;
    @JsonProperty("structuredText")
    private String structuredText;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("contentType")
    private String contentType;
//    @JsonProperty("size")
//    private long size;
    @JsonProperty("ref")
    private String ref;


    public Event(String messageText){
        this.setEventType(EventType.TEXT.toString());
        this.setText(messageText);
    }

    public Event(File file){
        this.setEventType(EventType.MEDIA.toString());
        this.setCaption(file.getName());
        this.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
//        this.setSize(FileUtils.sizeOf(file));
        this.setRef(file.getAbsolutePath());
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

    @JsonProperty("caption")
    public String getCaption() {
        return contentType;
    }

    @JsonProperty("caption")
    public void setCaption(String text) {
        this.caption = text;
    }

    @JsonProperty("contentType")
    public String getContentType() {
        return contentType;
    }

    @JsonProperty("contentType")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty("ref")
    public String getRef() {
        return ref;
    }

    @JsonProperty("ref")
    public void setRef(String ref) {
        this.ref = ref;
    }

//    @JsonProperty("size")
//    public long getSize() {
//        return size;
//    }
//    @JsonProperty("size")
//    public void setSize(long size) {
//        this.size = size;
//    }
}
