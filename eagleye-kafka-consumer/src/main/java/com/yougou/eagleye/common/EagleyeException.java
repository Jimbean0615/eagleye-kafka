package com.yougou.eagleye.common;

public class EagleyeException extends Exception {
	
	private static final long serialVersionUID = -8366864047417916118L;

	public EagleyeException(Exception e) {
		super(e);
	}

	public EagleyeException(String txt) {
		super(txt);
	}
}