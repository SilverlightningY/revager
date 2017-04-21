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

/**
 * An aspect is a single directive for which a product is checked.
 * 
 * <p>
 * Java class for aspectType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="aspectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="directive" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aspectType", propOrder = { "id", "category", "directive", "description" })
public class Aspect {

	@XmlElement(required = true)
	protected String id;
	@XmlElement(required = true)
	protected String category;
	@XmlElement(required = true)
	protected String directive;
	@XmlElement(required = true)
	protected String description;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

	public boolean isSetId() {
		return (this.id != null);
	}

	/**
	 * Gets the value of the category property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the value of the category property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCategory(String value) {
		this.category = value;
	}

	public boolean isSetCategory() {
		return (this.category != null);
	}

	/**
	 * Gets the value of the directive property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDirective() {
		return directive;
	}

	/**
	 * Sets the value of the directive property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDirective(String value) {
		this.directive = value;
	}

	public boolean isSetDirective() {
		return (this.directive != null);
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	public boolean isSetDescription() {
		return (this.description != null);
	}

}
