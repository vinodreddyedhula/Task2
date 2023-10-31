package com.task.person.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SuccessResponse<T> extends BaseRestApiResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8711085212725719300L;
	private T response;
	
	public SuccessResponse(T response) {
		super();
		this.response=response;
	}
	public SuccessResponse(String message,T response) {
		super(message,true);
		this.response=response;
	}

	public Object getResponse() {
		return response;
	}
	
}
