package com.allrounds.pcms.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.allrounds.pcms.dao.IDataProvider;

public class FundFinancialsBalanceSheetFactory {

	@Autowired
	private IDataProvider dataProvider;
	
	public static final FundFinancialsBalanceSheetFactory INSTANCE = new FundFinancialsBalanceSheetFactory();
	
	private FundFinancialsBalanceSheetFactory(){}
	
	public IFundFinancialsBalanceSheet generate() {
		return new FundFinancialsBalanceSheet();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
