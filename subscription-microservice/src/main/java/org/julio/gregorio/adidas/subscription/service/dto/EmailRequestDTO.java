package org.julio.gregorio.adidas.subscription.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiParam;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailRequestDTO implements Serializable {

	private static final long serialVersionUID = -3830090798177767144L;

	@XmlElement
	@ApiParam(value = "Destination Email", required = true)
	@NotNull
	private String destEmail;

	@XmlElement
	@ApiParam(value = "From Email", required = true)
	@NotNull
	private String fromEmail;

	@XmlElement
	@ApiParam(value = "Email Subject", required = true)
	@NotNull
	private String emailSubject;

	@XmlElement
	@ApiParam(value = "Email Content (accept html)", required = true)
	@NotNull
	private String emailContent;

	public String getDestEmail() {
		return destEmail;
	}

	public void setDestEmail(String destEmail) {
		this.destEmail = destEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	@Override
	public String toString() {
		return "EmailRequestDTO [destEmail=" + destEmail + ", fromEmail=" + fromEmail + ", emailSubject=" + emailSubject
				+ ", emailContent=" + emailContent + "]";
	}

}