/**
 * 
 * No descripton provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.touch.models.touch.chats;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


/**
 * ChatHistoryRecordResponse
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-20T12:15:53.907Z")
public class ChatHistoryRecordResponse {
  @SerializedName("sessionId")
  private String sessionId = null;

  @SerializedName("messageTime")
  private Long messageTime = null;

  @SerializedName("messageText")
  private String messageText = null;

  /**
   * Gets or Sets messageType
   */
  public enum MessageTypeEnum {
    @SerializedName("BOT_MESSAGE")
    BOT_MESSAGE("BOT_MESSAGE"),
    
    @SerializedName("CLIENT_MESSAGE")
    CLIENT_MESSAGE("CLIENT_MESSAGE"),
    
    @SerializedName("AGENT_MESSAGE")
    AGENT_MESSAGE("AGENT_MESSAGE");

    private String value;

    MessageTypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("messageType")
  private MessageTypeEnum messageType = null;

  @SerializedName("tenantId")
  private String tenantId = null;

  @SerializedName("clientId")
  private String clientId = null;

  public ChatHistoryRecordResponse sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

   /**
   * Get sessionId
   * @return sessionId
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public ChatHistoryRecordResponse messageTime(Long messageTime) {
    this.messageTime = messageTime;
    return this;
  }

   /**
   * Get messageTime
   * @return messageTime
  **/
  @ApiModelProperty(example = "null", value = "")
  public Long getMessageTime() {
    return messageTime;
  }

  public void setMessageTime(Long messageTime) {
    this.messageTime = messageTime;
  }

  public ChatHistoryRecordResponse messageText(String messageText) {
    this.messageText = messageText;
    return this;
  }

   /**
   * Get messageText
   * @return messageText
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public ChatHistoryRecordResponse messageType(MessageTypeEnum messageType) {
    this.messageType = messageType;
    return this;
  }

   /**
   * Get messageType
   * @return messageType
  **/
  @ApiModelProperty(example = "null", value = "")
  public MessageTypeEnum getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageTypeEnum messageType) {
    this.messageType = messageType;
  }

  public ChatHistoryRecordResponse tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * Get tenantId
   * @return tenantId
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public ChatHistoryRecordResponse clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

   /**
   * Get clientId
   * @return clientId
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatHistoryRecordResponse chatHistoryRecordResponse = (ChatHistoryRecordResponse) o;
    return Objects.equals(this.sessionId, chatHistoryRecordResponse.sessionId) &&
        Objects.equals(this.messageTime, chatHistoryRecordResponse.messageTime) &&
        Objects.equals(this.messageText, chatHistoryRecordResponse.messageText) &&
        Objects.equals(this.messageType, chatHistoryRecordResponse.messageType) &&
        Objects.equals(this.tenantId, chatHistoryRecordResponse.tenantId) &&
        Objects.equals(this.clientId, chatHistoryRecordResponse.clientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionId, messageTime, messageText, messageType, tenantId, clientId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatHistoryRecordResponse {\n");
    
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    messageTime: ").append(toIndentedString(messageTime)).append("\n");
    sb.append("    messageText: ").append(toIndentedString(messageText)).append("\n");
    sb.append("    messageType: ").append(toIndentedString(messageType)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
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

