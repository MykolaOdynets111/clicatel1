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


package com.touch.models.touch.tenant;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import com.touch.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * TenantUpdateDtoV5
 */
public class TenantUpdateDtoV5 {
  @SerializedName("tenantOrgName")
  private String tenantOrgName = null;

  @SerializedName("contactEmail")
  private String contactEmail = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("shortDescription")
  private String shortDescription = null;

  @SerializedName("category")
  private String category = null;

  @SerializedName("sessionsCapacity")
  private Integer sessionsCapacity = null;

  public TenantUpdateDtoV5() {
    this.tenantOrgName = "new_test_name"+ StringUtils.generateRandomString(4);
    this.contactEmail = "newFake@fake.perfectial.com";
    this.description = "New test description";
    this.shortDescription = "new test Short";
    this.category = "New Bussines";
    this.sessionsCapacity = 11;
  }

  public TenantUpdateDtoV5(String tenantOrgName, String contactEmail, String description, String shortDescription, String category, Integer sessionsCapacity) {
    this.tenantOrgName = tenantOrgName;
    this.contactEmail = contactEmail;
    this.description = description;
    this.shortDescription = shortDescription;
    this.category = category;
    this.sessionsCapacity = sessionsCapacity;
  }

  public TenantUpdateDtoV5 tenantOrgName(String tenantOrgName) {
    this.tenantOrgName = tenantOrgName;
    return this;
  }

   /**
   * Get tenantOrgName
   * @return tenantOrgName
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getTenantOrgName() {
    return tenantOrgName;
  }

  public void setTenantOrgName(String tenantOrgName) {
    this.tenantOrgName = tenantOrgName;
  }

  public TenantUpdateDtoV5 contactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
    return this;
  }

   /**
   * Get contactEmail
   * @return contactEmail
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public TenantUpdateDtoV5 description(String description) {
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

  public TenantUpdateDtoV5 shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

   /**
   * Get shortDescription
   * @return shortDescription
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public TenantUpdateDtoV5 category(String category) {
    this.category = category;
    return this;
  }

   /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public TenantUpdateDtoV5 sessionsCapacity(Integer sessionsCapacity) {
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
    TenantUpdateDtoV5 tenantUpdateDtoV5 = (TenantUpdateDtoV5) o;
    return Objects.equals(this.tenantOrgName, tenantUpdateDtoV5.tenantOrgName) &&
        Objects.equals(this.contactEmail, tenantUpdateDtoV5.contactEmail) &&
        Objects.equals(this.description, tenantUpdateDtoV5.description) &&
        Objects.equals(this.shortDescription, tenantUpdateDtoV5.shortDescription) &&
        Objects.equals(this.category, tenantUpdateDtoV5.category) &&
        Objects.equals(this.sessionsCapacity, tenantUpdateDtoV5.sessionsCapacity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantOrgName, contactEmail, description, shortDescription, category, sessionsCapacity);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantUpdateDtoV5 {\n");
    
    sb.append("    tenantOrgName: ").append(toIndentedString(tenantOrgName)).append("\n");
    sb.append("    contactEmail: ").append(toIndentedString(contactEmail)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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
