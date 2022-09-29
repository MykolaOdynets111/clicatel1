package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
public class ChatExtension {

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

    public ChatExtension(String label, Optional name, String extensionType){
        this.setType(extensionType);
        this.setLabel(label);
        this.setSupportedChannels(Arrays.asList("ABC"));
        this.setConfig(new Config(name, extensionType));
        this.setPopularityScore(6);
    }
}