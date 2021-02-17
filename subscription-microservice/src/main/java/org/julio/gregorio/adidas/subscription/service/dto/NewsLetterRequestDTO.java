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
public class NewsLetterRequestDTO implements Serializable {

	private static final long serialVersionUID = -4156709322804073995L;

	@XmlElement
	@ApiParam(value = "Campaign Year (Format: YYYY)", required = true)
	@NotNull
	@Pattern(regexp="\\d{4}")
	private String campaignYear;

	public String getCampaignYear() {
		return campaignYear;
	}

	public void setCampaignYear(String campaignYear) {
		this.campaignYear = campaignYear;
	}

	@Override
	public String toString() {
		return "NewsLetterRequestDTO [campaignYear=" + campaignYear + "]";
	}

}