package com.allrounds.pcms.service.financialscreation;

import java.util.Date;

public class FundFinancialsParameters {
	
	public static enum MODE { BALANCE, OPERATIONS, PARTNER_CAPITAL, CASH_FLOW, ALL }; 

	private MODE mode = MODE.ALL;
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public MODE getMode() {
		return mode;
	}
	public void setMode(MODE mode) {
		this.mode = mode;
	}
	
}
