package org.julio.gregorio.adidas.subscription.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "email")
	private String email;

	@Column(name = "consent")
	private Boolean consent;

	@ManyToOne
    @JoinColumn(name="newsletter_id", nullable=false)
	private NewsLetter newsLetter;

	public Subscription() {

	}

	public Subscription(Long id, String firstName, String gender, String dateOfBirth, String email, Boolean consent,
			NewsLetter newsletter) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.consent = consent;
		this.newsLetter = newsletter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public NewsLetter getNewsletter() {
		return newsLetter;
	}

	public void setNewsletter(NewsLetter newsletter) {
		this.newsLetter = newsletter;
	}

}