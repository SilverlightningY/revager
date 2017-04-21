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
 * A review that is about a specimen in several versions. The specimen is
 * checked by some reviewers against defined aspects in several review sessions.
 * For further information about technical reviews see Ludewig, J. and Lichter,
 * H. (2008): Software Engineering. dpunkt.verlag GmbH.
 * 
 * <p>
 * Java class for reviewType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="reviewType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="product" type="{http://www.informatik.uni-stuttgart.de/iste/se}productType"/>
 *         &lt;element name="attendee" type="{http://www.informatik.uni-stuttgart.de/iste/se}attendeeType" maxOccurs="unbounded"/>
 *         &lt;element name="aspect" type="{http://www.informatik.uni-stuttgart.de/iste/se}aspectType" maxOccurs="unbounded"/>
 *         &lt;element name="severities" type="{http://www.informatik.uni-stuttgart.de/iste/se}severitiesType"/>
 *         &lt;element name="meeting" type="{http://www.informatik.uni-stuttgart.de/iste/se}meetingType" maxOccurs="unbounded"/>
 *         &lt;element name="impression" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recommendation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reviewType", propOrder = { "name", "description", "product", "attendees", "aspects", "severities",
		"meetings", "impression", "recommendation", "comments" })
public class Review {

	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String description;
	@XmlElement(required = true)
	protected Product product;
	@XmlElement(name = "attendee", required = true)
	protected List<Attendee> attendees;
	@XmlElement(name = "aspect", required = true)
	protected List<Aspect> aspects;
	@XmlElement(required = true)
	protected Severities severities;
	@XmlElement(name = "meeting", required = true)
	protected List<Meeting> meetings;
	@XmlElement(required = true)
	protected String impression;
	@XmlElement(required = true)
	protected String recommendation;
	@XmlElement(required = true)
	protected String comments;

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	public boolean isSetName() {
		return (this.name != null);
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

	/**
	 * Gets the value of the product property.
	 * 
	 * @return possible object is {@link Product }
	 * 
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the value of the product property.
	 * 
	 * @param value
	 *            allowed object is {@link Product }
	 * 
	 */
	public void setProduct(Product value) {
		this.product = value;
	}

	public boolean isSetProduct() {
		return (this.product != null);
	}

	/**
	 * Gets the value of the attendees property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the attendees property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAttendees().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Attendee
	 * }
	 * 
	 * 
	 */
	public List<Attendee> getAttendees() {
		if (attendees == null) {
			attendees = new ArrayList<Attendee>();
		}
		return this.attendees;
	}

	public boolean isSetAttendees() {
		return ((this.attendees != null) && (!this.attendees.isEmpty()));
	}

	public void unsetAttendees() {
		this.attendees = null;
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
	 * Objects of the following type(s) are allowed in the list {@link Aspect }
	 * 
	 * 
	 */
	public List<Aspect> getAspects() {
		if (aspects == null) {
			aspects = new ArrayList<Aspect>();
		}
		return this.aspects;
	}

	public boolean isSetAspects() {
		return ((this.aspects != null) && (!this.aspects.isEmpty()));
	}

	public void unsetAspects() {
		this.aspects = null;
	}

	/**
	 * Gets the value of the severities property.
	 * 
	 * @return possible object is {@link Severities }
	 * 
	 */
	public Severities getSeverities() {
		return severities;
	}

	/**
	 * Sets the value of the severities property.
	 * 
	 * @param value
	 *            allowed object is {@link Severities }
	 * 
	 */
	public void setSeverities(Severities value) {
		this.severities = value;
	}

	public boolean isSetSeverities() {
		return (this.severities != null);
	}

	/**
	 * Gets the value of the meetings property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the meetings property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getMeetings().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Meeting }
	 * 
	 * 
	 */
	public List<Meeting> getMeetings() {
		if (meetings == null) {
			meetings = new ArrayList<Meeting>();
		}
		return this.meetings;
	}

	public boolean isSetMeetings() {
		return ((this.meetings != null) && (!this.meetings.isEmpty()));
	}

	public void unsetMeetings() {
		this.meetings = null;
	}

	/**
	 * Gets the value of the impression property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getImpression() {
		return impression;
	}

	/**
	 * Sets the value of the impression property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setImpression(String value) {
		this.impression = value;
	}

	public boolean isSetImpression() {
		return (this.impression != null);
	}

	/**
	 * Gets the value of the recommendation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRecommendation() {
		return recommendation;
	}

	/**
	 * Sets the value of the recommendation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRecommendation(String value) {
		this.recommendation = value;
	}

	public boolean isSetRecommendation() {
		return (this.recommendation != null);
	}

	/**
	 * Gets the value of the comments property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Sets the value of the comments property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setComments(String value) {
		this.comments = value;
	}

	public boolean isSetComments() {
		return (this.comments != null);
	}

}
