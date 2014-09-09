package org.unitcode.tools;

import java.io.Serializable;

public class TestBean implements Serializable {

	private static final long serialVersionUID = 6790404707869130536L;

	private String name;

	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
