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

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

public class ListChatEventResponseV5 {
  @SerializedName("chatEvents")
  private List<ChatEventResponseV5> chatEvents = new ArrayList<ChatEventResponseV5>();

  public ListChatEventResponseV5 chatEvents(List<ChatEventResponseV5> chatEvents) {
    this.chatEvents = chatEvents;
    return this;
  }

  public ListChatEventResponseV5 addChatEventsItem(ChatEventResponseV5 chatEventsItem) {
    this.chatEvents.add(chatEventsItem);
    return this;
  }

   /**
   * Get chatEvents
   * @return chatEvents
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<ChatEventResponseV5> getChatEvents() {
    return chatEvents;
  }

  public void setChatEvents(List<ChatEventResponseV5> chatEvents) {
    this.chatEvents = chatEvents;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListChatEventResponseV5 listChatEventResponseV5 = (ListChatEventResponseV5) o;
    return Objects.equals(this.chatEvents, listChatEventResponseV5.chatEvents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatEvents);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListChatEventResponseV5 {\n");
    
    sb.append("    chatEvents: ").append(toIndentedString(chatEvents)).append("\n");
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
