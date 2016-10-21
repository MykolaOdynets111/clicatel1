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
 * Licensed under the Apache License, VersionFlow 2.0 (the "License");
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


package com.touch.models.touch.tenant;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


/**
 * DepartmentDto
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-09-06T15:43:34.475Z")
public class DepartmentDto   {

  public DepartmentDto() {
  }

  public DepartmentDto(String tenantId, String name, String description, Integer sessionsCapacity) {
    this.tenantId = tenantId;
    this.name = name;
    this.description = description;
    this.sessionsCapacity = sessionsCapacity;
  }

  @SerializedName("tenantId")
  private String tenantId = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("sessionsCapacity")
  private Integer sessionsCapacity = null;

  public DepartmentDto tenantId(String tenantId) {
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

  public DepartmentDto name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DepartmentDto description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DepartmentDto sessionsCapacity(Integer sessionsCapacity) {
    this.sessionsCapacity = sessionsCapacity;
    return this;
  }

   /**
   * Get sessionsCapacity
   * @return sessionsCapacity
  **/
  @ApiModelProperty(example = "null", value = "")
  public Integer getSessionsCapacity() {
    return sessionsCapacity;
  }

  public void setSessionsCapacity(Integer sessionsCapacity) {
    this.sessionsCapacity = sessionsCapacity;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentDto departmentDto = (DepartmentDto) o;
    return Objects.equals(this.tenantId, departmentDto.tenantId) &&
        Objects.equals(this.name, departmentDto.name) &&
        Objects.equals(this.description, departmentDto.description) &&
        Objects.equals(this.sessionsCapacity, departmentDto.sessionsCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, name, description, sessionsCapacity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepartmentDto {\n");
    
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    sessionsCapacity: ").append(toIndentedString(sessionsCapacity)).append("\n");
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

