package org.julio.gregorio.adidas.subscription.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class ForbiddenAccessException extends RuntimeException {

	private static final long serialVersionUID = 5031095461061279690L;
	
	public ForbiddenAccessException(String message) {
		super(message);
	}

}
