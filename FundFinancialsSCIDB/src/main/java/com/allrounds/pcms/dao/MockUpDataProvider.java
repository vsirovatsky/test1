package com.allrounds.pcms.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.allrounds.pcms.domain.ChartOfAccount;
import com.allrounds.pcms.domain.JournalEntry;
import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.domain.RegisteredInvestor;

public class MockUpDataProvider implements IDataProvider {
	
	private List<RegisteredInvestor> investors;
	
	private List<ChartOfAccount> charts;
	
	private List<JournalEntry> entries;
	
	private List<JournalEntryItem> items;
	
	public MockUpDataProvider() {
		initializeDemoData();
	}
	
	private void initializeDemoData() {
		this.investors = new ArrayList<RegisteredInvestor>();
		this.charts = new ArrayList<ChartOfAccount>();
		this.entries = new ArrayList<JournalEntry>();
		this.items = new ArrayList<JournalEntryItem>();
		
		this.investors.add( new RegisteredInvestor(1, "Investor 1") );
		this.investors.add( new RegisteredInvestor(2, "Investor 2") );
		this.investors.add( new RegisteredInvestor(3, "Investor 3") );
		this.investors.add( new RegisteredInvestor(4, "Investor 4") );
		this.investors.add( new RegisteredInvestor(5, "Investor 5") );
		
		this.charts.add( new ChartOfAccount(1, "Cash", "Asset") );
		this.charts.add( new ChartOfAccount(2, "Unrealized Gains", "Liability") );
		this.charts.add( new ChartOfAccount(3, "Partner Capital", "Equity") );
		
		this.entries.add( new JournalEntry(1, "Contribution", Date.valueOf("2000-01-01")));
		this.entries.add( new JournalEntry(2, "Contribution", Date.valueOf("2000-02-01")));
		this.entries.add( new JournalEntry(3, "Unrealized Gains", Date.valueOf("2000-03-01")));
		this.entries.add( new JournalEntry(4, "Unrealized Gains", Date.valueOf("2000-04-01")));
		
		// 1st contribution
		this.items.add( new JournalEntryItem(1000, 0, this.entries.get(0).getDate(), this.charts.get(0).getId(), this.entries.get(0).getId(), 1) );
		this.items.add( new JournalEntryItem(0, 1000, this.entries.get(0).getDate(), this.charts.get(2).getId(), this.entries.get(0).getId(), 1) );
		this.items.add( new JournalEntryItem(1500, 0, this.entries.get(0).getDate(), this.charts.get(0).getId(), this.entries.get(0).getId(), 2) );
		this.items.add( new JournalEntryItem(0, 1500, this.entries.get(0).getDate(), this.charts.get(2).getId(), this.entries.get(0).getId(), 2) );
		
		// 2nd contribution
		this.items.add( new JournalEntryItem(2000, 0, this.entries.get(1).getDate(), this.charts.get(0).getId(), this.entries.get(1).getId(), 3) );
		this.items.add( new JournalEntryItem(0, 2000, this.entries.get(1).getDate(), this.charts.get(2).getId(), this.entries.get(1).getId(), 3) );
		this.items.add( new JournalEntryItem(3000, 0, this.entries.get(1).getDate(), this.charts.get(0).getId(), this.entries.get(1).getId(), 4) );
		this.items.add( new JournalEntryItem(0, 3000, this.entries.get(1).getDate(), this.charts.get(2).getId(), this.entries.get(1).getId(), 4) );
		this.items.add( new JournalEntryItem(500, 0, this.entries.get(1).getDate(), this.charts.get(0).getId(), this.entries.get(1).getId(), 5) );
		this.items.add( new JournalEntryItem(0, 500, this.entries.get(1).getDate(), this.charts.get(2).getId(), this.entries.get(1).getId(), 5) );
		
		// 1st unrealized
		this.items.add( new JournalEntryItem(100, 0, this.entries.get(2).getDate(), this.charts.get(1).getId(), this.entries.get(2).getId(), 1) );
		this.items.add( new JournalEntryItem(0, 100, this.entries.get(2).getDate(), this.charts.get(2).getId(), this.entries.get(2).getId(), 1) );
		this.items.add( new JournalEntryItem(150, 0, this.entries.get(2).getDate(), this.charts.get(1).getId(), this.entries.get(2).getId(), 2) );
		this.items.add( new JournalEntryItem(0, 150, this.entries.get(2).getDate(), this.charts.get(2).getId(), this.entries.get(2).getId(), 2) );
		this.items.add( new JournalEntryItem(200, 0, this.entries.get(2).getDate(), this.charts.get(1).getId(), this.entries.get(2).getId(), 3) );
		this.items.add( new JournalEntryItem(0, 200, this.entries.get(2).getDate(), this.charts.get(2).getId(), this.entries.get(2).getId(), 3) );
		this.items.add( new JournalEntryItem(300, 0, this.entries.get(2).getDate(), this.charts.get(1).getId(), this.entries.get(2).getId(), 4) );
		this.items.add( new JournalEntryItem(0, 300, this.entries.get(2).getDate(), this.charts.get(2).getId(), this.entries.get(2).getId(), 4) );
		this.items.add( new JournalEntryItem(50, 0, this.entries.get(2).getDate(), this.charts.get(1).getId(), this.entries.get(2).getId(), 5) );
		this.items.add( new JournalEntryItem(0, 50, this.entries.get(2).getDate(), this.charts.get(2).getId(), this.entries.get(2).getId(), 5) );
		
		// 1st unrealized
		this.items.add( new JournalEntryItem(10, 0, this.entries.get(3).getDate(), this.charts.get(1).getId(), this.entries.get(3).getId(), 1) );
		this.items.add( new JournalEntryItem(0, 10, this.entries.get(3).getDate(), this.charts.get(2).getId(), this.entries.get(3).getId(), 1) );
		this.items.add( new JournalEntryItem(15, 0, this.entries.get(3).getDate(), this.charts.get(1).getId(), this.entries.get(3).getId(), 2) );
		this.items.add( new JournalEntryItem(0, 15, this.entries.get(3).getDate(), this.charts.get(2).getId(), this.entries.get(3).getId(), 2) );
		this.items.add( new JournalEntryItem(20, 0, this.entries.get(3).getDate(), this.charts.get(1).getId(), this.entries.get(3).getId(), 3) );
		this.items.add( new JournalEntryItem(0, 20, this.entries.get(3).getDate(), this.charts.get(2).getId(), this.entries.get(3).getId(), 3) );
		this.items.add( new JournalEntryItem(30, 0, this.entries.get(3).getDate(), this.charts.get(1).getId(), this.entries.get(3).getId(), 4) );
		this.items.add( new JournalEntryItem(0, 30, this.entries.get(3).getDate(), this.charts.get(2).getId(), this.entries.get(3).getId(), 4) );
		this.items.add( new JournalEntryItem(5, 0, this.entries.get(3).getDate(), this.charts.get(1).getId(), this.entries.get(3).getId(), 5) );
		this.items.add( new JournalEntryItem(0, 5, this.entries.get(3).getDate(), this.charts.get(2).getId(), this.entries.get(3).getId(), 5) );
	}

	@Override
	public List<RegisteredInvestor> getAllInvestors() {
		return this.investors;
	}

	@Override
	public List<ChartOfAccount> getAllCharts() {
		return this.charts;
	}

	@Override
	public List<JournalEntry> getAllEntries() {
		return this.entries;
	}

	@Override
	public List<JournalEntryItem> getAllItems() {
		return this.items;
	}

}
