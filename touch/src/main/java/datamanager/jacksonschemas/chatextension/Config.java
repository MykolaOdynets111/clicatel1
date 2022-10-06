package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.jacksonschemas.orca.ExtraFields;
import datamanager.jacksonschemas.orca.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @JsonProperty("groups")
    private List<Groups> groups;
    @JsonProperty("imageRef")
    private String imageRef;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("subTitle")
    private String subTitle;
    @JsonProperty("timeSlots")
    private List<TimeSlots> timeSlots;
    @JsonProperty("header")
    private String header;
    @JsonProperty("description")
    private String description;
    @JsonProperty("postbackData")
    private String postbackData;

    public Config(Optional name, String extensionType){
        switch (extensionType) {
            case "TIME_PICKER":
                this.setTitle("Schedule Appointment");
                this.setImageRef("null");
                this.setLocation(new Location(name.get().toString()));
                this.setSubTitle("Select one of the available time slots for your appointment");
                this.setTimeSlots(Arrays.asList(new TimeSlots()));
                this.setDescription("description");
                this.setPostbackData("timeslot-postback");
                break;
            case "LIST_PICKER":
                this.setGroups(Arrays.asList(new Groups()));
                this.setHeader(name.get().toString());
                this.setImageRef("null");
                this.setDescription("Tap to Choose");
                break;
            default:
                throw new NoSuchElementException("Extension type element: '" + extensionType + "' wasn't found");
        }
    }
}