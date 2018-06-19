package com.tanatat.exceptions;

import com.tanatat.stderr.StatusCode;
import com.tanatat.stderr.StatusCode.Status;

public class LoginFailException extends Exception {
	private static final long serialVersionUID = 1L;
	 
	public StatusCode.Status getResponse(){
		return Status.LOGIN_FAIL;
	}
}
