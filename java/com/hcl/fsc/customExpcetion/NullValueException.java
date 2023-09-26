package com.hcl.fsc.customExpcetion;

public class NullValueException extends Exception {
	public NullValueException() {

	}

	public NullValueException(String str) {
		super(str);
	}
}
