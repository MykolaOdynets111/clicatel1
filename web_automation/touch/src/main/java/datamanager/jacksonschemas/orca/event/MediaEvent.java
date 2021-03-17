package datamanager.jacksonschemas.orca.event;

import org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonInclude;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventType",
        "caption",
        "contentType",
        "size",
        "ref",
})
public class MediaEvent  implements OrcaEventType {

    @JsonProperty("eventType")
    protected EventType eventType;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("size")
    private long size;
    @JsonProperty("ref")
    private String ref;

    public MediaEvent() {
    }

    public MediaEvent(File file){
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
                ", caption='" + caption + '\'' +
                ", initial='" + contentType + '\'' +
                ", release='" + ref + '\'' +
                '}';
    }

    @Override
    public String getText() {
        return null;
    }
    @Override
    public void setText(String message) {
    }
}
