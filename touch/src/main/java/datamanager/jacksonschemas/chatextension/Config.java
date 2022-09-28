package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.jacksonschemas.orca.ExtraFields;
import datamanager.jacksonschemas.orca.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "imageRef",
        "location",
        "subTitle",
        "timeSlots",
        "description",
        "postbackData"
})
@Data @AllArgsConstructor @NoArgsConstructor
public class Config {

    @JsonProperty("title")
    private String title;
    @JsonProperty("imageRef")
    private String imageRef;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("subTitle")
    private String subTitle;
    @JsonProperty("timeSlots")
    private List<TimeSlots> timeSlots;
    @JsonProperty("description")
    private String description;
    @JsonProperty("postbackData")
    private String postbackData;

    public Config(String name){
        this.setTitle("Schedule Appointment");
        this.setImageRef(null);
        this.setLocation(new Location(name));
        this.setSubTitle("Select one of the available time slots for your appointment");
        this.setTimeSlots(Arrays.asList(new TimeSlots()));
        this.setDescription("description");
        this.setPostbackData("timeslot-postback");
    }
}