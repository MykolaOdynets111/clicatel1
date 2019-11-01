
package datamanager.jacksonschemas.chathistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "sender",
    "channel",
    "replyId",
    "text"
})
public class Message_ {

    @JsonProperty("type")
    private String type;
    @JsonProperty("sender")
    private Sender sender;
    @JsonProperty("channel")
    private Channel channel;
    @JsonProperty("replyId")
    private Object replyId;
    @JsonProperty("text")
    private String text;

    public Message_(String text){
        this.type = "PLAIN_MESSAGE";
        this.text = text;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("sender")
    public Sender getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public Message_ setSender(Sender sender) {
        this.sender = sender;
        return this;
    }

    @JsonProperty("channel")
    public Channel getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public Message_ setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    @JsonProperty("replyId")
    public Object getReplyId() {
        return replyId;
    }

    @JsonProperty("replyId")
    public void setReplyId(Object replyId) {
        this.replyId = replyId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message_{" +
                "type='" + type + '\'' +
                ", sender=" + sender +
                ", channel=" + channel +
                ", replyId=" + replyId +
                ", text='" + text + '\'' +
                '}';
    }
}
