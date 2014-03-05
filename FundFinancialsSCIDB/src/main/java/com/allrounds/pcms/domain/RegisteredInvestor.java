package com.allrounds.pcms.domain;

public class RegisteredInvestor {
	
	private int id;

	private String name;
	
	public RegisteredInvestor() { super(); }

	public RegisteredInvestor(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
