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

import java.time.LocalTime;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

/**
 * BusinessHourResponse
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-12-14T07:50:07.337Z")
public class BusinessHourResponse {
  @SerializedName("id")
  private String id = null;

  /**
   * Gets or Sets dayOfWeek
   */
  public enum DayOfWeekEnum {
    @SerializedName("MONDAY")
    MONDAY("MONDAY"),
    
    @SerializedName("TUESDAY")
    TUESDAY("TUESDAY"),
    
    @SerializedName("WEDNESDAY")
    WEDNESDAY("WEDNESDAY"),
    
    @SerializedName("THURSDAY")
    THURSDAY("THURSDAY"),
    
    @SerializedName("FRIDAY")
    FRIDAY("FRIDAY"),
    
    @SerializedName("SATURDAY")
    SATURDAY("SATURDAY"),
    
    @SerializedName("SUNDAY")
    SUNDAY("SUNDAY");

    private String value;

    DayOfWeekEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("dayOfWeek")
  private DayOfWeekEnum dayOfWeek = null;

  @SerializedName("startWorkTime")
  private LocalTime startWorkTime = null;

  @SerializedName("endWorkTime")
  private LocalTime endWorkTime = null;

  @SerializedName("tenantId")
  private String tenantId = null;

  public BusinessHourResponse id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BusinessHourResponse dayOfWeek(DayOfWeekEnum dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
    return this;
  }

   /**
   * Get dayOfWeek
   * @return dayOfWeek
  **/
  @ApiModelProperty(example = "null", value = "")
  public DayOfWeekEnum getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public BusinessHourResponse startWorkTime(LocalTime startWorkTime) {
    this.startWorkTime = startWorkTime;
    return this;
  }

   /**
   * Get startWorkTime
   * @return startWorkTime
  **/
  @ApiModelProperty(example = "null", value = "")
  public LocalTime getStartWorkTime() {
    return startWorkTime;
  }

  public void setStartWorkTime(LocalTime startWorkTime) {
    this.startWorkTime = startWorkTime;
  }

  public BusinessHourResponse endWorkTime(LocalTime endWorkTime) {
    this.endWorkTime = endWorkTime;
    return this;
  }

   /**
   * Get endWorkTime
   * @return endWorkTime
  **/
  @ApiModelProperty(example = "null", value = "")
  public LocalTime getEndWorkTime() {
    return endWorkTime;
  }

  public void setEndWorkTime(LocalTime endWorkTime) {
    this.endWorkTime = endWorkTime;
  }

  public BusinessHourResponse tenantId(String tenantId) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BusinessHourResponse businessHourResponse = (BusinessHourResponse) o;
    return Objects.equals(this.id, businessHourResponse.id) &&
        Objects.equals(this.dayOfWeek, businessHourResponse.dayOfWeek) &&
        Objects.equals(this.startWorkTime, businessHourResponse.startWorkTime) &&
        Objects.equals(this.endWorkTime, businessHourResponse.endWorkTime) &&
        Objects.equals(this.tenantId, businessHourResponse.tenantId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dayOfWeek, startWorkTime, endWorkTime, tenantId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BusinessHourResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dayOfWeek: ").append(toIndentedString(dayOfWeek)).append("\n");
    sb.append("    startWorkTime: ").append(toIndentedString(startWorkTime)).append("\n");
    sb.append("    endWorkTime: ").append(toIndentedString(endWorkTime)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
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

