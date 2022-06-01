package org.coronaVirusTracker.exceptions;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;
	private String message;
	private String details;

	public ExceptionResponse() {
	}

	public ExceptionResponse(Date date, String message, String details) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, details, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionResponse other = (ExceptionResponse) obj;
		return Objects.equals(date, other.date) && Objects.equals(details, other.details)
				&& Objects.equals(message, other.message);
	}

	@Override
	public String toString() {
		return "ExceptionResponse [date=" + date + ", message=" + message + ", details=" + details + "]";
	}

}
