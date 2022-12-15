package pojoClasses.Providers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
}
