package io.app.web.rest.errors;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

	private final static Logger log = LoggerFactory
			.getLogger(ExceptionTranslator.class);

	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorVM entityNotFoundException(EntityNotFoundException e) {
		log.debug("catching EntityNotFoundException and processing custom message "
				+ e.getMessage());
		try {
			ErrorVM error = new ErrorVM(ErrorConstants.ERR_VALIDATION,
					messageSource.getMessage(e.getMessage(), null, "super",
							Locale.FRANCE));
			return error;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return new ErrorVM(ErrorConstants.ERR_VALIDATION,
				messageSource.getMessage(e.getMessage(), null, Locale.FRANCE));
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorVM processAccessDeniedException(AccessDeniedException e) {
		log.debug("catching processAccessDeniedException and processing custom message");
		return new ErrorVM(ErrorConstants.ERR_ACCESS_DENIED,
				messageSource.getMessage(e.getMessage(), null, e.getMessage(),
						Locale.FRANCE));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
		log.debug("catching MethodArgumentNotValidException and processing custom message");
		BindingResult result = ex.getBindingResult();
		// List<FieldError> fieldErrors = result.getFieldErrors();
		// return processFieldErrors(fieldErrors);
		List<ObjectError> errors = result.getAllErrors();
		return processObjectErrors(errors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorVM> processRuntimeException(Exception ex)
			throws Exception {
		log.debug("catching {} and processing", ex.getClass());
		ex.printStackTrace();
		BodyBuilder builder;
		ErrorVM errorVM;
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(
				ex.getClass(), ResponseStatus.class);
		if (responseStatus != null) {
			builder = ResponseEntity.status(responseStatus.value());
			errorVM = new ErrorVM("error." + responseStatus.value().value(),
					responseStatus.reason());
		} else {
			builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
			errorVM = new ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR,
					"Internal server error");
		}
		return builder.body(errorVM);
	}

	private ErrorVM processObjectErrors(List<ObjectError> objectErrors) {
		ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);

		for (ObjectError objectError : objectErrors) {
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				dto.add(fieldError.getObjectName(), fieldError.getField(),
						messageSource.getMessage(
								fieldError.getDefaultMessage(), null,
								Locale.FRANCE));
			} else {
				// dto.add(objectError.getObjectName(),
				// objectError.getObjectName(),messageSource.getMessage(objectError.getDefaultMessage(),null,
				// Locale.FRANCE));
				dto.add(objectError.getObjectName(), objectError
						.getObjectName(), messageSource.getMessage(
						objectError.getDefaultMessage(), null,
						objectError.getDefaultMessage(), Locale.FRANCE));
			}

		}

		return dto;
	}

	// private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
	// ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);
	//
	// for (FieldError fieldError : fieldErrors) {
	// System.out.println(fieldError);
	// dto.add(fieldError.getObjectName(), fieldError.getField(),
	// messageSource.getMessage(fieldError.getDefaultMessage(),
	// null, Locale.FRANCE));
	// }
	//
	// return dto;
	// }
}
