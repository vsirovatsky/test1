package com.allrounds.pcms.dao;

import java.util.List;

import com.allrounds.pcms.domain.ChartOfAccount;
import com.allrounds.pcms.domain.JournalEntry;
import com.allrounds.pcms.domain.JournalEntryItem;
import com.allrounds.pcms.domain.RegisteredInvestor;

public interface IDataProvider {

	public List<RegisteredInvestor> getAllInvestors();
	
	public List<ChartOfAccount> getAllCharts();
	
	public List<JournalEntry> getAllEntries();
	
	public List<JournalEntryItem> getAllItems();
	
}
