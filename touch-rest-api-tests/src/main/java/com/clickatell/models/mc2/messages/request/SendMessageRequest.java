package com.clickatell.models.mc2.messages.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * SendMessageRequest
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-07-19T14:04:54.682Z")
public class SendMessageRequest {

    private String content = null;
    private List<String> to = new ArrayList<String>();
    private String from = null;
    private Boolean binary = false;
    private String clientMessageId = null;
    private String scheduledDeliveryTime = null;
    private String userDataHeader = null;
    private Integer validityPeriod = null;
    private String charset;


    /**
     **/
    public SendMessageRequest content(String content) {
        this.content = content;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    /**
     **/
    public SendMessageRequest to(List<String> to) {
        this.to = to;
        return this;
    }

    public SendMessageRequest to(String phone) {
        this.to.add(phone);
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("to")
    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }


    /**
     **/
    public SendMessageRequest from(String from) {
        this.from = from;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    /**
     **/
    public SendMessageRequest binary(Boolean binary) {
        this.binary = binary;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("binary")
    public Boolean getBinary() {
        return binary;
    }

    public void setBinary(Boolean binary) {
        this.binary = binary;
    }


    /**
     **/
    public SendMessageRequest clientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("clientMessageId")
    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }


    /**
     **/
    public SendMessageRequest scheduledDeliveryTime(String scheduledDeliveryTime) {
        this.scheduledDeliveryTime = scheduledDeliveryTime;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("scheduledDeliveryTime")
    public String getScheduledDeliveryTime() {
        return scheduledDeliveryTime;
    }

    public void setScheduledDeliveryTime(String scheduledDeliveryTime) {
        this.scheduledDeliveryTime = scheduledDeliveryTime;
    }


    /**
     **/
    public SendMessageRequest userDataHeader(String userDataHeader) {
        this.userDataHeader = userDataHeader;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("userDataHeader")
    public String getUserDataHeader() {
        return userDataHeader;
    }

    public void setUserDataHeader(String userDataHeader) {
        this.userDataHeader = userDataHeader;
    }


    /**
     **/
    public SendMessageRequest validityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("validityPeriod")
    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }


    /**
     **/
    public SendMessageRequest charset(String charset) {
        this.charset = charset;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    @JsonProperty("charset")
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SendMessageRequest sendMessageRequest = (SendMessageRequest) o;
        return Objects.equals(this.content, sendMessageRequest.content) &&
                Objects.equals(this.to, sendMessageRequest.to) &&
                Objects.equals(this.from, sendMessageRequest.from) &&
                Objects.equals(this.binary, sendMessageRequest.binary) &&
                Objects.equals(this.clientMessageId, sendMessageRequest.clientMessageId) &&
                Objects.equals(this.scheduledDeliveryTime, sendMessageRequest.scheduledDeliveryTime) &&
                Objects.equals(this.userDataHeader, sendMessageRequest.userDataHeader) &&
                Objects.equals(this.validityPeriod, sendMessageRequest.validityPeriod) &&
                Objects.equals(this.charset, sendMessageRequest.charset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, to, from, binary, clientMessageId, scheduledDeliveryTime, userDataHeader, validityPeriod, charset);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SendMessageRequest {\n");

        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    to: ").append(toIndentedString(to)).append("\n");
        sb.append("    from: ").append(toIndentedString(from)).append("\n");
        sb.append("    binary: ").append(toIndentedString(binary)).append("\n");
        sb.append("    clientMessageId: ").append(toIndentedString(clientMessageId)).append("\n");
        sb.append("    scheduledDeliveryTime: ").append(toIndentedString(scheduledDeliveryTime)).append("\n");
        sb.append("    userDataHeader: ").append(toIndentedString(userDataHeader)).append("\n");
        sb.append("    validityPeriod: ").append(toIndentedString(validityPeriod)).append("\n");
        sb.append("    charset: ").append(toIndentedString(charset)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

