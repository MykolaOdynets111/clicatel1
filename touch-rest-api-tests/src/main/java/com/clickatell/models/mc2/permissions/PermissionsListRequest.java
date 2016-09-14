package com.clickatell.models.mc2.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * PermissionsListRequest
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-07-19T14:04:54.682Z")
public class PermissionsListRequest   {
  
  private List<PermissionRequest> permissions = new ArrayList<PermissionRequest>();

  
  /**
   **/
  public PermissionsListRequest permissions(List<PermissionRequest> permissions) {
    this.permissions = permissions;
    return this;
  }
  
  @JsonProperty("permissions")
  public List<PermissionRequest> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<PermissionRequest> permissions) {
    this.permissions = permissions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermissionsListRequest permissionsListRequest = (PermissionsListRequest) o;
    return Objects.equals(this.permissions, permissionsListRequest.permissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(permissions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermissionsListRequest {\n");

    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
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

