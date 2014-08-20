package com.mocha.unitcode.mina.code;


public class MinaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3117037333965625264L;

	public MinaException() {
		super();
	}

	public MinaException(Exception e) {
		super(e);
	}

	public MinaException(String s) {
		super(s);
	}

	public MinaException(String s, Exception e) {
		super(s, e);
	}
}

