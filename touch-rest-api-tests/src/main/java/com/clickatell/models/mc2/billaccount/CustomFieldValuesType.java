//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.29 at 05:24:47 PM EEST 
//


package com.clickatell.models.mc2.billaccount;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for customFieldValuesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customFieldValuesType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="elementCount" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="pageSize" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="totalElements" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="totalPages" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customFieldValuesType", propOrder = {
    "value"
})
public class CustomFieldValuesType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "elementCount")
    protected Byte elementCount;
    @XmlAttribute(name = "pageNumber")
    protected Byte pageNumber;
    @XmlAttribute(name = "pageSize")
    protected Byte pageSize;
    @XmlAttribute(name = "totalElements")
    protected Byte totalElements;
    @XmlAttribute(name = "totalPages")
    protected Byte totalPages;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the elementCount property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getElementCount() {
        return elementCount;
    }

    /**
     * Sets the value of the elementCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setElementCount(Byte value) {
        this.elementCount = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPageNumber(Byte value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPageSize(Byte value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the totalElements property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getTotalElements() {
        return totalElements;
    }

    /**
     * Sets the value of the totalElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setTotalElements(Byte value) {
        this.totalElements = value;
    }

    /**
     * Gets the value of the totalPages property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the value of the totalPages property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setTotalPages(Byte value) {
        this.totalPages = value;
    }

}
