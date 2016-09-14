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


package com.clickatell.models.mc2.two_way_long.request.reserved;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * ReserveLongNumbersRequest
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-08-25T08:38:12.719Z")
public class ReserveLongNumbersRequest   {
  @SerializedName("numbersToReserve")
  private List<ReserveLongNumberRequest> numbersToReserve = new ArrayList<ReserveLongNumberRequest>();

  public ReserveLongNumbersRequest numbersToReserve(List<ReserveLongNumberRequest> numbersToReserve) {
    this.numbersToReserve = numbersToReserve;
    return this;
  }

  public ReserveLongNumbersRequest addNumbersToReserveItem(ReserveLongNumberRequest numbersToReserveItem) {
    this.numbersToReserve.add(numbersToReserveItem);
    return this;
  }

   /**
   * Get numbersToReserve
   * @return numbersToReserve
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<ReserveLongNumberRequest> getNumbersToReserve() {
    return numbersToReserve;
  }

  public void setNumbersToReserve(List<ReserveLongNumberRequest> numbersToReserve) {
    this.numbersToReserve = numbersToReserve;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReserveLongNumbersRequest reserveLongNumbersRequest = (ReserveLongNumbersRequest) o;
    return Objects.equals(this.numbersToReserve, reserveLongNumbersRequest.numbersToReserve);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numbersToReserve);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReserveLongNumbersRequest {\n");

    sb.append("    numbersToReserve: ").append(toIndentedString(numbersToReserve)).append("\n");
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

