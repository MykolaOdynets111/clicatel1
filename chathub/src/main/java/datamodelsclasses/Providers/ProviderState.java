package datamodelsclasses.Providers;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderState{
    public String id;
    public String name;
    public String isActive;
}