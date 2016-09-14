/**
 * No descripton provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 * <p>
 * OpenAPI spec version: 1.0.0
 * <p>
 * <p>
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.clickatell.models.mc2.response.country_phones;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * AllCountryPhoneCodesResponse
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-08-22T14:03:57.945Z")
public class AllCountryPhoneCodesResponse {
    @SerializedName("allCountries")
    private List<PhoneCodesResponse> allCountries = new ArrayList<PhoneCodesResponse>();

    public AllCountryPhoneCodesResponse allCountries(List<PhoneCodesResponse> allCountries) {
        this.allCountries = allCountries;
        return this;
    }

    public AllCountryPhoneCodesResponse addAllCountriesItem(PhoneCodesResponse allCountriesItem) {
        this.allCountries.add(allCountriesItem);
        return this;
    }


    /**
     * Get allCountries
     *
     * @return allCountries
     **/
    @ApiModelProperty(example = "null", value = "")
    public List<PhoneCodesResponse> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(List<PhoneCodesResponse> allCountries) {
        this.allCountries = allCountries;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllCountryPhoneCodesResponse allCountryPhoneCodesResponse = (AllCountryPhoneCodesResponse) o;
        return Objects.equals(this.allCountries, allCountryPhoneCodesResponse.allCountries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allCountries);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AllCountryPhoneCodesResponse {\n");

        sb.append("    allCountries: ").append(toIndentedString(allCountries)).append("\n");
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

