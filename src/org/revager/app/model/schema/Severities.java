//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.05.29 at 10:25:38 PM MESZ 
//


package org.revager.app.model.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This type lists all severities that can be 
 *              defined for a finding. The order of the severities define their 
 *              importance. The first listed severity is more important than the 
 *              second and so on. The last listed severity is the least important.
 *            
 * 
 * <p>Java class for severitiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="severitiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "severitiesType", propOrder = {
    "severities"
})
public class Severities {

    @XmlElement(name = "severity", required = true)
    protected List<String> severities;

    /**
     * Gets the value of the severities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the severities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeverities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSeverities() {
        if (severities == null) {
            severities = new ArrayList<String>();
        }
        return this.severities;
    }

    public boolean isSetSeverities() {
        return ((this.severities!= null)&&(!this.severities.isEmpty()));
    }

    public void unsetSeverities() {
        this.severities = null;
    }

}
