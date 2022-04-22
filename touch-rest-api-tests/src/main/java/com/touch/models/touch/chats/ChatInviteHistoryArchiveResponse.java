/*
 * 
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.touch.models.touch.chats;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * ChatInviteHistoryResponseV5
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-02-13T08:15:05.879Z")
public class ChatInviteHistoryArchiveResponse {
  @SerializedName("agentNickname")
  private String agentNickname = null;

  @SerializedName("sessionId")
  private String sessionId = null;
  @SerializedName("roomJid")
  private String roomJid = null;

  @SerializedName("userMeta")
  private String userMeta = null;

  @SerializedName("app")
  private String app = null;

  @SerializedName("status")
  private String status = null;
  @SerializedName("clientId")
  private String clientId = null;
  @SerializedName("startedDate")
  private Long startedDate = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("surname")
  private String surname = null;
  @SerializedName("lastMessage")
  private String lastMessage = null;

  public ChatInviteHistoryArchiveResponse agentNickname(String agentNickname) {
    this.agentNickname = agentNickname;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public String getRoomJid() {
    return roomJid;
  }

  public void setRoomJid(String roomJid) {
    this.roomJid = roomJid;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   * Get agentNickname
   * @return agentNickname
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getAgentNickname() {
    return agentNickname;
  }

  public void setAgentNickname(String agentNickname) {
    this.agentNickname = agentNickname;
  }

  public ChatInviteHistoryArchiveResponse sessionId(String sessionId) {
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

  public ChatInviteHistoryArchiveResponse userMeta(String userMeta) {
    this.userMeta = userMeta;
    return this;
  }

   /**
   * Get userMeta
   * @return userMeta
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getUserMeta() {
    return userMeta;
  }

  public void setUserMeta(String userMeta) {
    this.userMeta = userMeta;
  }

  public ChatInviteHistoryArchiveResponse app(String app) {
    this.app = app;
    return this;
  }

   /**
   * Get app
   * @return app
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public ChatInviteHistoryArchiveResponse status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ChatInviteHistoryArchiveResponse startedDate(Long startedDate) {
    this.startedDate = startedDate;
    return this;
  }

   /**
   * Get startedDate
   * @return startedDate
  **/
  @ApiModelProperty(example = "null", value = "")
  public Long getStartedDate() {
    return startedDate;
  }

  public void setStartedDate(Long startedDate) {
    this.startedDate = startedDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatInviteHistoryArchiveResponse chatInviteHistoryResponseV5 = (ChatInviteHistoryArchiveResponse) o;
    return Objects.equals(this.agentNickname, chatInviteHistoryResponseV5.agentNickname) &&
        Objects.equals(this.sessionId, chatInviteHistoryResponseV5.sessionId) &&
        Objects.equals(this.userMeta, chatInviteHistoryResponseV5.userMeta) &&
        Objects.equals(this.app, chatInviteHistoryResponseV5.app) &&
        Objects.equals(this.status, chatInviteHistoryResponseV5.status) &&
        Objects.equals(this.startedDate, chatInviteHistoryResponseV5.startedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentNickname, sessionId, userMeta, app, status, startedDate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatInviteHistoryResponseV5 {\n");
    
    sb.append("    agentNickname: ").append(toIndentedString(agentNickname)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    userMeta: ").append(toIndentedString(userMeta)).append("\n");
    sb.append("    app: ").append(toIndentedString(app)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startedDate: ").append(toIndentedString(startedDate)).append("\n");
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

