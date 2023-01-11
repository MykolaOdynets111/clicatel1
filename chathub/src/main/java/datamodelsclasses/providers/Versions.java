package datamodelsclasses.providers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Versions {
        @JsonProperty("id")
        private String id;

        @JsonProperty("version")
        private String version;

        @JsonProperty("latest")
        private boolean latest;

        public Versions(Map<String,String> parameters) {
                this.id = parameters.get("o.providerID");
                this.version = parameters.get("o.providerName");
                this.latest = Boolean.parseBoolean(parameters.get("o.status"));
        }
}
