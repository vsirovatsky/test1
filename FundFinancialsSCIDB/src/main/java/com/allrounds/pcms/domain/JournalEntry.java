package com.allrounds.pcms.domain;

import java.util.Date;

public class JournalEntry {
	
	private String id;

	private String name;
	
	private int date;
	
	public JournalEntry() {super();}

	public JournalEntry(String id, String name, int date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
