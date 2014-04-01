package com.allrounds.pcms.service.support;

import java.util.HashMap;

import com.allrounds.pcms.domain.JournalEntryItem.CATEGORY;

public class JeiUtils {

	private static final HashMap<String,CATEGORY> categories = new HashMap<String,CATEGORY>();
	
	static {
		categories.put("Cash", CATEGORY.ASSET);
		categories.put("Accounts Receivable", CATEGORY.ASSET);
		categories.put("Long Term Asset", CATEGORY.ASSET);
		categories.put("Short Term Asset", CATEGORY.ASSET);
		categories.put("Marketable Securities", CATEGORY.ASSET);
		categories.put("Non Marketable Securities", CATEGORY.ASSET);
		categories.put("Other Current Asset", CATEGORY.ASSET);
		categories.put("Other Asset", CATEGORY.ASSET);
		
		categories.put("Accounts Payable", CATEGORY.LIABILITY);
		categories.put("Short Term Liability", CATEGORY.LIABILITY);
		categories.put("Long Term Liability", CATEGORY.LIABILITY);
		categories.put("Other Current Liability", CATEGORY.LIABILITY);
	}
	
	public static CATEGORY checkCategory ( String s ) {
		if ( s == null ) return CATEGORY.EQUITY;
		final CATEGORY c = categories.get( s );
		return (c!=null) ? c : CATEGORY.EQUITY;
	}
	
	public static Boolean isNetIncome( String catName ){
    	return isIncome(catName) || isExpense(catName); 
    }
   
    public static boolean isIncome( String catName ){
    	return ( (catName == "Income") || (catName == "Other Income") ); 
    }
   
    public static boolean isExpense( String catName ){
    	return ( (catName == "Expense") || (catName == "Other Expense") ); 
    }
    
    public static boolean isUnrealizedGains( String chartName ) {
    	return ( chartName == "Unrealized Gains" );
    }

    public static boolean isRealizedGains( String chartName ) {
    	return ( chartName == "Realized Gains" );
    }
    
}