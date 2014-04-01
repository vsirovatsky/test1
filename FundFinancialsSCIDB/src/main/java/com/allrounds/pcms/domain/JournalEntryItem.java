package com.allrounds.pcms.domain;

import com.allrounds.pcms.service.support.JeiUtils;

public class JournalEntryItem {
	
	public enum CATEGORY { ASSET, LIABILITY, EQUITY }
	
	private double debit;
	
	private double credit;
	
	private int date;

	private String chartofaccounts;
	
	private String chartcategory;
	
	private String jeId;
	
	private String investor;
	
	private CATEGORY category;
	
	public JournalEntryItem() {super();}
	
	public JournalEntryItem(double debit, double credit, int date,
			String chartofaccounts, String jeId, String investor, String chartcategory ) {
		super();
		this.debit = debit;
		this.credit = credit;
		this.date = date;
		this.chartofaccounts = chartofaccounts;
		this.jeId = jeId;
		this.investor = investor;
		setChartcategory(chartcategory);
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getChartofaccounts() {
		return chartofaccounts;
	}

	public void setChartofaccounts(String chartofaccounts) {
		this.chartofaccounts = chartofaccounts;
	}

	public String getJeId() {
		return jeId;
	}

	public void setJeId(String jeId) {
		this.jeId = jeId;
	}

	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	public String getChartcategory() {
		return chartcategory;
	}

	public void setChartcategory(String chartcategory) {
		this.chartcategory = chartcategory;
		setCategory(JeiUtils.checkCategory(this.chartcategory));
	}

	public CATEGORY getCategory() {
		return category;
	}

	public void setCategory(CATEGORY category) {
		this.category = category;
	}

}
