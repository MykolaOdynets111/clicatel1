package datamanager.jacksonschemas.orca;


import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.javafaker.Faker;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventId",
        "providerId",
        "routeId",
        "sourceId",
        "sessionId",
        "content",
        "userInfo",
        "externalReferenceId",
        "history"
})
@Generated("jsonschema2pojo")
public class OrcaEvent {

    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("providerId")
    private String providerId;
    @JsonProperty("routeId")
    private String routeId;
    @JsonProperty("sourceId")
    private String sourceId;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("userInfo")
    private UserInfo userInfo;
    @JsonProperty("externalReferenceId")
    private String externalReferenceId;
    @JsonProperty("history")
    private List<History> history = null;

    public OrcaEvent() {
    }

    public OrcaEvent(String routeId, String messageText){
        Faker faker = new Faker();
        this.setEventId(faker.letterify("AQA???"));
        this.setProviderId("TOUCH");
        this.setRouteId(routeId);
        this.setSourceId("AQA ORCA" +  faker.number().randomNumber(7, false));
        this.setSessionId(faker.letterify("1?????"));
        this.setContent(new Content(messageText));
        this.setUserInfo(null);
        this.setExternalReferenceId(faker.letterify("RefId???"));
    }

    @JsonProperty("eventId")
    public String getEventId() {
        return eventId;
    }

    @JsonProperty("eventId")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @JsonProperty("providerId")
    public String getProviderId() {
        return providerId;
    }

    @JsonProperty("providerId")
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @JsonProperty("routeId")
    public String getRouteId() {
        return routeId;
    }

    @JsonProperty("routeId")
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @JsonProperty("sourceId")
    public String getSourceId() {
        return sourceId;
    }

    @JsonProperty("sourceId")
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("userInfo")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @JsonProperty("userInfo")
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JsonProperty("externalReferenceId")
    public String getExternalReferenceId() {
        return externalReferenceId;
    }

    @JsonProperty("externalReferenceId")
    public void setExternalReferenceId(String externalReferenceId) {
        this.externalReferenceId = externalReferenceId;
    }

    @JsonProperty("history")
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
    }

}