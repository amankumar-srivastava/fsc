package com.hcl.fsc.customExpcetion;

public class ValueNotPresentException extends Exception{
	public ValueNotPresentException() {
		
	}
	public ValueNotPresentException(String str) {
		super(str);
	}
}
