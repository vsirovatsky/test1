package com.allrounds.pcms.dao;

import java.util.Date;

public class DAODetailsParams {
	
	public enum TAB_NAME { BALANCE, STATEMENTS, PARTNER_CAPITAL, CASH_FLOW }
	
	private String fundId;	
	private Date startDate;
	private Date endDate;
	private TAB_NAME tab;
	private String chart;
	private int page;

	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
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
	public TAB_NAME getTab() {
		return tab;
	}
	public void setTab(TAB_NAME tab) {
		this.tab = tab;
	}
	public String getChart() {
		return chart;
	}
	public void setChart(String chart) {
		this.chart = chart;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public boolean isDatesSet() {
		return this.startDate!=null && this.endDate != null;
	}
}
