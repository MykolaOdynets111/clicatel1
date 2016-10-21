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
import java.util.ArrayList;
import java.util.List;


/**
 * AddressDto
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-09-02T11:18:28.144Z")
public class AddressDto   {
  @SerializedName("firstAddressLine")
  protected String firstAddressLine = null;

  @SerializedName("secondAddressLine")
  protected String secondAddressLine = null;

  @SerializedName("city")
  protected String city = null;

  @SerializedName("stateOrProvince")
  protected String stateOrProvince = null;

  @SerializedName("postalCode")
  protected String postalCode = null;

  @SerializedName("lat")
  protected Float lat = null;

  @SerializedName("lng")
  protected Float lng = null;

  @SerializedName("businessHours")
  protected List<AddressBusinessHourDto> businessHours = new ArrayList<AddressBusinessHourDto>();

  @SerializedName("phones")
  protected List<AddressPhoneDto> phones = new ArrayList<AddressPhoneDto>();

  public AddressDto firstAddressLine(String firstAddressLine) {
    this.firstAddressLine = firstAddressLine;
    return this;
  }

  public AddressDto() {
    this.firstAddressLine = "Test1";
    this.secondAddressLine = "Test1";
    this.city = "New York";
    this.stateOrProvince = "New York";
    this.postalCode = "112347";
    this.lat = 0f;
    this.lng = 0f;
    this.businessHours = new ArrayList<AddressBusinessHourDto>();
    businessHours.add(new AddressBusinessHourDto());
    this.phones = new ArrayList<AddressPhoneDto>();
    phones.add(new AddressPhoneDto());
  }

  public AddressDto(String firstAddressLine, String secondAddressLine, String city, String stateOrProvince, String postalCode, Float lat, Float lng, List<AddressBusinessHourDto> businessHours, List<AddressPhoneDto> phones) {
    this.firstAddressLine = firstAddressLine;
    this.secondAddressLine = secondAddressLine;
    this.city = city;
    this.stateOrProvince = stateOrProvince;
    this.postalCode = postalCode;
    this.lat = lat;
    this.lng = lng;
    this.businessHours = businessHours;
    this.phones = phones;
  }

  /**
   * Get firstAddressLine
   * @return firstAddressLine
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getFirstAddressLine() {
    return firstAddressLine;
  }

  public void setFirstAddressLine(String firstAddressLine) {
    this.firstAddressLine = firstAddressLine;
  }

  public AddressDto secondAddressLine(String secondAddressLine) {
    this.secondAddressLine = secondAddressLine;
    return this;
  }

   /**
   * Get secondAddressLine
   * @return secondAddressLine
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getSecondAddressLine() {
    return secondAddressLine;
  }

  public void setSecondAddressLine(String secondAddressLine) {
    this.secondAddressLine = secondAddressLine;
  }

  public AddressDto city(String city) {
    this.city = city;
    return this;
  }

   /**
   * Get city
   * @return city
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AddressDto stateOrProvince(String stateOrProvince) {
    this.stateOrProvince = stateOrProvince;
    return this;
  }

   /**
   * Get stateOrProvince
   * @return stateOrProvince
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getStateOrProvince() {
    return stateOrProvince;
  }

  public void setStateOrProvince(String stateOrProvince) {
    this.stateOrProvince = stateOrProvince;
  }

  public AddressDto postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Get postalCode
   * @return postalCode
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public AddressDto lat(Float lat) {
    this.lat = lat;
    return this;
  }

   /**
   * Get lat
   * @return lat
  **/
  @ApiModelProperty(example = "null", value = "")
  public Float getLat() {
    return lat;
  }

  public void setLat(Float lat) {
    this.lat = lat;
  }

  public AddressDto lng(Float lng) {
    this.lng = lng;
    return this;
  }

   /**
   * Get lng
   * @return lng
  **/
  @ApiModelProperty(example = "null", value = "")
  public Float getLng() {
    return lng;
  }

  public void setLng(Float lng) {
    this.lng = lng;
  }

  public AddressDto businessHours(List<AddressBusinessHourDto> businessHours) {
    this.businessHours = businessHours;
    return this;
  }

  public AddressDto addBusinessHoursItem(AddressBusinessHourDto businessHoursItem) {
    this.businessHours.add(businessHoursItem);
    return this;
  }

   /**
   * Get businessHours
   * @return businessHours
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<AddressBusinessHourDto> getBusinessHours() {
    return businessHours;
  }

  public void setBusinessHours(List<AddressBusinessHourDto> businessHours) {
    this.businessHours = businessHours;
  }

  public AddressDto phones(List<AddressPhoneDto> phones) {
    this.phones = phones;
    return this;
  }

  public AddressDto addPhonesItem(AddressPhoneDto phonesItem) {
    this.phones.add(phonesItem);
    return this;
  }

   /**
   * Get phones
   * @return phones
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<AddressPhoneDto> getPhones() {
    return phones;
  }

  public void setPhones(List<AddressPhoneDto> phones) {
    this.phones = phones;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDto AddressDto = (AddressDto) o;
    return Objects.equals(this.firstAddressLine, AddressDto.firstAddressLine) &&
        Objects.equals(this.secondAddressLine, AddressDto.secondAddressLine) &&
        Objects.equals(this.city, AddressDto.city) &&
        Objects.equals(this.stateOrProvince, AddressDto.stateOrProvince) &&
        Objects.equals(this.postalCode, AddressDto.postalCode) &&
        Objects.equals(this.lat, AddressDto.lat) &&
        Objects.equals(this.lng, AddressDto.lng) &&
        Objects.equals(this.businessHours, AddressDto.businessHours) &&
        Objects.equals(this.phones, AddressDto.phones);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstAddressLine, secondAddressLine, city, stateOrProvince, postalCode, lat, lng, businessHours, phones);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressDto {\n");
    
    sb.append("    firstAddressLine: ").append(toIndentedString(firstAddressLine)).append("\n");
    sb.append("    secondAddressLine: ").append(toIndentedString(secondAddressLine)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    stateOrProvince: ").append(toIndentedString(stateOrProvince)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    lng: ").append(toIndentedString(lng)).append("\n");
    sb.append("    businessHours: ").append(toIndentedString(businessHours)).append("\n");
    sb.append("    phones: ").append(toIndentedString(phones)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  protected String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

