package com.allrounds.pcms.dao;

import java.util.Date;

public class DAOParams {

	private String fundId;
	private Date startDate;
	private Date endDate;
	
	public DAOParams(){}
	
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

	public boolean isDatesSet() {
		return this.startDate!=null && this.endDate != null;
	}
}
