package datamanager.jacksonschemas.orca;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventId",
        "providerId",
        "routeId",
        "sourceId",
        "replyToEventId",
        "sessionId",
        "content",
        "userInfo",
        "externalReferenceId",
        "history"
})
@Data @NoArgsConstructor
public class OrcaEvent {

    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("providerId")
    private String providerId;
    @JsonProperty("routeId")
    private String routeId;
    @JsonProperty("sourceId")
    private String sourceId;
    @JsonProperty("replyToEventId")
    private String replyToEventId;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("userInfo")
    private UserInfo userInfo;
    @JsonProperty("externalReferenceId")
    private String externalReferenceId;
    @JsonProperty("channelId")
    private Integer channelId;
    @JsonProperty("history")
    private List<History> history = null;

    public OrcaEvent(String routeId, String messageText){
        Faker faker = new Faker();
        String name = "AQA ORCA" +  faker.number().randomNumber(7, false);
        this.setEventId(faker.letterify("AQA???????"));
        this.setProviderId("touch");
        this.setRouteId(routeId);
        this.setSourceId(faker.numerify("78#########"));
        this.setSessionId(faker.letterify("1?????"));
        this.setContent(new Content(messageText, name));
        this.setUserInfo(new UserInfo(name));
        this.setExternalReferenceId(faker.letterify("RefId???"));
    }

}