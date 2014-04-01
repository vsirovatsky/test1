package com.allrounds.pcms.service.temptest;

import java.util.List;

public class FundFinancialsBalanceSheet implements IFundFinancialsBalanceSheet {
	
	private List<String> messages;

	public FundFinancialsBalanceSheet(){}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
