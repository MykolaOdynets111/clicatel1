package datamanager.jacksonschemas.orca.event;
import lombok.Builder;
import org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

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
public class Event {

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
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("size")
    private long size;
    @JsonProperty("ref")
    private String ref;

    public Event(String messageText){
        this.setEventType(EventType.TEXT);
        this.setText(messageText);
    }

    public Event(File file){
        this.setEventType(EventType.MEDIA);
        this.setCaption(file.getName());
        this.setContentType(URLConnection.guessContentTypeFromName(file.getName()));
        this.setSize(FileUtils.sizeOf(file));
        this.setRef(file.getAbsolutePath());
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

    @JsonProperty("size")
    public long getSize() {
        return size;
    }
    @JsonProperty("size")
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "TextEvent{" +
                "eventType=" + eventType +
                ", text='" + text + '\'' +
                ", initial='" + initial + '\'' +
                ", release='" + release + '\'' +
                ", structuredText='" + structuredText + '\'' +
                ", caption='" + caption + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", ref='" + ref + '\'' +
                '}';
    }
}
