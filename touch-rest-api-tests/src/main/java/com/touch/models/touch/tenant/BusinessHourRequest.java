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
 * BusinessHourDtoV1
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-09-02T11:18:28.144Z")
public class BusinessHourRequest {
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

  public BusinessHourRequest() {
    this.dayOfWeek = DayOfWeekEnum.MONDAY;
    this.startWorkTime = "00:00";
    this.endWorkTime = "23:59";
  }

  public BusinessHourRequest(DayOfWeekEnum dayOfWeek, String startWorkTime, String endWorkTime) {
    this.dayOfWeek = dayOfWeek;
    this.startWorkTime = startWorkTime;
    this.endWorkTime = endWorkTime;
  }

  @SerializedName("dayOfWeek")
  private DayOfWeekEnum dayOfWeek = null;

  @SerializedName("startWorkTime")
  private String startWorkTime = null;

  @SerializedName("endWorkTime")
  private String endWorkTime = null;

  public BusinessHourRequest dayOfWeek(DayOfWeekEnum dayOfWeek) {
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

  public BusinessHourRequest startWorkTime(String startWorkTime) {
    this.startWorkTime = startWorkTime;
    return this;
  }

   /**
   * Get startWorkTime
   * @return startWorkTime
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getStartWorkTime() {
    return startWorkTime;
  }

  public void setStartWorkTime(String startWorkTime) {
    this.startWorkTime = startWorkTime;
  }

  public BusinessHourRequest endWorkTime(String endWorkTime) {
    this.endWorkTime = endWorkTime;
    return this;
  }

   /**
   * Get endWorkTime
   * @return endWorkTime
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getEndWorkTime() {
    return endWorkTime;
  }

  public void setEndWorkTime(String endWorkTime) {
    this.endWorkTime = endWorkTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || (o instanceof BusinessHourRequest||o instanceof BusinessHourResponse||o instanceof BusinessHourDtoIdV5) == false) {
      return false;
    }
    if(o instanceof BusinessHourDtoIdV5){
      BusinessHourDtoIdV5 businessHour = (BusinessHourDtoIdV5) o;
      return Objects.equals(this.dayOfWeek.toString(), businessHour.getDayOfWeek().toString()) &&
              Objects.equals(this.startWorkTime, businessHour.getStartWorkTime().toString()) &&
              Objects.equals(this.endWorkTime, businessHour.getEndWorkTime().toString());
    }
    if(o instanceof BusinessHourResponse){
      BusinessHourResponse businessHour = (BusinessHourResponse) o;
      return Objects.equals(this.dayOfWeek, businessHour.getDayOfWeek()) &&
              Objects.equals(this.startWorkTime, businessHour.getStartWorkTime()) &&
              Objects.equals(this.endWorkTime, businessHour.getEndWorkTime());
    }
    BusinessHourRequest businessHourRequestV1 = (BusinessHourRequest) o;
    return Objects.equals(this.dayOfWeek, businessHourRequestV1.dayOfWeek) &&
        Objects.equals(this.startWorkTime, businessHourRequestV1.startWorkTime) &&
        Objects.equals(this.endWorkTime, businessHourRequestV1.endWorkTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayOfWeek, startWorkTime, endWorkTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BusinessHourDtoV1 {\n");
    
    sb.append("    dayOfWeek: ").append(toIndentedString(dayOfWeek)).append("\n");
    sb.append("    startWorkTime: ").append(toIndentedString(startWorkTime)).append("\n");
    sb.append("    endWorkTime: ").append(toIndentedString(endWorkTime)).append("\n");
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

