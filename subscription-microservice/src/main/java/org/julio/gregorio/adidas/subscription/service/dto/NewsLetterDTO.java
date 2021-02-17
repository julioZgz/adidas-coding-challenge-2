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
public class NewsLetterDTO implements Serializable {

	private static final long serialVersionUID = -4156709322804073995L;

	@XmlElement
	@ApiParam(value = "NewsLetter Id")
	private Long newsLetterId;

	@XmlElement
	@ApiParam(value = "Campaign Year (Format: YYYY)")
	@NotNull
	@Pattern(regexp="\\d{4}")
	private Integer campaignYear;

	public Long getNewsLetterId() {
		return newsLetterId;
	}

	public void setNewsLetterId(Long newsLetterId) {
		this.newsLetterId = newsLetterId;
	}

	public Integer getCampaignYear() {
		return campaignYear;
	}

	public void setCampaignYear(Integer campaignYear) {
		this.campaignYear = campaignYear;
	}

}