//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.29 at 05:24:47 PM EEST 
//


package com.clickatell.models.mc2.billaccount;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dailyBillCycleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dailyBillCycleType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="autoBill" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="billCycleType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="currencyType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="eid" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="queryScope" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dailyBillCycleType", propOrder = {
    "value"
})
public class DailyBillCycleType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "autoBill")
    protected String autoBill;
    @XmlAttribute(name = "billCycleType")
    protected String billCycleType;
    @XmlAttribute(name = "currencyType")
    protected String currencyType;
    @XmlAttribute(name = "eid")
    protected Byte eid;
    @XmlAttribute(name = "endDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "queryScope")
    protected String queryScope;
    @XmlAttribute(name = "startDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlAttribute(name = "status")
    protected String status;

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
     * Gets the value of the autoBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoBill() {
        return autoBill;
    }

    /**
     * Sets the value of the autoBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoBill(String value) {
        this.autoBill = value;
    }

    /**
     * Gets the value of the billCycleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCycleType() {
        return billCycleType;
    }

    /**
     * Sets the value of the billCycleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCycleType(String value) {
        this.billCycleType = value;
    }

    /**
     * Gets the value of the currencyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyType() {
        return currencyType;
    }

    /**
     * Sets the value of the currencyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyType(String value) {
        this.currencyType = value;
    }

    /**
     * Gets the value of the eid property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getEid() {
        return eid;
    }

    /**
     * Sets the value of the eid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setEid(Byte value) {
        this.eid = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the queryScope property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryScope() {
        return queryScope;
    }

    /**
     * Sets the value of the queryScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryScope(String value) {
        this.queryScope = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
