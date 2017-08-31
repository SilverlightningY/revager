//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.05.29 at 10:25:38 PM MESZ 
//

package org.revager.app.model.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.revager.gamecontroller.FindingStatus;

/**
 * A finding is some error or comment on the product in order of its examination
 * against an aspect.
 * 
 * 
 * <p>
 * Java class for findingType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="findingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="aspect" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="external-reference" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findingType", propOrder = { "id", "severity", "description", "references", "aspects",
		"externalReferences" })
public class Finding extends Observable {

	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(Adapter4.class)
	@XmlSchemaType(name = "nonNegativeInteger")
	protected Integer id;
	@XmlElement(required = true)
	protected String severity;
	@XmlElement(required = true)
	protected String description;
	@XmlElement(name = "reference")
	protected List<String> references = new ArrayList<>();
	@XmlElement(name = "aspect")
	protected List<String> aspects = new ArrayList<>();
	@XmlElement(name = "external-reference")
	@XmlSchemaType(name = "anyURI")
	protected List<String> externalReferences = new ArrayList<>();
	@XmlTransient
	private final FindingStatus findingStatus = new FindingStatus();

	/**
	 * Can be called when the Finding is focused.
	 */
	public void focus() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(Integer value) {
		this.id = value;
	}

	public boolean isSetId() {
		return (this.id != null);
	}

	/**
	 * Gets the value of the severity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * Sets the value of the severity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSeverity(String value) {
		if (!StringUtils.equals(this.severity, value)) {
			setChanged();
		}
		this.severity = value;
		notifyObservers();
	}

	public boolean isSetSeverity() {
		return (this.severity != null);
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
		if (!StringUtils.equals(this.description, value)) {
			setChanged();
		}
		this.description = value;
		notifyObservers();
	}

	public boolean isSetDescription() {
		return (this.description != null);
	}

	/**
	 * Gets the value of the references property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the references property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getReferences().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 */
	public List<String> getReferences() {
		return this.references;
	}

	public boolean addReference(String reference) {
		if (!references.contains(reference)) {
			references.add(reference);
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	public void removeReference(String ref) {
		references.remove(ref);
		setChanged();
		notifyObservers();
	}

	public boolean updateReference(String oldReference, String newReference) {
		if (references.contains(oldReference)) {
			int index = references.lastIndexOf(oldReference);
			references.set(index, newReference);
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	public boolean isSetReferences() {
		return ((this.references != null) && (!this.references.isEmpty()));
	}

	public void unsetReferences() {
		this.references = null;
	}

	/**
	 * Gets the value of the aspects property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the aspects property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAspects().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 */
	public List<String> getAspects() {
		return this.aspects;
	}

	public boolean addAspect(String aspect) {
		if (!this.aspects.contains(aspect)) {
			aspects.add(aspect);
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	public void removeAspect(String asp) {
		aspects.remove(asp);
		setChanged();
		notifyObservers();
	}

	public boolean isSetAspects() {
		return ((this.aspects != null) && (!this.aspects.isEmpty()));
	}

	public void unsetAspects() {
		this.aspects = null;
	}

	/**
	 * Gets the value of the externalReferences property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the externalReferences property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExternalReferences().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 */
	public List<String> getExternalReferences() {
		return this.externalReferences;
	}

	public void addExternalReferences(String extRefFile) {
		externalReferences.add(extRefFile);
		setChanged();
		notifyObservers();
	}

	public void removeExternalReferences(String extRef) {
		externalReferences.remove(extRef);
		setChanged();
		notifyObservers();
	}

	public boolean isSetExternalReferences() {
		return ((this.externalReferences != null) && (!this.externalReferences.isEmpty()));
	}

	public void unsetExternalReferences() {
		this.externalReferences = null;
	}

	public FindingStatus getFindingStatus() {
		return findingStatus;
	}

}

