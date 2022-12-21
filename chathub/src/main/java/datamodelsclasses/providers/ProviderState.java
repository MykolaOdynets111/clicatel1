package datamodelsclasses.providers;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProviderState{
    public String id;
    public String name;
    public String isActive;

    public ProviderState(Map<String,String> parameters) {
        this.id = parameters.get("providerID");
        this.name = parameters.get("providerName");
        this.isActive = parameters.get("status");
    }
}