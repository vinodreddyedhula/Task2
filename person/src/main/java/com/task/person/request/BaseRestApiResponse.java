package com.task.person.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRestApiResponse implements Serializable{

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 4713646429378010919L;
	
	private String message;
	private boolean success=true;
	
	public BaseRestApiResponse() {
		this.message="Operation Completed Successfully";
		this.success=true;
	}
	
	public BaseRestApiResponse(String message,boolean success) {
		super();
		this.message=message;
		this.success=success;
	}

}
