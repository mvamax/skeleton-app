package io.app.core.join;

import java.util.List;

public class FieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	private List<String> fieldsAvailable;

	public List<String> getFieldsAvailable() {
		return fieldsAvailable;
	}

	public void setFieldsAvailable(List<String> fieldsAvailable) {
		this.fieldsAvailable = fieldsAvailable;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FieldException(String message, List<String> fieldsAvailable) {
		super();
		this.message = message;
		this.fieldsAvailable = fieldsAvailable;
	}

}
