package com.allrounds.pcms.service;

public class FundFinancials {
	
	private FundFinancialsBalance balance;
	private FundFinancialsStatements statements;
	private FundFinancialsPartnerCapital partnerCapital;
	private FundFinancialsCashFlow cashFlow;

	public FundFinancialsBalance getBalance() {
		return balance;
	}

	public void setBalance(FundFinancialsBalance balance) {
		this.balance = balance;
	}

	public FundFinancialsStatements getStatements() {
		return statements;
	}

	public void setStatements(FundFinancialsStatements statements) {
		this.statements = statements;
	}

	public FundFinancialsPartnerCapital getPartnerCapital() {
		return partnerCapital;
	}

	public void setPartnerCapital(FundFinancialsPartnerCapital partnerCapital) {
		this.partnerCapital = partnerCapital;
	}

	public FundFinancialsCashFlow getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(FundFinancialsCashFlow cashFlow) {
		this.cashFlow = cashFlow;
	}
	
}
