package com.healthgov.exceptions;

import java.time.LocalDate;

public class ExceptionResponse {
	private String errMessage;
	private LocalDate dateOfException;
	private int statusCode;
	
	public ExceptionResponse() {
	}
	public ExceptionResponse(String errMessage, LocalDate dateOfException, int statusCode) {
		super();
		this.errMessage = errMessage;
		this.dateOfException = dateOfException;
		this.statusCode = statusCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public LocalDate getDateOfException() {
		return dateOfException;
	}
	public void setDateOfException(LocalDate dateOfException) {
		this.dateOfException = dateOfException;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
