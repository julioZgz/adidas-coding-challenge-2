package org.julio.gregorio.adidas.subscription.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorDTOList implements Serializable {

	private static final long serialVersionUID = -7226890350020355187L;

	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	private List<ErrorDTO> errors;

	public List<ErrorDTO> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDTO> errors) {
		this.errors = errors;
	}

	public void addError(ErrorDTO error) {
		if (error != null) {
			if (errors == null) {
				errors = new ArrayList<>();
				errors.add(error);
			} else if (!errors.contains(error)) {
				errors.add(error);
			}
		}
	}

	public void addErrors(List<ErrorDTO> errors) {
		if (errors != null) {
			for (ErrorDTO error : errors) {
				addError(error);
			}
		}
	}

}
