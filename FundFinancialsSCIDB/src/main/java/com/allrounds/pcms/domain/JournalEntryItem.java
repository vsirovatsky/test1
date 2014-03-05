package com.allrounds.pcms.domain;

import java.util.Date;

public class JournalEntryItem {

	private double debit;
	
	private double credit;
	
	private Date date;

	private int chartofaccountsId;
	
	private int jeId;
	
	private int investorId;
	
	public JournalEntryItem() {super();}
	
	public JournalEntryItem(double debit, double credit, Date date,
			int chartofaccountsId, int jeId, int investorId) {
		super();
		this.debit = debit;
		this.credit = credit;
		this.date = date;
		this.chartofaccountsId = chartofaccountsId;
		this.jeId = jeId;
		this.investorId = investorId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getChartofaccountsId() {
		return chartofaccountsId;
	}

	public void setChartofaccountsId(int chartofaccountsId) {
		this.chartofaccountsId = chartofaccountsId;
	}

	public int getJeId() {
		return jeId;
	}

	public void setJeId(int jeId) {
		this.jeId = jeId;
	}

	public int getInvestorId() {
		return investorId;
	}

	public void setInvestorId(int investorId) {
		this.investorId = investorId;
	}

	
}
