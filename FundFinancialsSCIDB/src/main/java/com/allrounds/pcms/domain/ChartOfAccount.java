package com.allrounds.pcms.domain;

public class ChartOfAccount {
	
	private int id;

	private String name;
	
	private String category;
	
	public ChartOfAccount(){ super(); }

	public ChartOfAccount(int id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
