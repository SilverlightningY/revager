//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.05.29 at 10:25:38 PM MESZ 
//

package org.revager.app.model.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

/**
 * An attendee is a person that takes part in a review session.
 * 
 * <p>
 * Java class for attendeeReferenceType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="attendeeReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attendee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="preparation-time" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attendeeReferenceType", propOrder = { "attendee", "preparationTime" })
public class AttendeeReference {

	@XmlElement(required = true)
	protected String attendee;
	@XmlElement(name = "preparation-time")
	protected Duration preparationTime;

	/**
	 * Gets the value of the attendee property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAttendee() {
		return attendee;
	}

	/**
	 * Sets the value of the attendee property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAttendee(String value) {
		this.attendee = value;
	}

	public boolean isSetAttendee() {
		return (this.attendee != null);
	}

	/**
	 * Gets the value of the preparationTime property.
	 * 
	 * @return possible object is {@link Duration }
	 * 
	 */
	public Duration getPreparationTime() {
		return preparationTime;
	}

	/**
	 * Sets the value of the preparationTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Duration }
	 * 
	 */
	public void setPreparationTime(Duration value) {
		this.preparationTime = value;
	}

	public boolean isSetPreparationTime() {
		return (this.preparationTime != null);
	}

}
