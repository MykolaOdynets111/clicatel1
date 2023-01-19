package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "latest",
})
@Data
@NoArgsConstructor
public class Versions {
        @JsonProperty("id")
        private String id;

        @JsonProperty("version")
        private String version;

        @JsonProperty("latest")
        private String latest;

        public Versions(String id, String version, String latest) {
                this.setId(id);
                this.setVersion(version);
                this.setLatest(latest);
        }
}

