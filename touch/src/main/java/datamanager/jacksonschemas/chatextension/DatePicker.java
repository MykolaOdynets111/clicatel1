package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import datamanager.jacksonschemas.orca.Content;
import datamanager.jacksonschemas.orca.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "label",
        "supportedChannels",
        "config",
        "popularityScore"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePicker {

    @JsonProperty("type")
    private String type;
    @JsonProperty("label")
    private String label = "Schedule Appointment with picker";
    @JsonProperty("supportedChannels")
    private List<String> supportedChannels;
    @JsonProperty("config")
    private Config config;
    @JsonProperty("popularityScore")
    private int popularityScore;

    public DatePicker(String label, String name){
        this.setType("TIME_PICKER");
        this.setLabel(label);
        this.setSupportedChannels(Arrays.asList("ABC"));
        this.setConfig(new Config(name));
        this.setPopularityScore(6);
    }
}