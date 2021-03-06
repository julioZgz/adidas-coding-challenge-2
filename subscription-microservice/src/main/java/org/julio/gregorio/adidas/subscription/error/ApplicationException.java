package org.julio.gregorio.adidas.subscription.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 5031095461061279690L;
	
	public ApplicationException(String message) {
		super(message);
	}

}
