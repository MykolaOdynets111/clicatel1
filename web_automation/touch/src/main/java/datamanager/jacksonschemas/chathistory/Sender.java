
package datamanager.jacksonschemas.chathistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type"
})
public class Sender {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;

    public Sender(String id, String type){
        this.id = id;
        this.type = type;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
