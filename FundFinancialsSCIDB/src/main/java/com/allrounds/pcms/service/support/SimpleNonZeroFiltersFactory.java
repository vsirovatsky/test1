package com.allrounds.pcms.service.support;

import com.allrounds.pcms.service.FundFinancialsBalance;
import com.allrounds.pcms.service.FundFinancialsCashFlow;
import com.allrounds.pcms.service.FundFinancialsPartnerCapital;
import com.allrounds.pcms.service.FundFinancialsStatements;

public class SimpleNonZeroFiltersFactory implements IValueFiltersFactory {

	@Override
	public IValuesFilter<FundFinancialsBalance.ValuePair> getBalanceFilter(){
		return new SimpleNonZeroFilter<FundFinancialsBalance.ValuePair>();
	}
	
	@Override
	public IValuesFilter<FundFinancialsStatements.ValuePair> getStatementsFilter(){
		return new SimpleNonZeroFilter<FundFinancialsStatements.ValuePair>();
	}

	@Override
	public IValuesFilter<FundFinancialsPartnerCapital.ValuePair> getPartnerCapitalFilter(){
		return new SimpleNonZeroFilter<FundFinancialsPartnerCapital.ValuePair>();
	}

	@Override
	public IValuesFilter<FundFinancialsCashFlow.ValuePair> getCashFlow(){
		return new SimpleNonZeroFilter<FundFinancialsCashFlow.ValuePair>();
	}

}
