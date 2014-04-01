package com.allrounds.pcms.service.support;

import com.allrounds.pcms.service.FundFinancialsBalance;
import com.allrounds.pcms.service.FundFinancialsCashFlow;
import com.allrounds.pcms.service.FundFinancialsPartnerCapital;
import com.allrounds.pcms.service.FundFinancialsStatements;

public interface IValueFiltersFactory {
	
	IValuesFilter<FundFinancialsBalance.ValuePair> getBalanceFilter();
	
	IValuesFilter<FundFinancialsStatements.ValuePair> getStatementsFilter();

	IValuesFilter<FundFinancialsPartnerCapital.ValuePair> getPartnerCapitalFilter();

	IValuesFilter<FundFinancialsCashFlow.ValuePair> getCashFlow();

}
