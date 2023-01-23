package datamodelsclasses.providers;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProviderState{
    private String id;
    private String name;
    private String isActive;

    public ProviderState(Map<String,String> parameters) {
        this.id = parameters.get("o.providerId");
        this.name = parameters.get("o.providerName");
        this.isActive = parameters.get("o.status");
    }
}