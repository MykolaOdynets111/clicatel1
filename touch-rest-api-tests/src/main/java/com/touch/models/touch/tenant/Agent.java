
package com.touch.models.touch.tenant;

import javax.annotation.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Agent {

    private String id;
    private int createdDate;
    private int modifiedDate;
    private String tenantId;
    private String name;
    private String surname;
    private String email;
    private String agentJid;
    private int maxChats;
    private String imageUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Agent() {

    }

    /**
     * 
     * @param tenantId
     * @param id
     * @param agentJid
     * @param imageUrl
     * @param email
     * @param maxChats
     * @param name
     * @param surname
     * @param createdDate
     * @param modifiedDate
     */
    public Agent(String id, int createdDate, int modifiedDate, String tenantId, String name, String surname, String email, String agentJid, int maxChats, String imageUrl) {
        this.id = id;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.tenantId = tenantId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.agentJid = agentJid;
        this.maxChats = maxChats;
        this.imageUrl = imageUrl;
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

    public Agent withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The createdDate
     */
    public int getCreatedDate() {
        return createdDate;
    }

    /**
     * 
     * @param createdDate
     *     The createdDate
     */
    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public Agent withCreatedDate(int createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * 
     * @return
     *     The modifiedDate
     */
    public int getModifiedDate() {
        return modifiedDate;
    }

    /**
     * 
     * @param modifiedDate
     *     The modifiedDate
     */
    public void setModifiedDate(int modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Agent withModifiedDate(int modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * 
     * @return
     *     The tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 
     * @param tenantId
     *     The tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Agent withTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Agent withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * 
     * @param surname
     *     The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Agent withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public Agent withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * 
     * @return
     *     The agentJid
     */
    public String getAgentJid() {
        return agentJid;
    }

    /**
     * 
     * @param agentJid
     *     The agentJid
     */
    public void setAgentJid(String agentJid) {
        this.agentJid = agentJid;
    }

    public Agent withAgentJid(String agentJid) {
        this.agentJid = agentJid;
        return this;
    }

    /**
     * 
     * @return
     *     The maxChats
     */
    public int getMaxChats() {
        return maxChats;
    }

    /**
     * 
     * @param maxChats
     *     The maxChats
     */
    public void setMaxChats(int maxChats) {
        this.maxChats = maxChats;
    }

    public Agent withMaxChats(int maxChats) {
        this.maxChats = maxChats;
        return this;
    }

    /**
     * 
     * @return
     *     The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * @param imageUrl
     *     The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Agent withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(createdDate).append(modifiedDate).append(tenantId).append(name).append(surname).append(email).append(agentJid).append(maxChats).append(imageUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Agent) == false) {
            return false;
        }
        Agent rhs = ((Agent) other);
        return new EqualsBuilder().append(id, rhs.id).append(createdDate, rhs.createdDate).append(modifiedDate, rhs.modifiedDate).append(tenantId, rhs.tenantId).append(name, rhs.name).append(surname, rhs.surname).append(email, rhs.email).append(agentJid, rhs.agentJid).append(maxChats, rhs.maxChats).append(imageUrl, rhs.imageUrl).isEquals();
    }

}
