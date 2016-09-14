
package com.clickatell.models.mc2.integration.general;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class Country {

    private Integer id;
    private String name;
    private String isoCode3;
    private Integer numCode;
    private Integer phoneCode;
    private Integer priority;
    private List<Integer> areaCodes = new ArrayList<Integer>();
    private String isoCode2;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Country() {
    }

    /**
     * 
     * @param id
     * @param isoCode2
     * @param isoCode3
     * @param phoneCode
     * @param priority
     * @param numCode
     * @param name
     * @param areaCodes
     */
    public Country(Integer id, String name, String isoCode3, Integer numCode, Integer phoneCode, Integer priority, List<Integer> areaCodes, String isoCode2) {
        this.id = id;
        this.name = name;
        this.isoCode3 = isoCode3;
        this.numCode = numCode;
        this.phoneCode = phoneCode;
        this.priority = priority;
        this.areaCodes = areaCodes;
        this.isoCode2 = isoCode2;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
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

    /**
     * 
     * @return
     *     The isoCode3
     */
    public String getIsoCode3() {
        return isoCode3;
    }

    /**
     * 
     * @param isoCode3
     *     The isoCode3
     */
    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    /**
     * 
     * @return
     *     The numCode
     */
    public Integer getNumCode() {
        return numCode;
    }

    /**
     * 
     * @param numCode
     *     The numCode
     */
    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    /**
     * 
     * @return
     *     The phoneCode
     */
    public Integer getPhoneCode() {
        return phoneCode;
    }

    /**
     * 
     * @param phoneCode
     *     The phoneCode
     */
    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    /**
     * 
     * @return
     *     The priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 
     * @param priority
     *     The priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 
     * @return
     *     The areaCodes
     */
    public List<Integer> getAreaCodes() {
        return areaCodes;
    }

    /**
     * 
     * @param areaCodes
     *     The areaCodes
     */
    public void setAreaCodes(List<Integer> areaCodes) {
        this.areaCodes = areaCodes;
    }

    /**
     * 
     * @return
     *     The isoCode2
     */
    public String getIsoCode2() {
        return isoCode2;
    }

    /**
     * 
     * @param isoCode2
     *     The isoCode2
     */
    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(isoCode3).append(numCode).append(phoneCode).append(priority).append(areaCodes).append(isoCode2).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Country)) {
            return false;
        }
        Country rhs = ((Country) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(isoCode3, rhs.isoCode3).append(numCode, rhs.numCode).append(phoneCode, rhs.phoneCode).append(priority, rhs.priority).append(areaCodes, rhs.areaCodes).append(isoCode2, rhs.isoCode2).isEquals();
    }

}
