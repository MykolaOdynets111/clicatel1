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


package com.touch.models.touch.agent;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


/**
 * AgentMaxChatsResponse
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-10-20T12:15:53.907Z")
public class AgentMaxChatsResponse {
  @SerializedName("maxChats")
  private String maxChats = null;

  public AgentMaxChatsResponse maxChats(String maxChats) {
    this.maxChats = maxChats;
    return this;
  }

   /**
   * Get maxChats
   * @return maxChats
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getMaxChats() {
    return maxChats;
  }

  public void setMaxChats(String maxChats) {
    this.maxChats = maxChats;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgentMaxChatsResponse agentMaxChatsResponse = (AgentMaxChatsResponse) o;
    return Objects.equals(this.maxChats, agentMaxChatsResponse.maxChats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxChats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgentMaxChatsResponse {\n");
    
    sb.append("    maxChats: ").append(toIndentedString(maxChats)).append("\n");
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

