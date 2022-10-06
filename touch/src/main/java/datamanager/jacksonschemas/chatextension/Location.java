package datamanager.jacksonschemas.chatextension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import datamanager.jacksonschemas.orca.ExtraFields;
import datamanager.jacksonschemas.orca.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "radius",
        "address",
        "latitude",
        "longitude",
        "identifier",
        "description"
})
@Data @AllArgsConstructor @NoArgsConstructor
public class Location {

    @JsonProperty("name")
    private String name = "WaveBank Sranch";
    @JsonProperty("radius")
    private double radius;
    @JsonProperty("address")
    private String address;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("description")
    private String description;

    public Location(String name){
        this.setName(name);
        this.setRadius(10.2);
        this.setAddress("WaveBank");
        this.setLatitude(49.8511);
        this.setLongitude(21.842);
        this.setIdentifier("WaveBankBranch");
        this.setDescription("Location description");
    }
}