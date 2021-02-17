package org.julio.gregorio.adidas.subscription.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiParam;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubscriptionDTO implements Serializable {

	private static final long serialVersionUID = -6543220464576733780L;

	@XmlElement
	@ApiParam(value = "Subcription user first name")
	private String firstName;

	@XmlElement
	@ApiParam(value = "Subcription user gender")
	private String gender;

	@XmlElement
	@ApiParam(value = "Subcription user date of birth", required = true)
	@NotNull
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
	private String dateOfBirth;

	@XmlElement
	@ApiParam(value = "Subcription user email", required = true)
	@NotNull
	// @Pattern(regexp =
	// "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
	private String email;

	@XmlElement
	@ApiParam(value = "User consent", required = true)
	@NotNull
	private Boolean consent;

	@XmlElement
	@ApiParam(value = "Subcription associated newsletter", required = true)
	@NotNull
	private NewsLetterDTO newsletter;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getConsent() {
		return consent;
	}

	public void setConsent(Boolean consent) {
		this.consent = consent;
	}

	public NewsLetterDTO getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(NewsLetterDTO newsletter) {
		this.newsletter = newsletter;
	}

}