package org.julio.gregorio.adidas.subscription.service.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiParam;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubscriptionRequestDTO implements Serializable {

	private static final long serialVersionUID = -6543220464576733780L;

	@XmlElement
	@ApiParam(value = "Subcription user first name")
	private String firstName;

	@XmlElement
	@ApiParam(value = "Subcription user gender (Valid values F, M)")
	@Pattern(regexp="^(F|M)$", message="Invalid gender (Accepted values F, M)")
	private String gender;

	@XmlElement
	@ApiParam(value = "Subcription user date of birth (Format: yyyy-MM-dd)", required = true)
	@Past
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateOfBirth;

	@XmlElement
	@ApiParam(value = "Subcription user email", required = true)
	@NotNull
	@Email
	private String email;

	@XmlElement
	@ApiParam(value = "User consent", required = true)
	@NotNull
	private Boolean consent;

	@XmlElement
	@ApiParam(value = "Subcription associated newsletter id", required = true)
	@NotNull
	private Long newsLetterId;

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public Long getNewsLetterId() {
		return newsLetterId;
	}

	public void setNewsLetterId(Long newsLetterId) {
		this.newsLetterId = newsLetterId;
	}

	@Override
	public String toString() {
		return "SubscriptionRequestDTO [firstName=" + firstName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", consent=" + consent + ", newsLetterId=" + newsLetterId + "]";
	}

}