package org.julio.gregorio.adidas.subscription.error;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Path.Node;

import org.julio.gregorio.adidas.subscription.service.dto.ErrorDTO;
import org.julio.gregorio.adidas.subscription.service.dto.ErrorDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ErrorExceptionHandler {

	private static final String ERROR_CODE_SEPARATOR = ":";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTOList> handleBadRequestException(BadRequestException ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		ErrorDTO error = new ErrorDTO();
		error.setDescription(ex.getMessage());
		errors.addError(error);

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTOList> handleNotFoundException(NotFoundException ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		ErrorDTO error = new ErrorDTO();
		error.setDescription(ex.getMessage());
		errors.addError(error);

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ForbiddenAccessException.class)
	public ResponseEntity<ErrorDTOList> handleForbiddenAccessException(ForbiddenAccessException ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		ErrorDTO error = new ErrorDTO();
		error.setDescription(ex.getMessage());
		errors.addError(error);

		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorDTOList> handleBindException(BindException ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		List<ErrorDTO> errorList = new ArrayList<>();

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		for (FieldError fieldError : fieldErrors) {
			ErrorDTO error = getErrorDTO(fieldError.getField(), fieldError.getDefaultMessage(), null, locale);
			errorList.add(error);
		}

		errors.addErrors(errorList);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDTOList> handleBindException(ConstraintViolationException ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		List<ErrorDTO> errorList = new ArrayList<>();

		for (@SuppressWarnings("rawtypes")
		ConstraintViolation violation : ex.getConstraintViolations()) {
			String field = getIsolatedPropertyPath(violation.getPropertyPath());
			ErrorDTO error = getErrorDTO(field, violation.getMessage(), null, locale);

			errorList.add(error);
		}

		errors.addErrors(errorList);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTOList> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		List<ErrorDTO> errorList = new ArrayList<>();

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		for (FieldError fieldError : fieldErrors) {
			ErrorDTO error = getErrorDTO(fieldError.getField(), fieldError.getDefaultMessage(), null, locale);
			errorList.add(error);
		}

		errors.addErrors(errorList);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorDTOList> handleOtherExceptions(Exception ex, Locale locale) {
		ErrorDTOList errors = new ErrorDTOList();

		ErrorDTO error = new ErrorDTO();
		error.setDescription(ex instanceof NullPointerException ? "NullPointerException" : ex.getMessage());
		errors.addError(error);

		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorDTO getErrorDTO(String field, String message, Object[] args, Locale locale) {
		ErrorDTO errorDTO = new ErrorDTO();

		if (!StringUtils.isEmpty(field)) {
			errorDTO.setField(field);
		}

		if (!StringUtils.isEmpty(message)) {
			String aux = null;
			try {
				aux = messageSource.getMessage(message, args, locale);
			} catch (NoSuchMessageException e) {
				aux = message;
			}
			if (aux.contains(ERROR_CODE_SEPARATOR)) {
				errorDTO.setCode(aux.split(ERROR_CODE_SEPARATOR)[0]);
				errorDTO.setDescription(aux.split(ERROR_CODE_SEPARATOR)[1]);
			} else {
				errorDTO.setDescription(aux);
			}
		}

		return errorDTO;
	}

	private String getIsolatedPropertyPath(Path path) {
		Iterator<Node> itr = path.iterator();
		Node node = itr.next();
		while (itr.hasNext()) {
			node = itr.next();
		}
		return node.getName();
	}

}