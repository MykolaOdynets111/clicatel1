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


package com.touch.models.touch.cards;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class PlatformDtoV4 {
    @SerializedName("platform")
    private String platform = null;

    @SerializedName("version")
    private String version = null;

    @SerializedName("path")
    private String path = null;

    @SerializedName("width")
    private String width = null;

    @SerializedName("height")
    private String height = null;

    @SerializedName("description")
    private String description = null;

    public PlatformDtoV4() {
    }

    public PlatformDtoV4(String platform, String version, String path, String width, String height) {
        this.platform = platform;
        this.version = version;
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public PlatformDtoV4 platform(String platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Get platform
     *
     * @return platform
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public PlatformDtoV4 version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get version
     *
     * @return version
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PlatformDtoV4 path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Get path
     *
     * @return path
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PlatformDtoV4 width(String width) {
        this.width = width;
        return this;
    }

    /**
     * Get width
     *
     * @return width
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public PlatformDtoV4 height(String height) {
        this.height = height;
        return this;
    }

    /**
     * Get height
     *
     * @return height
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public PlatformDtoV4 description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    @ApiModelProperty(example = "null", value = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlatformDtoV4 platformDtoV3 = (PlatformDtoV4) o;
        return Objects.equals(this.platform, platformDtoV3.platform) &&
                Objects.equals(this.version, platformDtoV3.version) &&
                Objects.equals(this.path, platformDtoV3.path) &&
                Objects.equals(this.width, platformDtoV3.width) &&
                Objects.equals(this.description, platformDtoV3.description) &&
                Objects.equals(this.height, platformDtoV3.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, version, path, width, height, description);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PlatformDtoV3 {\n");

        sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    path: ").append(toIndentedString(path)).append("\n");
        sb.append("    width: ").append(toIndentedString(width)).append("\n");
        sb.append("    height: ").append(toIndentedString(height)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

