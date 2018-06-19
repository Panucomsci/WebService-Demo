package com.tanatat.exceptions;

import com.tanatat.stderr.StatusCode.Status;

public class GatewayReturnError extends Exception { 
	private static final long serialVersionUID = 1L;

	public Status getResponse() {
		return Status.GATEWAY_RETURN_ERROR;
	}
}
