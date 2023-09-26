package com.hcl.fsc.customExpcetion;

public class DuplicateValueException extends Exception {
	
	public DuplicateValueException() {

	}
	
	public DuplicateValueException(String str) {
		super(str);
	}
	
}
