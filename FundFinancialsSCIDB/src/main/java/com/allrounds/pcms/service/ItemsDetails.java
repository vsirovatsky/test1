package com.allrounds.pcms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.utils.DateUtils;

public class ItemsDetails {
	
	public class ItemDetail {
		
		private double debit;
		
		private double credit;
		
		private Date date;

		private String chartofaccounts;
		
		private String chartcategory;

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

		public String getChartofaccounts() {
			return chartofaccounts;
		}

		public void setChartofaccounts(String chartofaccounts) {
			this.chartofaccounts = chartofaccounts;
		}

		public String getChartcategory() {
			return chartcategory;
		}

		public void setChartcategory(String chartcategory) {
			this.chartcategory = chartcategory;
		}
		
	}

	private int page;
	
	private int totalPages;
	
	private List<ItemDetail> items = new ArrayList<ItemDetail>();
	
	private Date startDate;
	
	private Date endDate;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<ItemDetail> getItems() {
		return items;
	}

	public void addItem(JournalEntryItem item) {
		ItemDetail d = new ItemDetail();
		d.setChartcategory(item.getChartcategory());
		d.setChartofaccounts(item.getChartofaccounts());
		d.setCredit(item.getCredit());
		d.setDebit(item.getDebit());
		d.setDate( DateUtils.convertToDate(item.getDate(), java.sql.Date.valueOf("2000-01-01") ) );
		this.items.add( d );
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
}
