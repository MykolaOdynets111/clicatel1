package datamanager.jacksonschemas.orca.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.annotation.Generated;
import java.io.File;
import java.net.URLConnection;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "text",
        "eventType",
        "initial",
        "release",
        "structuredText",
        "caption",
        "contentType",
        "latitude",
        "longitude",
        "address",
        "name",
        "eventType",
//        "size",
        "ref"
})
@Setter @Getter @ToString @Builder @AllArgsConstructor
@Generated("jsonschema2pojo")
public class Event {

    @JsonProperty("text")
    private String text;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("initial")
    private Object initial;
    @JsonProperty("release")
    private Object release;
    @JsonProperty("structuredText")
    private Object structuredText;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("address")
    private String address;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("size")
//    private long size;
    @JsonProperty("ref")
    private String ref;

    public Event(){
    }

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

}
