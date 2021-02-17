package org.julio.gregorio.adidas.subscription.service.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class NewsLetter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;

	@Column(name = "campaign_year")
	private Integer campaignYear;
	
	@OneToMany(mappedBy="newsLetter")
    private Set<Subscription> subscriptions;

	public NewsLetter() {

	}

	public NewsLetter(Long id, Integer campaignYear) {
		super();
		this.id = id;
		this.campaignYear = campaignYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCampaignYear() {
		return campaignYear;
	}

	public void setCampaignYear(Integer campaignYear) {
		this.campaignYear = campaignYear;
	}

}