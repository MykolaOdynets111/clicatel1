

package com.touch.models.touch.tenant;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import com.touch.models.touch.department.DepartmentResponse;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * TenantResponseV5
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-12-06T14:49:12.583Z")
public class TenantResponseV5 {
  @SerializedName("accountId")
  private String accountId = null;

  @SerializedName("tenantOrgName")
  private String tenantOrgName = null;

  @SerializedName("tenantJid")
  private String tenantJid = null;

  @SerializedName("contactEmail")
  private String contactEmail = null;

  /**
   * Gets or Sets state
   */
  public enum StateEnum {
    @SerializedName("ACTIVE")
    ACTIVE("ACTIVE"),
    
    @SerializedName("INACTIVE")
    INACTIVE("INACTIVE");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @SerializedName("state")
  private StateEnum state = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("shortDescription")
  private String shortDescription = null;

  @SerializedName("category")
  private String category = null;

  @SerializedName("tenantTags")
  private List<String> tenantTags = new ArrayList<String>();

  @SerializedName("sessionsCapacity")
  private Integer sessionsCapacity = null;

  @SerializedName("tenantFaqs")
  private List<TenantFaqDto> tenantFaqs = new ArrayList<TenantFaqDto>();

  @SerializedName("tenantColours")
  private List<TenantColourDto> tenantColours = new ArrayList<TenantColourDto>();

  @SerializedName("tenantAddresses")
  private List<AddressResponse> tenantAddresses = new ArrayList<AddressResponse>();

  @SerializedName("id")
  private String id = null;

  @SerializedName("tenantResources")
  private List<String> tenantResources = new ArrayList<String>();

  @SerializedName("departments")
  private List<DepartmentResponse> departments = new ArrayList<DepartmentResponse>();

  public TenantResponseV5 accountId(String accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * Get accountId
   * @return accountId
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public TenantResponseV5 tenantOrgName(String tenantOrgName) {
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

  public TenantResponseV5 tenantJid(String tenantJid) {
    this.tenantJid = tenantJid;
    return this;
  }

  /**
   * Get tenantJid
   * @return tenantJid
   **/
  @ApiModelProperty(example = "null", value = "")
  public String getTenantJid() {
    return tenantJid;
  }

  public void setTenantJid(String tenantJid) {
    this.tenantJid = tenantJid;
  }


  public TenantResponseV5 contactEmail(String contactEmail) {
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

  public TenantResponseV5 state(StateEnum state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(example = "null", required = true, value = "")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public TenantResponseV5 description(String description) {
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

  public TenantResponseV5 shortDescription(String shortDescription) {
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

  public TenantResponseV5 category(String category) {
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

  public TenantResponseV5 tenantTags(List<String> tenantTags) {
    this.tenantTags = tenantTags;
    return this;
  }

  public TenantResponseV5 addTenantTagsItem(String tenantTagsItem) {
    this.tenantTags.add(tenantTagsItem);
    return this;
  }

   /**
   * Get tenantTags
   * @return tenantTags
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<String> getTenantTags() {
    return tenantTags;
  }

  public void setTenantTags(List<String> tenantTags) {
    this.tenantTags = tenantTags;
  }

  public TenantResponseV5 sessionsCapacity(Integer sessionsCapacity) {
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

  public TenantResponseV5 tenantFaqs(List<TenantFaqDto> tenantFaqs) {
    this.tenantFaqs = tenantFaqs;
    return this;
  }

  public TenantResponseV5 addTenantFaqsItem(TenantFaqDto tenantFaqsItem) {
    this.tenantFaqs.add(tenantFaqsItem);
    return this;
  }

   /**
   * Get tenantFaqs
   * @return tenantFaqs
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<TenantFaqDto> getTenantFaqs() {
    return tenantFaqs;
  }

  public void setTenantFaqs(List<TenantFaqDto> tenantFaqs) {
    this.tenantFaqs = tenantFaqs;
  }

  public TenantResponseV5 tenantColours(List<TenantColourDto> tenantColours) {
    this.tenantColours = tenantColours;
    return this;
  }

  public TenantResponseV5 addTenantColoursItem(TenantColourDto tenantColoursItem) {
    this.tenantColours.add(tenantColoursItem);
    return this;
  }

   /**
   * Get tenantColours
   * @return tenantColours
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<TenantColourDto> getTenantColours() {
    return tenantColours;
  }

  public void setTenantColours(List<TenantColourDto> tenantColours) {
    this.tenantColours = tenantColours;
  }

  public TenantResponseV5 tenantAddresses(List<AddressResponse> tenantAddresses) {
    this.tenantAddresses = tenantAddresses;
    return this;
  }

  public TenantResponseV5 addTenantAddressesItem(AddressResponse tenantAddressesItem) {
    this.tenantAddresses.add(tenantAddressesItem);
    return this;
  }

   /**
   * Get tenantAddresses
   * @return tenantAddresses
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<AddressResponse> getTenantAddresses() {
    return tenantAddresses;
  }

  public void setTenantAddresses(List<AddressResponse> tenantAddresses) {
    this.tenantAddresses = tenantAddresses;
  }

  public TenantResponseV5 id(String id) {
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

  public TenantResponseV5 tenantResources(List<String> tenantResources) {
    this.tenantResources = tenantResources;
    return this;
  }

  public TenantResponseV5 addTenantResourcesItem(String tenantResourcesItem) {
    this.tenantResources.add(tenantResourcesItem);
    return this;
  }

   /**
   * Get tenantResources
   * @return tenantResources
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<String> getTenantResources() {
    return tenantResources;
  }

  public void setTenantResources(List<String> tenantResources) {
    this.tenantResources = tenantResources;
  }

  public TenantResponseV5 departments(List<DepartmentResponse> departments) {
    this.departments = departments;
    return this;
  }

  public TenantResponseV5 addDepartmentsItem(DepartmentResponse departmentsItem) {
    this.departments.add(departmentsItem);
    return this;
  }

   /**
   * Get departments
   * @return departments
  **/
  @ApiModelProperty(example = "null", value = "")
  public List<DepartmentResponse> getDepartments() {
    return departments;
  }

  public void setDepartments(List<DepartmentResponse> departments) {
    this.departments = departments;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }


    if (o == null || (o instanceof TenantRequest||o instanceof TenantResponseV5) == false) {
      return false;
    }
    if (o instanceof TenantRequest){
      TenantRequest rhs = ((TenantRequest) o);
      return Objects.equals(this.accountId, rhs.getAccountId()) &&
              Objects.equals(this.tenantOrgName, rhs.getTenantOrgName()) &&
              Objects.equals(this.contactEmail, rhs.getContactEmail()) &&
              Objects.equals(this.description, rhs.getDescription()) &&
              Objects.equals(this.shortDescription, rhs.getShortDescription()) &&
              Objects.equals(this.category, rhs.getCategory()) &&
              Objects.equals(this.sessionsCapacity, rhs.getSessionsCapacity());

    }
    TenantResponseV5 tenantResponseV5 = (TenantResponseV5) o;
    return Objects.equals(this.accountId, tenantResponseV5.accountId) &&
        Objects.equals(this.tenantOrgName, tenantResponseV5.tenantOrgName) &&
        Objects.equals(this.contactEmail, tenantResponseV5.contactEmail) &&
        Objects.equals(this.state, tenantResponseV5.state) &&
        Objects.equals(this.description, tenantResponseV5.description) &&
        Objects.equals(this.shortDescription, tenantResponseV5.shortDescription) &&
        Objects.equals(this.category, tenantResponseV5.category) &&
        Objects.equals(this.tenantTags, tenantResponseV5.tenantTags) &&
        Objects.equals(this.sessionsCapacity, tenantResponseV5.sessionsCapacity) &&
        Objects.equals(this.tenantFaqs, tenantResponseV5.tenantFaqs) &&
        Objects.equals(this.tenantColours, tenantResponseV5.tenantColours) &&
        Objects.equals(this.tenantAddresses, tenantResponseV5.tenantAddresses) &&
        Objects.equals(this.id, tenantResponseV5.id) &&
        Objects.equals(this.tenantResources, tenantResponseV5.tenantResources) &&
        Objects.equals(this.departments, tenantResponseV5.departments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, tenantOrgName, contactEmail, state, description, shortDescription, category, tenantTags, sessionsCapacity, tenantFaqs, tenantColours, tenantAddresses, id, tenantResources, departments);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantResponseV5 {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    tenantOrgName: ").append(toIndentedString(tenantOrgName)).append("\n");
    sb.append("    contactEmail: ").append(toIndentedString(contactEmail)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    tenantTags: ").append(toIndentedString(tenantTags)).append("\n");
    sb.append("    sessionsCapacity: ").append(toIndentedString(sessionsCapacity)).append("\n");
    sb.append("    tenantFaqs: ").append(toIndentedString(tenantFaqs)).append("\n");
    sb.append("    tenantColours: ").append(toIndentedString(tenantColours)).append("\n");
    sb.append("    tenantAddresses: ").append(toIndentedString(tenantAddresses)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantResources: ").append(toIndentedString(tenantResources)).append("\n");
    sb.append("    departments: ").append(toIndentedString(departments)).append("\n");
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

