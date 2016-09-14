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

import java.util.Objects;


/**
 * ReserveLongNumberRequest
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-08-25T08:38:12.719Z")
public class ReserveLongNumberRequest   {
  @SerializedName("numberId")
  private String numberId = null;

  @SerializedName("productId")
  private Long productId = null;

  @SerializedName("recurringPriceId")
  private Long recurringPriceId = null;

  @SerializedName("countryId")
  private Integer countryId = null;

  public ReserveLongNumberRequest numberId(String numberId) {
    this.numberId = numberId;
    return this;
  }

   /**
   * Get numberId
   * @return numberId
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getNumberId() {
    return numberId;
  }

  public void setNumberId(String numberId) {
    this.numberId = numberId;
  }

  public ReserveLongNumberRequest productId(Long productId) {
    this.productId = productId;
    return this;
  }

   /**
   * Get productId
   * @return productId
  **/
  @ApiModelProperty(example = "null", value = "")
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public ReserveLongNumberRequest recurringPriceId(Long recurringPriceId) {
    this.recurringPriceId = recurringPriceId;
    return this;
  }

   /**
   * Get recurringPriceId
   * @return recurringPriceId
  **/
  @ApiModelProperty(example = "null", value = "")
  public Long getRecurringPriceId() {
    return recurringPriceId;
  }

  public void setRecurringPriceId(Long recurringPriceId) {
    this.recurringPriceId = recurringPriceId;
  }

  public ReserveLongNumberRequest countryId(Integer countryId) {
    this.countryId = countryId;
    return this;
  }

   /**
   * Get countryId
   * @return countryId
  **/
  @ApiModelProperty(example = "null", value = "")
  public Integer getCountryId() {
    return countryId;
  }

  public void setCountryId(Integer countryId) {
    this.countryId = countryId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReserveLongNumberRequest reserveLongNumberRequest = (ReserveLongNumberRequest) o;
    return Objects.equals(this.numberId, reserveLongNumberRequest.numberId) &&
        Objects.equals(this.productId, reserveLongNumberRequest.productId) &&
        Objects.equals(this.recurringPriceId, reserveLongNumberRequest.recurringPriceId) &&
        Objects.equals(this.countryId, reserveLongNumberRequest.countryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberId, productId, recurringPriceId, countryId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReserveLongNumberRequest {\n");

    sb.append("    numberId: ").append(toIndentedString(numberId)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    recurringPriceId: ").append(toIndentedString(recurringPriceId)).append("\n");
    sb.append("    countryId: ").append(toIndentedString(countryId)).append("\n");
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

