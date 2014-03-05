package com.allrounds.pcms.domain;

import java.util.Date;

public class JournalEntry {
	
	private int id;

	private String name;
	
	private Date date;
	
	public JournalEntry() {super();}

	public JournalEntry(int id, String name, Date date) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
