package com.allrounds.pcms.service.temptest;

import org.springframework.beans.factory.annotation.Autowired;

import com.allrounds.pcms.dao.IDataProvider;
import com.allrounds.pcms.dao.TestConnection;

public class FundFinancialsBalanceSheetFactory {

	@Autowired
	private IDataProvider dataProvider;
	
	public static final FundFinancialsBalanceSheetFactory INSTANCE = new FundFinancialsBalanceSheetFactory();
	
	private FundFinancialsBalanceSheetFactory(){}
	
	public IFundFinancialsBalanceSheet generate() {
		FundFinancialsBalanceSheet bs = new FundFinancialsBalanceSheet();
		TestConnection tc = new TestConnection();
		bs.setMessages( tc.getTestInformationAQL() );
		return bs;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
