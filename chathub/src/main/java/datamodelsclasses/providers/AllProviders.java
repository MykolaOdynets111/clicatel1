package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "logoUrl",
        "description",
        "moreInfoUrl",
        "versions",
        "isAdded"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllProviders {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("logoUrl")
    private String logoUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("moreInfoUrl")
    private String moreInfoUrl;

    @JsonProperty("versions")
    private List<Versions> versions = Arrays.asList(new Versions());

    @JsonProperty("isAdded")
    public String isAdded;

    public AllProviders(String id, String name, String logoUrl, String description, String moreInfoUrl, String vid, String version, String latest, String isAdded) {
        this.setId(id);
        this.setName(name);
        this.setLogoUrl(logoUrl);
        this.setDescription(description);
        this.setMoreInfoUrl(moreInfoUrl);
        this.setIsAdded(isAdded);
        List<Versions> versions = Arrays.asList(new Versions(vid, version, latest));
        this.setVersions(versions);
    }

    public AllProviders(String id, String name, String logoUrl, String description, String moreInfoUrl, String vid, String version, String latest) {
        this.setId(id);
        this.setName(name);
        this.setLogoUrl(logoUrl);
        this.setDescription(description);
        this.setMoreInfoUrl(moreInfoUrl);
        List<Versions> versions = Arrays.asList(new Versions(vid, version, latest));
        this.setVersions(versions);
    }
}

