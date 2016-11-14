
package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.clickatell.touch.xmpp.agentmanager.Department;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class TenantResponse {

  private String accountId;
  private String tenantOrgName;
  private String contactEmail;
  private String state;
  private String description;
  private String shortDescription;
  private String tenantJid;
  private String tenantBotJid;
  private String category;
  private List<String> tenantTags = new ArrayList<String>();
  private int sessionsCapacity;
  private List<TenantFaq> tenantFaqs = new ArrayList<TenantFaq>();
  private List<TenantColour> tenantColours = new ArrayList<TenantColour>();
  private List<TenantAddress> tenantAddresses = new ArrayList<TenantAddress>();
  private String id;
  private List<String> tenantResources = new ArrayList<String>();
  private List<Department> departments = new ArrayList<Department>();

  /**
   * No args constructor for use in serialization
   *
   */
  public TenantResponse() {
  }

  /**
   *
   * @param tenantTags
   * @param accountId
   * @param tenantOrgName
   * @param contactEmail
   * @param state
   * @param departments
   * @param tenantResources
   * @param tenantAddresses
   * @param tenantBotJid
   * @param sessionsCapacity
   * @param id
   * @param category
   * @param shortDescription
   * @param description
   * @param tenantJid
   * @param tenantColours
   * @param tenantFaqs
   */
  public TenantResponse(String accountId, String tenantOrgName, String contactEmail, String state, String description, String shortDescription, String tenantJid, String tenantBotJid, String category, List<String> tenantTags, int sessionsCapacity, List<TenantFaq> tenantFaqs, List<TenantColour> tenantColours, List<TenantAddress> tenantAddresses, String id, List<String> tenantResources, List<Department> departments) {
    this.accountId = accountId;
    this.tenantOrgName = tenantOrgName;
    this.contactEmail = contactEmail;
    this.state = state;
    this.description = description;
    this.shortDescription = shortDescription;
    this.tenantJid = tenantJid;
    this.tenantBotJid = tenantBotJid;
    this.category = category;
    this.tenantTags = tenantTags;
    this.sessionsCapacity = sessionsCapacity;
    this.tenantFaqs = tenantFaqs;
    this.tenantColours = tenantColours;
    this.tenantAddresses = tenantAddresses;
    this.id = id;
    this.tenantResources = tenantResources;
    this.departments = departments;
  }

  /**
   *
   * @return
   *     The accountId
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   *
   * @param accountId
   *     The accountId
   */
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public TenantResponse withAccountId(String accountId) {
    this.accountId = accountId;
    return this;
  }

  /**
   *
   * @return
   *     The tenantOrgName
   */
  public String getTenantOrgName() {
    return tenantOrgName;
  }

  /**
   *
   * @param tenantOrgName
   *     The tenantOrgName
   */
  public void setTenantOrgName(String tenantOrgName) {
    this.tenantOrgName = tenantOrgName;
  }

  public TenantResponse withTenantOrgName(String tenantOrgName) {
    this.tenantOrgName = tenantOrgName;
    return this;
  }

  /**
   *
   * @return
   *     The contactEmail
   */
  public String getContactEmail() {
    return contactEmail;
  }

  /**
   *
   * @param contactEmail
   *     The contactEmail
   */
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public TenantResponse withContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
    return this;
  }

  /**
   *
   * @return
   *     The state
   */
  public String getState() {
    return state;
  }

  /**
   *
   * @param state
   *     The state
   */
  public void setState(String state) {
    this.state = state;
  }

  public TenantResponse withState(String state) {
    this.state = state;
    return this;
  }

  /**
   *
   * @return
   *     The description
   */
  public String getDescription() {
    return description;
  }

  /**
   *
   * @param description
   *     The description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public TenantResponse withDescription(String description) {
    this.description = description;
    return this;
  }

  /**
   *
   * @return
   *     The shortDescription
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   *
   * @param shortDescription
   *     The shortDescription
   */
  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public TenantResponse withShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

  /**
   *
   * @return
   *     The tenantJid
   */
  public String getTenantJid() {
    return tenantJid;
  }

  /**
   *
   * @param tenantJid
   *     The tenantJid
   */
  public void setTenantJid(String tenantJid) {
    this.tenantJid = tenantJid;
  }

  public TenantResponse withTenantJid(String tenantJid) {
    this.tenantJid = tenantJid;
    return this;
  }

  /**
   *
   * @return
   *     The tenantBotJid
   */
  public String getTenantBotJid() {
    return tenantBotJid;
  }

  /**
   *
   * @param tenantBotJid
   *     The tenantBotJid
   */
  public void setTenantBotJid(String tenantBotJid) {
    this.tenantBotJid = tenantBotJid;
  }

  public TenantResponse withTenantBotJid(String tenantBotJid) {
    this.tenantBotJid = tenantBotJid;
    return this;
  }

  /**
   *
   * @return
   *     The category
   */
  public String getCategory() {
    return category;
  }

  /**
   *
   * @param category
   *     The category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  public TenantResponse withCategory(String category) {
    this.category = category;
    return this;
  }

  /**
   *
   * @return
   *     The tenantTags
   */
  public List<String> getTenantTags() {
    return tenantTags;
  }

  /**
   *
   * @param tenantTags
   *     The tenantTags
   */
  public void setTenantTags(List<String> tenantTags) {
    this.tenantTags = tenantTags;
  }

  public TenantResponse withTenantTags(List<String> tenantTags) {
    this.tenantTags = tenantTags;
    return this;
  }

  /**
   *
   * @return
   *     The sessionsCapacity
   */
  public int getSessionsCapacity() {
    return sessionsCapacity;
  }

  /**
   *
   * @param sessionsCapacity
   *     The sessionsCapacity
   */
  public void setSessionsCapacity(int sessionsCapacity) {
    this.sessionsCapacity = sessionsCapacity;
  }

  public TenantResponse withSessionsCapacity(int sessionsCapacity) {
    this.sessionsCapacity = sessionsCapacity;
    return this;
  }

  /**
   *
   * @return
   *     The tenantFaqs
   */
  public List<TenantFaq> getTenantFaqs() {
    return tenantFaqs;
  }

  /**
   *
   * @param tenantFaqs
   *     The tenantFaqs
   */
  public void setTenantFaqs(List<TenantFaq> tenantFaqs) {
    this.tenantFaqs = tenantFaqs;
  }

  public TenantResponse withTenantFaqs(List<TenantFaq> tenantFaqs) {
    this.tenantFaqs = tenantFaqs;
    return this;
  }

  /**
   *
   * @return
   *     The tenantColours
   */
  public List<TenantColour> getTenantColours() {
    return tenantColours;
  }

  /**
   *
   * @param tenantColours
   *     The tenantColours
   */
  public void setTenantColours(List<TenantColour> tenantColours) {
    this.tenantColours = tenantColours;
  }

  public TenantResponse withTenantColours(List<TenantColour> tenantColours) {
    this.tenantColours = tenantColours;
    return this;
  }

  /**
   *
   * @return
   *     The tenantAddresses
   */
  public List<TenantAddress> getTenantAddresses() {
    return tenantAddresses;
  }

  /**
   *
   * @param tenantAddresses
   *     The tenantAddresses
   */
  public void setTenantAddresses(List<TenantAddress> tenantAddresses) {
    this.tenantAddresses = tenantAddresses;
  }

  public TenantResponse withTenantAddresses(List<TenantAddress> tenantAddresses) {
    this.tenantAddresses = tenantAddresses;
    return this;
  }

  /**
   *
   * @return
   *     The id
   */
  public String getId() {
    return id;
  }

  /**
   *
   * @param id
   *     The id
   */
  public void setId(String id) {
    this.id = id;
  }

  public TenantResponse withId(String id) {
    this.id = id;
    return this;
  }

  /**
   *
   * @return
   *     The tenantResources
   */
  public List<String> getTenantResources() {
    return tenantResources;
  }

  /**
   *
   * @param tenantResources
   *     The tenantResources
   */
  public void setTenantResources(List<String> tenantResources) {
    this.tenantResources = tenantResources;
  }

  public TenantResponse withTenantResources(List<String> tenantResources) {
    this.tenantResources = tenantResources;
    return this;
  }

  /**
   *
   * @return
   *     The departments
   */
  public List<Department> getDepartments() {
    return departments;
  }

  /**
   *
   * @param departments
   *     The departments
   */
  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }

  public TenantResponse withDepartments(List<Department> departments) {
    this.departments = departments;
    return this;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(accountId).append(tenantOrgName).append(contactEmail).append(state).append(description).append(shortDescription).append(tenantJid).append(tenantBotJid).append(category).append(tenantTags).append(sessionsCapacity).append(tenantFaqs).append(tenantColours).append(tenantAddresses).append(id).append(tenantResources).append(departments).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof TenantResponse) == false) {
      return false;
    }
    TenantResponse rhs = ((TenantResponse) other);
    return new EqualsBuilder().append(accountId, rhs.accountId).append(tenantOrgName, rhs.tenantOrgName).append(contactEmail, rhs.contactEmail).append(state, rhs.state).append(description, rhs.description).append(shortDescription, rhs.shortDescription).append(tenantJid, rhs.tenantJid).append(tenantBotJid, rhs.tenantBotJid).append(category, rhs.category).append(tenantTags, rhs.tenantTags).append(sessionsCapacity, rhs.sessionsCapacity).append(tenantFaqs, rhs.tenantFaqs).append(tenantColours, rhs.tenantColours).append(tenantAddresses, rhs.tenantAddresses).append(id, rhs.id).append(tenantResources, rhs.tenantResources).append(departments, rhs.departments).isEquals();
  }

}
